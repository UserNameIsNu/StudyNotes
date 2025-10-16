package edu.nf.like.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.nf.like.common.config.RabbitConfig;
import edu.nf.like.common.confirm.ConfirmCallbackService;
import edu.nf.like.common.enumeration.RedisKeyEnum;
import edu.nf.like.common.confirm.RabbitManager;
import edu.nf.like.mapper.LikeMapper;
import edu.nf.like.model.dto.LikeDTO;
import edu.nf.like.model.entity.Like;
import edu.nf.like.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

/**
 * @author wangl
 * @date 2023/12/27
 */
@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService, ConfirmCallbackService {

    private final LikeMapper likeMapper;

    private final RedisTemplate<String, Integer> redisTemplate;

    private final RabbitManager<LikeDTO> rabbitManager;

    @Override
    public LikeDTO like(int postId, int userId) {
        boolean likeStatus = false;
        //如果缓存中存在用户id则取消点赞，不存在则添加用户id记录点赞
        if(isLike(postId, userId)) {
            //将用户ID从set集合中移除
            redisTemplate.opsForSet().remove(RedisKeyEnum.POST_LIKE_PREFIX.getValue() + postId, userId);
        } else {
            likeStatus = true;
            //将用户ID添加到set集合中
            redisTemplate.opsForSet().add(RedisKeyEnum.POST_LIKE_PREFIX.getValue() + postId, userId);
        }
        //获取当前帖子在redis中的点赞总数
        Long likeCount = redisTemplate.opsForSet().size(RedisKeyEnum.POST_LIKE_PREFIX.getValue() + postId);
        //创建LikeDTO封装修改的数据并发布到消息队列
        LikeDTO likeDTO = new LikeDTO(likeStatus, postId, userId, likeCount);
        //发送到mq异步更新到数据库
        rabbitManager.send(RabbitConfig.EXCHANGE_NAME, RabbitConfig.ROUTER_KEY,
                "likeServiceImpl", likeDTO);
        return likeDTO;
    }

    @Override
    public Boolean isLike(int postId, int userId) {
        //如果缓存中没有则先从数据库点赞表拉取当前帖子的点赞用户并缓存
        if(!redisTemplate.hasKey(RedisKeyEnum.POST_LIKE_PREFIX.getValue() + postId)) {
            synchronized (this) {
                if(!redisTemplate.hasKey(RedisKeyEnum.POST_LIKE_PREFIX.getValue() + postId)) {
                    List<Like> list = likeMapper.listLikeByPostId(postId);
                    //将所有对当前贴子点赞的用户ID保存到redis的set集合中
                    list.forEach(like -> redisTemplate.opsForSet().add(RedisKeyEnum.POST_LIKE_PREFIX.getValue() + postId, like.getUserId()));
                    //设置过期时间
                    redisTemplate.expire(RedisKeyEnum.POST_LIKE_PREFIX.getValue() + postId, Duration.ofHours(72));
                }
            }
        }
        return redisTemplate.opsForSet().isMember(RedisKeyEnum.POST_LIKE_PREFIX.getValue() + postId, userId);
    }

    /**
     * 消息投递失败后的处理
     * @param message 失败后返回的消息
     */
    @Override
    public void confirmCallback(Message message) {
        byte[] bytes = message.getBody();
        try {
            //反序列化为LikeDTO对象
            LikeDTO dto = new ObjectMapper().readValue(bytes, LikeDTO.class);
            //执行反向操作
            if(dto.getLikeStatus()) {
                redisTemplate.opsForSet().remove(RedisKeyEnum.POST_LIKE_PREFIX.getValue() + dto.getPostId(), dto.getUserId());
            } else {
                redisTemplate.opsForSet().add(RedisKeyEnum.POST_LIKE_PREFIX.getValue() + dto.getPostId(), dto.getUserId());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
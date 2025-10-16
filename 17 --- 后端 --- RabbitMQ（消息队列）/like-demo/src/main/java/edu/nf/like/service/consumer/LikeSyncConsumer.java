package edu.nf.like.service.consumer;

import edu.nf.like.common.config.RabbitConfig;
import edu.nf.like.model.dto.LikeDTO;
import edu.nf.like.model.entity.Like;
import edu.nf.like.model.entity.Post;
import edu.nf.like.mapper.LikeMapper;
import edu.nf.like.mapper.PostMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author wangl
 * @date 2023/12/7
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
@RabbitListener(queues = RabbitConfig.QUEUE_NAME)
@RequiredArgsConstructor
@Slf4j
public class LikeSyncConsumer {

    private final LikeMapper likeMapper;

    private final PostMapper postMapper;

    /**
     * 将点赞信息更新到数据库
     * @param likeDTO 点赞信息
     */
    @RabbitHandler
    public void receive(LikeDTO likeDTO) {
        //更新点赞表
        if(likeDTO.getLikeStatus()) {
            Like like = new Like();
            BeanUtils.copyProperties(likeDTO, like);
            likeMapper.save(like);
        }else{
            likeMapper.delete(likeDTO.getPostId(), likeDTO.getUserId());
        }
        //更新帖子的点赞总数
        Post post = new Post();
        BeanUtils.copyProperties(likeDTO, post);
        postMapper.update(post);
    }
}
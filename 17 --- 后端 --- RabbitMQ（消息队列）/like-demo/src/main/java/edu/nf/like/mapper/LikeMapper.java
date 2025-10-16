package edu.nf.like.mapper;

import edu.nf.like.model.entity.Like;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wangl
 * @date 2023/12/7
 */
public interface LikeMapper {

    /**
     * 根据帖子的ID查询所有点赞信息
     * @param postId 帖子ID
     * @return List<Like>
     */
    List<Like> listLikeByPostId(int postId);

    /**
     * 保存点赞信息
     * @param like 点赞信息
     */
    void save(Like like);

    /**
     * 删除点赞信息
     * @param postId 帖子id
     * @param userId 点赞人id
     */
    void delete(@Param("postId") int postId,
                @Param("userId") int userId);

}
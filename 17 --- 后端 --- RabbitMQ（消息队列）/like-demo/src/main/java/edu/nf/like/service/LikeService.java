package edu.nf.like.service;

import edu.nf.like.model.dto.LikeDTO;

/**
 * @author wangl
 * @date 2023/12/7
 */
public interface LikeService {
    /**
     * 点赞/取消点赞
     * @param postId 帖子ID
     * @param userId 用户ID
     */
    LikeDTO like(int postId, int userId);

    /**
     * 检查当前用户是否对该贴已点赞
     * @param postId 帖子ID
     * @param userId 用户ID
     * @return boolean
     */
    Boolean isLike(int postId, int userId);
}
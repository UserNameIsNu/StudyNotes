package edu.nf.like.service;

import edu.nf.like.model.dto.PostDTO;

import java.util.List;

/**
 * @author wangl
 * @date 2023/12/6
 */
public interface PostService {

    /**
     * 查询帖子列表
     * @param userId 用户ID
     * @return List<Post>
     */
    List<PostDTO> listPost(Integer userId, Integer pageNum, Integer pageSize);
}
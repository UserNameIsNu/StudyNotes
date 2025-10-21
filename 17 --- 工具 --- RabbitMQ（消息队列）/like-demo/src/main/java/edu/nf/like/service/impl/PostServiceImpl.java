package edu.nf.like.service.impl;

import edu.nf.like.mapper.PostMapper;
import edu.nf.like.service.LikeService;
import edu.nf.like.service.PostService;
import edu.nf.like.model.dto.PostDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangl
 * @date 2023/12/6
 */
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostMapper postMapper;

    private final LikeService likeService;

    @Override
    public List<PostDTO> listPost(Integer userId, Integer pageNum, Integer pageSize) {
        //缓存的postVO集合
        final List<PostDTO> postVOList = new ArrayList<>();
        postMapper.listPost(pageNum, pageSize).forEach(post -> {
            PostDTO postDTO = new PostDTO();
            postDTO.setPost(post);
            //从缓存中判断当前用户是否点赞并设置到postDTO中
            Boolean like = likeService.isLike(post.getPostId(), userId);
            postDTO.setLike(like);
            postVOList.add(postDTO);
        });
        return postVOList;
    }
}
package edu.nf.like.model.entity;

import lombok.Data;

/**
 * @author wangl
 * @date 2023/12/6
 */
@Data
public class Post {
    private Integer postId;
    private String title;
    private String content;
    private Long likeCount;
    private User user;
}
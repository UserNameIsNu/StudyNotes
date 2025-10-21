package edu.nf.like.model.entity;

import lombok.Data;

/**
 * @author wangl
 * @date 2023/12/6
 */
@Data
public class Like {
    private Integer postId;
    private Integer userId;
}
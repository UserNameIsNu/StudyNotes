package edu.nf.like.model.dto;

import edu.nf.like.model.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wangl
 * @date 2023/12/6
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {
    private Boolean like;
    private Post post;
}
package edu.nf.like.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wangl
 * @date 2023/12/7
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LikeDTO {
    /**
     * 点赞或取消点赞
     */
    private Boolean likeStatus;
    private Integer postId;
    private Integer userId;
    private Long likeCount;
}
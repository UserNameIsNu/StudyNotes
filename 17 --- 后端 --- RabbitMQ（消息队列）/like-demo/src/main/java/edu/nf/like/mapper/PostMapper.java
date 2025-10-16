package edu.nf.like.mapper;

import edu.nf.like.model.entity.Post;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wangl
 * @date 2023/12/6
 */
public interface PostMapper {

    /**
     * 查询帖子列表
     * @return List<Post>
     */
    List<Post> listPost(@Param("pageNum") int pageNum,
                        @Param("pageSize") int pageSize);

    /**
     * 更新帖子信息
     * @param post
     */
    void update(Post post);
}
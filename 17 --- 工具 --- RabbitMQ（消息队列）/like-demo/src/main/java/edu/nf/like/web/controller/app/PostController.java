package edu.nf.like.web.controller.app;

import edu.nf.like.model.entity.User;
import edu.nf.like.service.PostService;
import edu.nf.like.model.dto.PostDTO;
import edu.nf.like.common.result.ResultData;
import edu.nf.like.web.controller.BaseController;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author wangl
 * @date 2023/12/6
 */
@RestController
@RequiredArgsConstructor
public class PostController extends BaseController {

    private final PostService postService;

    @GetMapping("/post/list")
    public ResultData<List<PostDTO>> listPost(int pageNum, int pageSize,
                                              HttpSession session) {
        User user = (User) session.getAttribute("user");
        Integer userId = user != null ? user.getUserId() : -1;
        List<PostDTO> list = postService.listPost(userId, pageNum, pageSize);
        return success(list);
    }
}
package edu.nf.like.web.controller.app;

import edu.nf.like.model.dto.LikeDTO;
import edu.nf.like.model.entity.User;
import edu.nf.like.service.LikeService;
import edu.nf.like.common.result.ResultData;
import edu.nf.like.web.controller.BaseController;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangl
 * @date 2023/12/7
 */
@RestController
@RequiredArgsConstructor
public class LikeController extends BaseController {

    private final LikeService likeService;

    @PostMapping("/like")
    public ResultData<?> like(Integer postId, HttpSession session) {
        User user = (User) session.getAttribute("user");
        LikeDTO vo = likeService.like(postId, user.getUserId());
        return success(vo);
    }

}
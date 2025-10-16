package edu.nf.like.web.controller.app;

import edu.nf.like.model.entity.User;
import edu.nf.like.service.UserService;
import edu.nf.like.common.result.ResultData;
import edu.nf.like.web.controller.BaseController;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangl
 * @date 2023/12/6
 */
@RestController
@RequiredArgsConstructor
public class UserController extends BaseController {

    private final UserService userService;

    @GetMapping("/user/login")
    public ResultData<?> login(String userName, HttpSession session) {
        User user = userService.getUser(userName);
        session.setAttribute("user", user);
        return success();
    }

}
package com.ljf.ch08.web.api;

import com.ljf.ch08.common.ResultVO;
import com.ljf.ch08.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
// 对当前API进行描述解释
@Tag(
    name = "用户服务接口",
    description = "提供用户所有信息相关的API操作接口"
)
public class UserController {
    private static List<User> users;

    static {
        User u1 = new User(1, "1", 10);
        User u2 = new User(2, "2", 11);
        User u3 = new User(3, "3", 12);
        users = Arrays.asList(u1, u2, u3);
    }

    @GetMapping("/users")
    // 方法描述
    @Operation(
        summary = "查询用户列表",
        description = "不指定任何参数，查询所有的用户信息，以列表形式返回",
        method = "GET"
    )
    // 响应描述
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "响应成功"),
        @ApiResponse(responseCode = "500", description = "查询失败，服务端错误")
    })
    public ResultVO<List<User>> listUsers() {
        List<User> list = users;
        return ResultVO.success(list);
    }

    @GetMapping("/users/{id}")
    @Operation(
            summary = "查询用户",
            description = "使用用户ID，查询指定的用户信息，以对象形式返回",
            method = "GET"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "响应成功"),
            @ApiResponse(responseCode = "500", description = "查询失败，服务端错误")
    })
    // 参数描述
    @Parameters({
        @Parameter(
            name = "id",
            description = "用户ID",
            required = true,
            in = ParameterIn.PATH
        )
    })
    public ResultVO<User> getUser(@PathVariable("id") int id) {
        for(User user : users) {
            if (user.getId() == id) {
                return ResultVO.success(user);
            }
        }
        return ResultVO.error(500, "查询失败，服务端错误");
    }

    @PostMapping("/user")
    @Operation(
            summary = "添加用户",
            description = "传递用户详细信息，创建一个用户对象",
            method = "POST"
    )
//    @Parameters({
//        @Parameter(
//            description = "用户对象",
//            required = true
//        )
//    })
    public ResultVO<String> addUser(@RequestBody User user) {
        return ResultVO.success();
    }
}

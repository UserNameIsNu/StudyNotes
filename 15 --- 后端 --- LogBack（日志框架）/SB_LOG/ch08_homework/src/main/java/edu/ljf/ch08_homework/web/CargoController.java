package edu.ljf.ch08_homework.web;

import edu.ljf.ch08_homework.common.ResultVO;
import edu.ljf.ch08_homework.entity.Cargo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
// 访问前缀，一级访问缀
@RequestMapping("/v1")
// 这个组的组解释
@Tag(
        // 组名
        name = "商品服务接口",
        // 组解释
        description = "提供商品相关操作的API"
)
public class CargoController {
    // 请求方式标注和二级访问缀
    @GetMapping("/cargoes")
    // 接口方法说明
    @Operation(
            // 接口名
            summary = "查询所有商品",
            // 接口描述
            description = "获取所有商品的所有信息",
            // 接口接受的请求方法
            method = "GET"
    )
    // 接口可能的多种响应情况
    @ApiResponses({
            // 一个响应的详情
            @ApiResponse(
                    // 响应码
                    responseCode = "200",
                    // 响应解释
                    description = "正常响应，并返回预期数据"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "异常响应，没有返回预期数据，服务端出现问题"
            ),
    })
    public ResultVO<List<Cargo>> listCargoes() {
        List<Cargo> cargoes = new ArrayList<>();
        return ResultVO.success(cargoes);
    }

    @GetMapping("/cargoes/{id}")
    @Operation(
            summary = "查询指定商品",
            description = "按指定ID查询匹配的商品的所有信息",
            method = "GET"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "正常响应，并返回预期数据"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "异常响应，没有返回预期数据，服务端出现问题"
            ),
    })
    // 接口的接受参数
    @Parameters({
            // 一个参数的详情
            @Parameter(
                    // 参数名
                    name = "id",
                    // 参数描述
                    description = "商品ID",
                    // 是否为必须
                    required = true,
                    // 参数所在位置（这里是参数存在于URL中）
                    in = ParameterIn.PATH
            )
    })
    public ResultVO<Cargo> getCargo(@PathVariable(name = "id") int id) {
        Cargo cargo = new Cargo();
        return ResultVO.success(cargo);
    }

    @PostMapping("/cargoes")
    @Operation(
            summary = "添加一件商品",
            description = "添加一件商品并填充商品信息",
            method = "POST"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "正常响应，并返回预期数据"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "异常响应，没有返回预期数据，服务端出现问题"
            ),
    })
    public ResultVO<String> addCargo(@RequestBody Cargo cargo) {
        return ResultVO.success();
    }

    @DeleteMapping("/cargoes/{id}")
    @Operation(
            summary = "删除一件商品",
            description = "根据ID删除一件匹配的商品",
            method = "DELETE"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "正常响应，并返回预期数据"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "异常响应，没有返回预期数据，服务端出现问题"
            ),
    })
    @Parameters({
            @Parameter(
                    name = "id",
                    description = "商品ID",
                    required = true,
                    in = ParameterIn.PATH
            )
    })
    public ResultVO<String> delCargo(@PathVariable(name = "id") int id) {
        return ResultVO.success();
    }

    @PutMapping("/cargoes/{id}")
    @Operation(
            summary = "修改一件商品",
            description = "根据ID修改一件商品的任意或所有信息",
            method = "PUT"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "正常响应，并返回预期数据"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "异常响应，没有返回预期数据，服务端出现问题"
            ),
    })
    @Parameters({
            @Parameter(
                    name = "id",
                    description = "商品ID",
                    required = true,
                    in = ParameterIn.PATH
            )
    })
    public ResultVO<String> updCargo(@RequestBody Cargo cargo, @PathVariable(name = "id") int id) {
        return ResultVO.success();
    }
}

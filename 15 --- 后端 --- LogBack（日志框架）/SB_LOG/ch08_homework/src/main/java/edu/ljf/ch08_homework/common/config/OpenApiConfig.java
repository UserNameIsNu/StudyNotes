package edu.ljf.ch08_homework.common.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
// 全局文档基本解释
@OpenAPIDefinition(
        // 文档基本信息
        info = @Info(
                // 文档标题
                title = "客户端——Web端接口文档",
                // 文档版本
                version = "1.0",
                // 文档描述
                description = "RSL商城的客户端Web端的API接口",
                // 开源协议信息
                license = @License(name = "开源协议名", url = "http://www.baidu.com")
        ),
        // 文档遵循的开源协议
        security = @SecurityRequirement(name = "开源协议名")
)
// 定义具体安全认证方案
@SecurityScheme(
        // 开源协议名
        name = "开源协议名",
        // HTTP类型
        type = SecuritySchemeType.HTTP,
        // 使用Bearer Token认证
        scheme = "Bearer",
        // 这个认证会放在请求头中
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}

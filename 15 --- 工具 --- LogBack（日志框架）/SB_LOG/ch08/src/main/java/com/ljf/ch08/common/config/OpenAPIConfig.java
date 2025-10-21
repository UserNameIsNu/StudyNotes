package com.ljf.ch08.common.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
// 使用注解配置OpenAPI的内容描述
@OpenAPIDefinition(
    // 基本描述信息（如title，版本，文档描述，开源授权）
    info = @Info(
        title = "移动端接接口文档",
        version = "1.0",
        description = "xxx项目移动端的API接口",
        license = @License(name = "MIT", url = "http://www.baidu.com") // 开源协议
    ),
    // 所有请求均需要JWT的认证，这里的name就是指向了下面JWT认证模式中的name
    security = @SecurityRequirement(name = "JWT")
)
// 设置JWT的认证模式
@SecurityScheme(
    name = "JWT",
    type = SecuritySchemeType.HTTP,
    scheme = "Bearer",
    in = SecuritySchemeIn.HEADER
)
public class OpenAPIConfig {
}

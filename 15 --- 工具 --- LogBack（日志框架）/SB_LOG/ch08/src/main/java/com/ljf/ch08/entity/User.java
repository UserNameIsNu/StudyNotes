package com.ljf.ch08.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(
    name = "User",
    description = "用户信息"
)
public class User {
    @Schema(
        name = "id",
        description = "用户唯一标识",
        example = "1"
    )
    private Integer id;
    @Schema(
            name = "name",
            description = "用户名",
            example = "张三"
    )
    private String name;
    @Schema(
            name = "age",
            description = "用户年龄",
            example = "20"
    )
    private Integer age;
}

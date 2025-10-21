package edu.ljf.ch08_homework.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
// 补充描述信息
@Schema(
        name = "商品对象",
        description = "商品自身的所有信息"
)
public class Cargo {
    @Schema(
            // 描述对象名
            name = "cargoId",
            // 描述对象解释
            description = "商品ID",
            // 描述对象示例
            example = "1"
    )
    private Integer cargoId;

    @Schema(
            name = "cargoName",
            description = "商品名",
            example = "可乐"
    )
    private String cargoName;

    @Schema(
            name = "price",
            description = "商品单价",
            example = "100.00"
    )
    private BigDecimal price;
}

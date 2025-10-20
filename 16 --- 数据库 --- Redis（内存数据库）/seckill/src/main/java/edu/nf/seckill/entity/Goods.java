package edu.nf.seckill.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wangl
 * @date 2024/11/26
 * 商品
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Goods {
    private Integer gid;
    private String name;
    private Integer stock;
}
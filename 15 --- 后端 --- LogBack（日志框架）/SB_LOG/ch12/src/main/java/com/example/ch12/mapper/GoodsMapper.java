package com.example.ch12.mapper;

import com.example.ch12.entity.Goods;

public interface GoodsMapper {
    /**
     * 根据指定的商品ID，查询匹配的商品的全部信息
     * @param goodsId 商品ID
     * @return 商品全部信息
     */
    Goods getGoodsById(int goodsId);

    /**
     * 根据指定的商品ID，减少匹配的商品的库存数量（单件）
     * @param goodsId 商品ID
     * @return 是否成功
     */
    int decrStockById(int goodsId);
}

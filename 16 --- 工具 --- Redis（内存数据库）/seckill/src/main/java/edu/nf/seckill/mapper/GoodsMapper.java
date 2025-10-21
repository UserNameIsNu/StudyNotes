package edu.nf.seckill.mapper;

import edu.nf.seckill.entity.Goods;

import java.util.List;

/**
 * @author wangl
 * @date 2024/11/26
 */
public interface GoodsMapper {

    /**
     * 查询参与活动的商品
     * @return
     */
    List<Goods> listGoods();


    /**
     * 减库存
     * @param gid
     */
    void decrStock(int gid);
}
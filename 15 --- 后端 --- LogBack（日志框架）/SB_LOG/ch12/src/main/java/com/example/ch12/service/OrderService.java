package com.example.ch12.service;

import com.example.ch12.common.exception.OrderException;
import com.example.ch12.entity.Order;
import com.example.ch12.mapper.GoodsMapper;
import com.example.ch12.mapper.OrderMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {
    private final GoodsMapper goodsMapper;
    private final OrderMapper orderMapper;

    public OrderService(GoodsMapper goodsMapper, OrderMapper orderMapper) {
        this.goodsMapper = goodsMapper;
        this.orderMapper = orderMapper;
    }

    public void placeOrder(int goodsId) {
        // 检查库存
        if (goodsMapper.decrStockById(goodsId) == 0) {
            // 库存不足
            throw new OrderException("库存不足", 1000);
        } else {
            // 创建订单
            orderMapper.addOrder(new Order(null, 0, 1, goodsId));
        }
    }
}

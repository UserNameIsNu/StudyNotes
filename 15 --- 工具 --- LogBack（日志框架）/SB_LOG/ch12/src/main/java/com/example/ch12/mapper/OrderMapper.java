package com.example.ch12.mapper;

import com.example.ch12.entity.Order;
import org.apache.ibatis.annotations.Options;

public interface OrderMapper {
    /**
     * 创建订单
     * @param order 订单全部信息
     */
    void addOrder(Order order);
}

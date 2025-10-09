package com.example.ch12.common.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    // 队列名
    public static final String ORDER_QUEUE = "order-queue";
    // 交换机名
    public static final String ORDER_EXCHANGE = "order-exchange";
    // 路由钥匙，匹配队列与交换机的
    public static final String ORDER_ROUTING_KEY = "order";

    // 创建一个非持久化队列
    @Bean
    public Queue orderQueue() {
        return QueueBuilder.nonDurable(ORDER_QUEUE).build();
    }

    // 创建一个非持久化，没有队列绑定后会删除的交换机
    @Bean
    public DirectExchange orderExchange() {
        return new DirectExchange(ORDER_EXCHANGE, false, false);
    }

    // 绑定队列与交换机
    @Bean
    public Binding orderBinding(Queue orderQueue, DirectExchange orderExchange) {
        return BindingBuilder.bind(orderQueue).to(orderExchange).with(ORDER_ROUTING_KEY);
    }
}

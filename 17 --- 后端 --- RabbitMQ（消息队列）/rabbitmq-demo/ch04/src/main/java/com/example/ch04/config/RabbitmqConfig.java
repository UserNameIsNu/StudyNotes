package com.example.ch04.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 方式1：在配置类里配置
//@Configuration
public class RabbitmqConfig {
    public final static String EXCHANGE_NAME = "order.exchange";
    public final static String QUEUE_NANE = "order.queue";
    public final static String ROUTING_KEY = "order.key";

    // 装配direct类型的交换机
    @Bean
    public Exchange exchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    // 装配队列（true持久化队列，false非持久化队列）
    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NANE, false);
    }

    // 绑定队列与交换机
    @Bean
    public Binding binding() {
        return BindingBuilder
                .bind(queue())
                .to(exchange())
                .with(ROUTING_KEY)
                .noargs();
    }
}

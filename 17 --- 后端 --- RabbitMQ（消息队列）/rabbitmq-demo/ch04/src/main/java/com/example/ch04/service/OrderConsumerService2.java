package com.example.ch04.service;

import com.example.ch04.config.RabbitmqConfig;
import com.example.ch04.dto.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Service;

@Service
// 这玩意打在类上，类中的方法就能用来定义接收不同的消息类型
@RabbitListener(bindings = @QueueBinding(
        value = @Queue(name = RabbitmqConfig.QUEUE_NANE, durable = "false"),
        exchange = @Exchange(name = RabbitmqConfig.EXCHANGE_NAME, type = ExchangeTypes.DIRECT),
        key = RabbitmqConfig.ROUTING_KEY
))
@Slf4j
public class OrderConsumerService2 {
    // 用这个注解接收不同的消息类型
    @RabbitHandler
    public void receiveString(String massage) {
        log.info(massage);
    }

    @RabbitHandler
    public void receiveOrder(Order order) {
        log.info(order.getOrderNo());
    }
}

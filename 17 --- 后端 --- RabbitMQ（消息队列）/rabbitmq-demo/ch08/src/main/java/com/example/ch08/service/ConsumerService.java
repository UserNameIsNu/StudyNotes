package com.example.ch08.service;

import com.example.ch08.config.RabbitConfig;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class ConsumerService {
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = RabbitConfig.QUEUE_NAME, durable = "false",
                    //通过arguments属性关联死信交换机
                    arguments = {
                            @Argument(name = "x-dead-letter-exchange", value = RabbitConfig.DEAD_EXCHANGE_NAME),
                            @Argument(name = "x-dead-letter-routing-key", value = RabbitConfig.DEAD_ROUTING_KEY)
                    }),
            exchange = @Exchange(name = RabbitConfig.EXCHANGE_NAME, type = ExchangeTypes.DIRECT),
            key = RabbitConfig.ROUTING_KEY
    ))
//    @RabbitListener(queues = RabbitConfig.QUEUE_NAME)
    public void receive(String message, Channel channel, @Headers Map<String, Object> headers) {
        log.info("接收的信息：{}", message);
        throw new RuntimeException("模拟异常");
    }
}

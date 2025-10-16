package com.example.ch08.service;

import com.example.ch08.config.RabbitConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DeadConsumerService {
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = RabbitConfig.DEAD_QUEUE_NAME, durable = "false"),
            exchange = @Exchange(name = RabbitConfig.DEAD_EXCHANGE_NAME, type = ExchangeTypes.DIRECT),
            key = RabbitConfig.DEAD_ROUTING_KEY
    ))
//    @RabbitListener(queues = RabbitConfig.DEAD_QUEUE_NAME)
    public void receiveDeadLetter(String message) {
        log.info("接收到的死信：{}", message);
    }
}

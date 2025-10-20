package edu.nf.ch08.service;

import com.rabbitmq.client.Channel;
import edu.nf.ch08.config.RabbitConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

/**
 * @author wangl
 * @date 2025/10/16
 * 普通消费者服务
 */
@Service
@Slf4j
public class ConsumerService {

    /*@RabbitListener(queues = RabbitConfig.QUEUE_NAME)
    public void receive(String message) {
        log.info("Receive message: {}", message);
        throw new RuntimeException("自定义异常");
    }*/

    /**
     * 使用@RabbitListener关联死信交换机
     * @param message
     */
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
    public void receiveMessage(String message) {
        log.info("接收消息: {}", message);
        throw new RuntimeException("自定义异常");
    }
}
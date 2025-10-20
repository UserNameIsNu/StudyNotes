package edu.nf.ch08.service;

import edu.nf.ch08.config.RabbitConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Service;

/**
 * @author wangl
 * @date 2025/10/16
 * 死信队列消费服务
 */
@Service
@Slf4j
public class DeadConsumerService {

    /*@RabbitListener(queues = RabbitConfig.DEAD_QUEUE_NAME)
    public void receiveDeadLetter(String message) {
        log.info("接受到异常消费信息，转人工处理: {}", message);
    }*/

    /**
     * 使用@RabbitListener绑定死信队列和交换机
     * @param message
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = RabbitConfig.DEAD_QUEUE_NAME, durable = "false"),
            exchange = @Exchange(name = RabbitConfig.DEAD_EXCHANGE_NAME, type = ExchangeTypes.DIRECT),
            key = RabbitConfig.DEAD_ROUTING_KEY
    ))
    public void receiveDeadMessage(String message) {
        log.info("接收死信消息: {}", message);
    }
}
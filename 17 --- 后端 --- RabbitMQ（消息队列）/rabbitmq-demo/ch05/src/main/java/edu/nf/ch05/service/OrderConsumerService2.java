package edu.nf.ch05.service;

import edu.nf.ch05.config.RabbitConfig;
import edu.nf.ch05.dto.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Service;

/**
 * @author wangl
 * @date 2025/10/11
 */
@Service
@Slf4j
@RabbitListener(bindings = @QueueBinding(
        value = @Queue(name = RabbitConfig.QUEUE_NAME, durable = "false"),
        exchange = @Exchange(name = RabbitConfig.EXCHANGE_NAME, type = ExchangeTypes.DIRECT),
        key = RabbitConfig.ROUTING_KEY
))
public class OrderConsumerService2 {

    /**
     * 使用@RabbitHandler来接收不同的消息类型
     * @param message
     */
    @RabbitHandler
    public void receiveString(String message) {
        log.info("Receive string: {}", message);
    }

    /**
     * 接收Order类型的消息
     *
     * 当发送的是其他自定义类型时，spring默认使用的是
     * SimpleMessageConverter，是基于JDK的序列化和反序列化，
     * 要求所有的类必须实现Serializable接口。
     * 也可以换成JSON的序列化器
     * @param order
     */
    @RabbitHandler
    public void receiveOrder(Order order) {
        log.info("Receive order: {}", order.getOrderNo());
    }
}

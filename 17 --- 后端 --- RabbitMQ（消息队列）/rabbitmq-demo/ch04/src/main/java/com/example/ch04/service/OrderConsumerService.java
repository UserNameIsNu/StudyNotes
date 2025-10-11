package com.example.ch04.service;

import com.example.ch04.config.RabbitmqConfig;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

//@Service
@Slf4j
public class OrderConsumerService {
    // 方式1：使用这个注解标记这个方法是个消费者，监听指定的队列
//    @RabbitListener(queues = RabbitmqConfig.QUEUE_NANE)
//    public void receive(String message) {
//        log.info("处理消息：{}", message);
//    }

    // 方式2：直接用RabbitListener一次性创建交换机，队列与绑定
    // ps：若配置类改为了手动签收，则可以传入一个channel参数，用于完成ack的应答，还要个headers用于获取签收标签
    @RabbitListener(bindings = @QueueBinding(
        value = @Queue(name = RabbitmqConfig.QUEUE_NANE, durable = "false"),
        exchange = @Exchange(name = RabbitmqConfig.EXCHANGE_NAME, type = ExchangeTypes.DIRECT),
        key = RabbitmqConfig.ROUTING_KEY
    ))
    public void receive(String message, Channel channel, Map<String, Object> headers) throws IOException {
        log.info("处理消息：{}", message);
        // 手动签收需要给兔子队列返回一条消息，告知兔子队列这条消息处理完了
        // 需要先从消息头中获取签收标签
        Long tag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        // 然后应答，第二个参数表示是否批量签收
        channel.basicAck(tag, false);
    }
}

package edu.nf.ch07.service;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * @author wangl
 * @date 2025/10/14
 */
@Service
@Slf4j
public class ConsumerService {

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "test.queue", durable = "false"),
            exchange = @Exchange(name = "test.exchange", type = ExchangeTypes.DIRECT),
            key = "key.test"))
    public void receive(String message) {
        log.info("receive message: {}", message);
        //抛出异常，会将消息重新放回队列
        throw new RuntimeException("消费失败");
        //当抛出这个异常将不会把消息重新放回队列
        //throw new AmqpRejectAndDontRequeueException("消费失败");

        //basicAck是确认签收（表示消费成功），
        //如果消费失败应该拒签，拒签使用basicNack方法（支持批量处理）
        //第二个参数是否批量拒签
        //第三个参数设置为true表示重新放回队列
        //channel.basicNack(tag, false, true);

        //也可以使用单条拒签的方法basicReject
        //第二个参数用于设置是否重新放回队列
        //channel.basicReject(tag, true);
    }
}
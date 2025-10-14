package edu.nf.ch06.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * @author wangl
 * @date 2025/10/14
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ProduceService {

    private final RabbitTemplate template;

    /**
     * 消息确认投递到交换机
     * @param message
     */
    public void send(String message) {
        //创建消息的唯一ID
        CorrelationData data = new CorrelationData();
        data.setId(UUID.randomUUID().toString());
        //投递消息
        template.convertAndSend("test.exchange",
                "key.test", message, data);
        //通过一个回调来接收rabbitmq返回的ack，
        //由于设置了ConfirmCallback，因此消息只要到达exchange，返回的ack就为true
        template.setConfirmCallback((cdata, ack, cause) -> {
            //获取消息ID
            String id = cdata.getId();
            if (!ack) {
                log.info("消息{}投递失败", id);
            }  else {
                log.info("消息{}投递成功", id);
            }
        });
    }

    /**
     * 消息确认投递到队列中
     * @param message
     */
    public void send2(String message) {
        //创建消息的唯一ID
        CorrelationData data = new CorrelationData();
        data.setId(UUID.randomUUID().toString());
        //投递消息
        template.convertAndSend("test.exchange",
                "aaa", message, data);
        //确认消息是否投递到队列中
        //注意：只有消息为正确到达队列时才会执行这个回调
        template.setReturnsCallback(returnedMessage -> {
            log.info("消息内容：{}", new String(returnedMessage.getMessage().getBody(), StandardCharsets.UTF_8));
            log.info("确认码：{}", returnedMessage.getReplyCode());
            log.info("路由Key：{}", returnedMessage.getRoutingKey());
        });
    }
}
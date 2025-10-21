package com.example.ch13_homework.service.mq;

import com.example.ch13_homework.config.RabbitDeleteConfig;
import com.example.ch13_homework.config.RabbitPushConfig;
import com.example.ch13_homework.config.RabbitUpdateConfig;
import com.example.ch13_homework.entity.News;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class Producer {
    private final RabbitTemplate rabbitTemplate;

    public void push(News news) {
        //创建消息的唯一ID
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(UUID.randomUUID().toString());
        //消息的投递
        rabbitTemplate.convertAndSend(RabbitPushConfig.PUSH_EXCHANGE_NAME,
                RabbitPushConfig.PUSH_ROUTING_KEY,
                news,
                correlationData);
    }

    public void upd(News news) {
        //创建消息的唯一ID
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(UUID.randomUUID().toString());
        //消息的投递
        rabbitTemplate.convertAndSend(RabbitUpdateConfig.UPD_EXCHANGE_NAME,
                RabbitUpdateConfig.UPD_ROUTING_KEY,
                news,
                correlationData);
    }

    public void del(Object[] objects) {
        //创建消息的唯一ID
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(UUID.randomUUID().toString());
        //消息的投递
        rabbitTemplate.convertAndSend(RabbitDeleteConfig.DEL_EXCHANGE_NAME,
                RabbitDeleteConfig.DEL_ROUTING_KEY,
                objects,
                correlationData);
    }
}

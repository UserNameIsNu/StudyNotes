package com.example.ch04.service;

import com.example.ch04.config.RabbitmqConfig;
import com.example.ch04.dto.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderProducerService {
    // 注入这个对象用于投递消息
    private final RabbitTemplate rabbitTemplate;

    // 投递消息
    public void publish(String massage) {
        // 创建唯一ID
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(UUID.randomUUID().toString());
        // 消息投递
        rabbitTemplate.convertAndSend(
                RabbitmqConfig.EXCHANGE_NAME,
                RabbitmqConfig.ROUTING_KEY,
                massage,
                correlationData);
    }

    public void publish(Order order) {
        // 创建唯一ID
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(UUID.randomUUID().toString());
        // 消息投递
        rabbitTemplate.convertAndSend(
                RabbitmqConfig.EXCHANGE_NAME,
                RabbitmqConfig.ROUTING_KEY,
                order,
                correlationData);
    }
}

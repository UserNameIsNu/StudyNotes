package edu.nf.ch05.service;

import edu.nf.ch05.config.RabbitConfig;
import edu.nf.ch05.dto.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author wangl
 * @date 2025/10/11
 */
@Service
@RequiredArgsConstructor
public class OrderProducerService {

    /**
     * 注入RabbitTemplate来投递消息
     */
    private final RabbitTemplate rabbitTemplate;

    /**
     * 投递消息
     * @param message
     */
    public void publish(String message) {
        //创建消息的唯一ID
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(UUID.randomUUID().toString());
        //消息的投递
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_NAME,
                RabbitConfig.ROUTING_KEY,
                message,
                correlationData);
    }


    /**
     * @param order
     */
    public void publish(Order order) {
        //创建消息的唯一ID
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(UUID.randomUUID().toString());
        //消息的投递
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_NAME,
                RabbitConfig.ROUTING_KEY,
                order,
                correlationData);
    }

}
package edu.nf.ch08.service;

import edu.nf.ch08.config.RabbitConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author wangl
 * @date 2025/10/16
 */
@Service
@RequiredArgsConstructor
public class ProduceService {

    private final RabbitTemplate template;

    public void send(String message) {
        CorrelationData cdata = new CorrelationData(UUID.randomUUID().toString());
        template.convertAndSend(RabbitConfig.EXCHANGE_NAME,
                RabbitConfig.ROUTING_KEY, message, cdata);
    }
}
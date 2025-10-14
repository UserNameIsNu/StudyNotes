package edu.nf.ch07.service;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author wangl
 * @date 2025/10/14
 */
@Service
@RequiredArgsConstructor
public class ProduceService {

    private final RabbitTemplate template;

    public void send(String message) {
        CorrelationData cdata = new CorrelationData();
        cdata.setId(UUID.randomUUID().toString());
        template.convertAndSend("test.exchange",
                "key.test", message, cdata);
    }
}
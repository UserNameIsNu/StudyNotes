package edu.nf.like.common.confirm;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @author wangl
 * @date 2023/12/27
 */
@Component
@RequiredArgsConstructor
public class RabbitManager<T> {

    private final RabbitTemplate template;

    public void send(String exchange, String routerKey,
                     String callbackBeanName, T data) {
        try {
            CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
            byte[] bytes = new ObjectMapper().writeValueAsBytes(data);
            Message message = new Message(bytes);
            //封装一个callback的返回消息，在confirmCallback中可以通过CorrelationData得到ReturnedMessage对象
            ReturnedMessage returnedMessage = new ReturnedMessage(message, 0, callbackBeanName,
                    exchange, routerKey);
            correlationData.setReturned(returnedMessage);
            template.convertAndSend(exchange, routerKey, data, correlationData);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
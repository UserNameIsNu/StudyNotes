package edu.nf.like.common.confirm;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author wangl
 * @date 2023/12/8
 * 消息可靠投递的回调处理
 * 当投递到交换机失败时，在这个类中处理后续逻辑
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ConfirmCallbackContext {

    private final RabbitTemplate rabbitTemplate;

    private final Map<String, ConfirmCallbackService> confirmCallbackServiceMap;

    @PostConstruct
    public void confirmCallback() {
        rabbitTemplate.setConfirmCallback(((correlationData, ack, cause) -> {
            ReturnedMessage returnedMessage = correlationData.getReturned();
            if (ack) {
                log.info("The message was delivered to the " + returnedMessage.getExchange());
            } else {
                //投递失败后的处理
                ConfirmCallbackService service = confirmCallbackServiceMap.get(returnedMessage.getReplyText());
                service.confirmCallback(returnedMessage.getMessage());
            }
        }));
    }
}
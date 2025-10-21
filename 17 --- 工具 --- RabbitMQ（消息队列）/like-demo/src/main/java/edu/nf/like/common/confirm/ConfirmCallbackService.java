package edu.nf.like.common.confirm;

import org.springframework.amqp.core.Message;

/**
 * @author wangl
 * @date 2023/12/11
 */
public interface ConfirmCallbackService {

    /**
     * 消息投递失败后的处理
     * @param message 失败处理的消息
     */
    void confirmCallback(Message message);
}
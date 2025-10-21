package com.example.ch10.service;

import com.example.ch10.dto.Message;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;

//  消息发布服务
@Service
public class PublishService {
    private SimpMessagingTemplate template;

    public PublishService(SimpMessagingTemplate template) {
        this.template = template;
    }

    // 消息投递
    public void sendTopicMessage(String message) {
        Message msg = new Message();
        msg.setContent(message);
        msg.setSendDate(new Date());
        // 将消息发布到Broker中
        // /topic是配置前缀，/message是自定义的路径
        template.convertAndSend("/topic/message", msg);
    }

    public void sendSportMessage(String message) {
        Message msg = new Message();
        msg.setContent(message);
        msg.setSendDate(new Date());
        template.convertAndSend("/sport/message", msg);
    }
}

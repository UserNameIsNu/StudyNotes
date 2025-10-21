package com.example.ch10.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
// 启用Spring的消息代理（Broker）（算是Spring提供的极简小垃圾消息队列）
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    // 配置消息代理，用于订阅者和发布者使用
    // 通过传入的Broker注册器来注册一个Broker
    // registry参数就是Broker注册器
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 创建Spring内置的简单消息队列并设置一个主题前缀（这个队列的名字（队列是有多个的））
        // 用于消息的订阅和发布
        // 一个队列可以有多个主题
        registry.enableSimpleBroker("/topic", "/sport");
    }

    // 注册STOMP连接端点（STOMP是包裹WebSocket的一个规范协议，用于标准化与规范化WebSocket）
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/message-broker");
    }
}

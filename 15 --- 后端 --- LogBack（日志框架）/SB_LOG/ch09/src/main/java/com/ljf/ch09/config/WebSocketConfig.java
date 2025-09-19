package com.ljf.ch09.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
public class WebSocketConfig {
    // 装配这玩意，可以自动注册使用了@ServerEndpoint标注的类至IOC中
    // 不过@ServerEndpoint只在内嵌容器中才要配置（如SpringBoot这种把汤姆🐱吃进肚子里的玩意）
    // 外部容器，如自己配置的🐱就不需要配置这玩意
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}

package com.example.ch13_homework.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitPushConfig {
    public final static String PUSH_EXCHANGE_NAME = "push.exchange";
    public final static String PUSH_QUEUE_NAME = "push.queue";
    public final static String PUSH_ROUTING_KEY = "push.key";

    @Bean
    public Exchange pushExchange() {
        return new DirectExchange(PUSH_EXCHANGE_NAME);
    }

    @Bean
    public Queue pushQueue() {
        return new Queue(PUSH_QUEUE_NAME, false);
    }

    @Bean
    public Binding pushBinding() {
        return BindingBuilder.bind(pushQueue())
                .to(pushExchange())
                .with(PUSH_ROUTING_KEY)
                .noargs();
    }
}

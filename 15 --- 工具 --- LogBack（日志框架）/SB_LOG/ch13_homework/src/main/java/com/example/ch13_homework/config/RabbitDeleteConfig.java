package com.example.ch13_homework.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitDeleteConfig {
    public final static String DEL_EXCHANGE_NAME = "del.exchange";
    public final static String DEL_QUEUE_NAME = "del.queue";
    public final static String DEL_ROUTING_KEY = "del.key";

    @Bean
    public Exchange delExchange() {
        return new DirectExchange(DEL_EXCHANGE_NAME);
    }

    @Bean
    public Queue delQueue() {
        return new Queue(DEL_QUEUE_NAME, false);
    }

    @Bean
    public Binding delBinding() {
        return BindingBuilder.bind(delQueue())
                .to(delExchange())
                .with(DEL_ROUTING_KEY)
                .noargs();
    }
}

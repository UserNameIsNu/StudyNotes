package com.example.ch13_homework.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitUpdateConfig {
    public final static String UPD_EXCHANGE_NAME = "upd.exchange";
    public final static String UPD_QUEUE_NAME = "upd.queue";
    public final static String UPD_ROUTING_KEY = "upd.key";

    @Bean
    public Exchange updExchange() {
        return new DirectExchange(UPD_EXCHANGE_NAME);
    }

    @Bean
    public Queue updQueue() {
        return new Queue(UPD_QUEUE_NAME, false);
    }

    @Bean
    public Binding updBinding() {
        return BindingBuilder.bind(updQueue())
                .to(updExchange())
                .with(UPD_ROUTING_KEY)
                .noargs();
    }
}

package com.example.ch08.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    // 常规组
    public static final String EXCHANGE_NAME = "test.exchange";
    public static final String QUEUE_NAME = "test.queue";
    public static final String ROUTING_KEY = "test.key";
//    @Bean
//    public Exchange exchange() {
//        return new DirectExchange(EXCHANGE_NAME);
//    }
//    @Bean
//    public Queue queue() {
//        // 创建一个不持久化的队列
//        return QueueBuilder.nonDurable(QUEUE_NAME)
//                // 设置自动删除
//                // .autoDelete()
//                // 与死信交换机关联
//                .withArgument("x-dead-letter-exchange", DEAD_EXCHANGE_NAME)
//                // 设置死信路由Key
//                .withArgument("x-dead-letter-routing-key", DEAD_ROUTING_KEY)
//                .build();
//    }
//    @Bean
//    public Binding binding() {
//        return BindingBuilder.bind(queue())
//                .to(exchange())
//                .with(ROUTING_KEY)
//                .noargs();
//    }



    // 死信组
    public static final String DEAD_EXCHANGE_NAME = "dead.exchange";
    public static final String DEAD_QUEUE_NAME = "dead.queue";
    public static final String DEAD_ROUTING_KEY = "dead.key";
//    @Bean
//    public Exchange deadExchange() {
//        return new DirectExchange(DEAD_EXCHANGE_NAME);
//    }
//    @Bean
//    public Queue deadQueue() {
//        return new Queue(DEAD_QUEUE_NAME);
//    }
//    @Bean
//    public Binding deadBinding() {
//        return BindingBuilder.bind(deadQueue())
//                .to(deadExchange())
//                .with(DEAD_ROUTING_KEY)
//                .noargs();
//    }
}

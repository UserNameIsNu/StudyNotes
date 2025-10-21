package edu.nf.like.common.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wangl
 * @date 2023/12/6
 */
@Configuration
public class RabbitConfig {

    public static final String EXCHANGE_NAME = "test.exchange";
    public static final String QUEUE_NAME = "test.queue";
    public static final String ROUTER_KEY = "key.test";

    /**
     * 装配交换机
     */
    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    /**
     * 装配队列
     * @return Queue
     */
    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME, false);
    }

    /**
     * 将队列绑定到交换机
     * @return Binding
     */
    @Bean
    public Binding binding() {
        return BindingBuilder
                .bind(queue())
                .to(exchange())
                .with(ROUTER_KEY);
    }

    /**
     * 装配自定义消息转换器
     * @return MessageConverter
     */
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
package edu.nf.ch06.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wangl
 * @date 2025/10/14
 */
@Configuration
public class RabbitConfig {

    @Bean
    public Exchange exchange() {
        return new DirectExchange("test.exchange");
    }

    @Bean
    public Queue queue() {
        return new Queue("test.queue", false);
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue())
                .to(exchange())
                .with("key.test")
                .noargs();
    }
}
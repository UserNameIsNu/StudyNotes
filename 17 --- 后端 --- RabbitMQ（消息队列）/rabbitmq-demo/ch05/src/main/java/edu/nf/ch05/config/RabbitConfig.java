package edu.nf.ch05.config;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wangl
 * @date 2025/10/11
 * 方式一：
 * 在配置类中装配交换机、队列还有绑定
 */
@Configuration
public class RabbitConfig {
    /**
     * 交换机名称
     */
    public final static String EXCHANGE_NAME = "order.exchange";
    /**
     * 队列的名称
     */
    public final static String QUEUE_NAME = "order.queue";
    /**
     * 路由的key
     */
    public final static String ROUTING_KEY = "order.key";

    /**
     * 装配Direct类型的交换机
     */
    /*@Bean
    public Exchange exchange() {
        // return new TopicExchange(EXCHANGE_NAME);
        // return new FanoutExchange(EXCHANGE_NAME);
        return new DirectExchange(EXCHANGE_NAME);
    }*/

    /**
     * 装配队列
     */
    /*@Bean
    public Queue queue() {
        //第二个参数表示队列是否持久化，true为持久化
        return new Queue(QUEUE_NAME, false);
    }*/

    /**
     * 将队列和交换机进行绑定
     */
    /*@Bean
    public Binding binding() {
        return BindingBuilder.bind(queue())
                .to(exchange())
                .with(ROUTING_KEY)
                .noargs();
    }*/


    /**
     * 替换默认的SimpleMessageConverter序列化器
     */
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}
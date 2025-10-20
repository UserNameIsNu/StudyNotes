package edu.nf.ch08.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wangl
 * @date 2025/10/16
 */
//@Configuration
public class RabbitConfig {
    //普通的换机、队列、路由key
    public static final String EXCHANGE_NAME = "test.exchange";
    public static final String QUEUE_NAME = "test.queue";
    public static final String ROUTING_KEY = "test.key";
    //死信交换机、队列、路由key
    public static final String DEAD_EXCHANGE_NAME = "dead.exchange";
    public static final String DEAD_QUEUE_NAME = "dead.queue";
    public static final String DEAD_ROUTING_KEY = "dead.key";

   /* *//**
     * 声明普通交换机
     * @return
     *//*
    @Bean
    public Exchange exchange() {
        return new DirectExchange(EXCHANGE_NAME, false, true);
    }

    *//**
     *  声明普通的队列，并且当重试失败后将消息自动投递到死信交换机
     *//*
    @Bean
    public Queue queue() {
        //nonDurable方法创建不持久化的交换机，如果需要持久化则调用durable方法
        return QueueBuilder.nonDurable(QUEUE_NAME)
                //自动删除
                //.autoDelete()
                //通过参数设置和死信交换机关联
                .withArgument("x-dead-letter-exchange", DEAD_EXCHANGE_NAME)
                //设置死信队列路由key
                .withArgument("x-dead-letter-routing-key", DEAD_ROUTING_KEY)
                .build();
    }

    *//**
     * 绑定普通交换机和队列
     *//*
    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue())
                .to(exchange())
                .with(ROUTING_KEY)
                .noargs();
    }

    *//**
     * 声明死信交换机（DLX），死信交换机也可以是Direct或者是其他类型
     *//*
    @Bean
    public Exchange deadExchange() {
        return new DirectExchange(DEAD_EXCHANGE_NAME, false, true);
    }

    *//**
     * 死信队列
     *//*
    @Bean
    public Queue deadQueue() {
        return new Queue(DEAD_QUEUE_NAME, false);
    }

    *//**
     * 绑定死信队列和交换机
     * @return
     *//*
    @Bean
    public Binding deadBinding() {
        return BindingBuilder.bind(deadQueue())
                .to(deadExchange())
                .with(DEAD_ROUTING_KEY)
                .noargs();
    }*/
}
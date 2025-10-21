package edu.nf.seckill.common.configure;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wangl
 * @date 2024/11/26
 */
@Configuration
public class RabbitConfigure {
    /**
     * 延迟交换机
     */
    public static final String EXCHANGE_NAME = "delay.exchange";
    /**
     * 延迟队列
     */
    public static final String QUEUE_NAME = "delay.queue";
    /**
     * 路由key
     */
    public static final String ROUTING_KEY = "delay.key";
    /**
     * 延迟时间
     */
    public static final Long DELAY_TIME = 30000L;

    /**
     * 自定义延迟交换机
     * @return
     */
    @Bean
    public CustomExchange exchange() {
        Map<String, Object> params = new HashMap<>();
        params.put("x-delayed-type", "direct");
        return new CustomExchange(EXCHANGE_NAME, "x-delayed-message", false, false, params);
    }

    /**
     * 装配Queue
     * @return
     */
    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME, false);
    }

    /**
     * 绑定交换机和队列
     * @return
     */
    @Bean
    public Binding binding() {
        return BindingBuilder
                .bind(queue())
                .to(exchange())
                .with(ROUTING_KEY)
                .noargs();
    }
}
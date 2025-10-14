package edu.nf.ch04;

import com.rabbitmq.client.*;

import java.nio.charset.StandardCharsets;

/**
 * @author wangl
 * @date 2025/10/10
 */
public class ConsumerB {
    /**
     * 声明队列
     */
    private final static String QUEUE_TWO = "queue.two";
    /**
     * 声明交换机
     */
    private final static String EXCHANGE_NAME = "my.fanout";

    private static ConnectionFactory connectionFactory;

    static{
        connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("wangl");
        connectionFactory.setPassword("wangl");
    }

    /**
     * 接收消息
     */
    public static void receive() {
        try {
            Connection conn =  connectionFactory.newConnection();
            Channel channel = conn.createChannel();
            //声明交换机
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
            //声明队列
            channel.queueDeclare(QUEUE_TWO, false, false, false, null);
            //绑定时，路由键可以是任意的字符串（通常是空字符串）
            // 因为FANOUT会忽略路由key
            channel.queueBind(QUEUE_TWO, EXCHANGE_NAME, "");
            //接收消息
            DeliverCallback callback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println(message);
            };
            channel.basicConsume(QUEUE_TWO, callback, consumerTag -> {});
        }catch (Exception e){
            throw new RuntimeException("消费失败",e);
        }
    }

    public static void main(String[] args) {
        receive();
    }
}
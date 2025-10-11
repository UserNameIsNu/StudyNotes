package edu.nf.ch02;

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
    private final static String EXCHANGE_NAME = "my.direct";

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
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
            //声明队列
            channel.queueDeclare(QUEUE_TWO, false, false, false, null);
            //将交换机和队列结合路由的KEY进行绑定
            //参数三：路由的KEY
            channel.queueBind(QUEUE_TWO, EXCHANGE_NAME, "my.key");
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
package edu.nf.ch01;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.nio.charset.StandardCharsets;

/**
 * @author wangl
 * @date 2025/10/9
 * 消费者
 */
public class Consumer {
    /**
     * 队列名称
     */
    private static final String QUEUE_NAME = "test";
    /**
     * 连接工厂
     */
    private static final ConnectionFactory connectionFactory;

    /**
     * 初始化连接工厂
     */
    static {
        connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        //注意：5672是客户端访问端口，15672是控制台页面的访问端口
        connectionFactory.setPort(5672);
    }

    /**
     * 接收消息
     *
     * @throws Exception
     */
    public static void receive() {
        try {
            //创建连接
            Connection conn = connectionFactory.newConnection();
            //使用连接对象构建一个消息通信的通道（Channel）
            Channel channel = conn.createChannel();
            //创建队列（如果队列存在则不会再创建）
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            //创建一个接收消息所需要的回调处理器
            //consumerTag是消费者的唯一标识（消费者的ID）
            //delivery消息的载体
            DeliverCallback callback = (consumerTag, delivery) -> {
                //获取消息(消息队列中的内容是字节数组，因此转换为String)
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                //System.out.println(consumerTag);
                System.out.println(message);
            };
            //订阅消息
            //参数一：队列名称
            //参数二：是否自动签收（true表示自动签收）
            //       自动签收就是消息处理完后会自动给rabbitmq回馈一个消息表示处理完毕
            //参数三：消息的回调处理器（处理消息的核心逻辑）
            //参数四：消息这取消订阅时的回调处理器，
            //       会传入一个consumerTag
            channel.basicConsume(QUEUE_NAME, true, callback, consumerTag -> {
            });
        } catch (Exception e) {
            throw new RuntimeException("消费异常", e);
        }
    }

    public static void main(String[] args) {
        receive();
    }
}
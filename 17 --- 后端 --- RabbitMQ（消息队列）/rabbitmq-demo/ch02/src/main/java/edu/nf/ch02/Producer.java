package edu.nf.ch02;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @author wangl
 * @date 2025/10/10
 */
public class Producer {

    /**
     * 只能给定义direct类型的交换机的名称
     */
    private final static String EXCHANGE_NAME = "my.direct";
    /**
     * 路由的KEY
     */
    private final static String ROUTER_KEY = "my.key";
    /**
     * 连接工厂
     */
    private static ConnectionFactory connectionFactory;

    static {
        connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("wangl");
        connectionFactory.setPassword("wangl");
    }


    /**
     * 发布消息
     * @param message
     */
    public static void send(String message) {
        //创建连接和通信通道
        try(Connection conn = connectionFactory.newConnection();
            Channel channel = conn.createChannel()) {
            //创建交换机并指定类型
            channel.exchangeDeclare(EXCHANGE_NAME,  BuiltinExchangeType.DIRECT);
            //投递消息到交换机
            //参数一：交换机名称
            //参数二：路由的key
            //参数三：交换机其他的属性设置
            //参数四：消息内容（二进制）
            channel.basicPublish(EXCHANGE_NAME, ROUTER_KEY, null, message.getBytes());
        }catch (Exception e) {
            throw new RuntimeException("投递失败",e);
        }
    }

    public static void main(String[] args) {
        send("Hello world!");
    }
}
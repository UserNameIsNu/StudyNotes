package edu.nf.ch01;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @author wangl
 * @date 2025/10/9
 * 消息生产者
 */
public class Producer {

    /**
     * 队列名称
     */
    private static final String  QUEUE_NAME = "test";
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
     * 发送消息
     * @param message
     * @throws Exception
     */
    public static void send(String message) {
        //创建连接
        try(Connection conn = connectionFactory.newConnection();
            //使用连接对象构建一个消息通信的通道（Channel）
            Channel channel = conn.createChannel()) {
            //创建队列
            //参数一：队列名称
            //参数二：队列是否持久化（true表示持久化）
            //参数三：是否排他（true表示排他），排他性指的是
            //       列队只对首次创建的connection可见，
            //       false表示可以被所有connection可见
            //参数四：设置true表示断开连接时自动删除队列
            //参数五：队列的其他属性设置，是一个map集合
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            //投递消息
            //参数一：交换机的名称，设置为""表示为指定交换机的名称，
            //       此时rabbitmq会通过一个默认的交换器来路由消息
            //参数二：队列的名称
            //参数三：消息头的属性信息，如果没有就设置为null
            //参数四：消息内容，必须将消息转换为字节数组再投递
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        } catch (Exception e) {
            throw new RuntimeException("操作失败",e);
        }
    }

    public static void main(String[] args) {
        send("Hello rabbitMQ!");
    }
}
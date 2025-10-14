package edu.nf.ch05.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author wangl
 * @date 2025/10/11
 */
@Service
@Slf4j
public class OrderConsumerService {

    /**
     * 使用@RabbitListener注解进行监听，queues属性指定
     * 要监听的队列名称
     *
     */
    /*@RabbitListener(queues = RabbitmqConfig.QUEUE_NAME)
    public void receive(String message) {
        log.info("处理消息: {}", message);
    }*/


    /**
     *
     * 方式二：
     * 也可以使用@RabbitListener来创建交换机、队列和绑定
     * 注意：当使用bindings时，不需要再指定queues属性
     *
     * 如果是手动签收的模式，可以传入一个Channel参数，
     * 这个是rabbitmq客户端原生的API对象，使用这个对象
     * 来完成ACK的应答
     */
    /*@RabbitListener(bindings = @QueueBinding(
         value = @Queue(name = RabbitmqConfig.QUEUE_NAME, durable = "false"),
         exchange = @Exchange(name = RabbitmqConfig.EXCHANGE_NAME, type = ExchangeTypes.DIRECT),
         key = RabbitmqConfig.ROUTING_KEY
    ))
    public void receive(String message,
                        Channel channel,
                        Map<String, Object> headers) throws IOException {
        log.info("处理消息: {}", message);
        //当手动签收时，需要给rabbitmq回馈条消息，告诉rabbitmq
        //当前的消息处理完毕。需要先从消息头中获取一个签收的标签
        Long tag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        //执行ACK应答,第二个参数用于设置是否批量签收，ture表示批量签收
        channel.basicAck(tag, false);
    }*/
}
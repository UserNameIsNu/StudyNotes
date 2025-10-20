package edu.nf.seckill.manager;

import edu.nf.seckill.common.configure.RabbitConfigure;
import edu.nf.seckill.entity.Order;
import edu.nf.seckill.mapper.GoodsMapper;
import edu.nf.seckill.mapper.OrderMapper;
import edu.nf.seckill.service.GoodsEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author wangl
 * @date 2024/11/26
 * 订单消费者
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DelayOrderConsumer {

    private final OrderMapper orderMapper;
    private final GoodsMapper goodsMapper;
    private final RedisTemplate<String, Object> redisTemplate;
    /**
     * 延迟消费，如果用户在指定的时间未支付，订单将改为失效，并且库存要回填。
     * 订单状态：
     * 0: 未支付
     * 1: 已支付
     * 2: 已失效
     * @param orderId
     */
    @RabbitListener(queues = RabbitConfigure.QUEUE_NAME)
    public void consume(Integer orderId) {
        //查询订单
       Order order = orderMapper.getOrderById(orderId);
       //如果状态为0表示未支付，则将其改为失效状态并回填库存
       if(order.getStatus() == 0) {
           log.info("订单[{}]超时未支付", orderId);
           //修改订单为失效状态
           orderMapper.updateStatus(orderId, 2);
           //回填redis库存
           redisTemplate.opsForValue().increment(GoodsEnum.KEY_PREFIX.value() + order.getGid());
       } else {
           //同步数据库的库存
           goodsMapper.decrStock(orderId);
       }
    }
}
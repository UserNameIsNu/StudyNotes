package edu.nf.seckill.service;

import edu.nf.seckill.common.configure.RabbitConfigure;
import edu.nf.seckill.common.exception.ErrorMessageEnum;
import edu.nf.seckill.common.exception.OrderException;
import edu.nf.seckill.entity.Order;
import edu.nf.seckill.mapper.GoodsMapper;
import edu.nf.seckill.mapper.OrderMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * @author wangl
 * @date 2024/11/26
 */
@Slf4j
@Service("orderService")
@RequiredArgsConstructor
public class SeckillService {

    private final OrderMapper orderMapper;

    private final GoodsMapper goodsMapper;

    private final RabbitTemplate rabbitTemplate;

    private final RedisTemplate<String, Object> redisTemplate;

    private final CacheManager cacheManager;

    /**
     * 缓存预热
     * 将秒杀商品的库存缓存到redis
     */
    @PostConstruct
    private void initStockCache() {
        goodsMapper.listGoods().forEach(goods -> {
            log.info("缓存{}号商品，库存:{}",goods.getGid(), goods.getStock());
            String key = GoodsEnum.KEY_PREFIX.value() + goods.getGid();
            redisTemplate.opsForValue().set(key, goods.getStock());
        });
    }

    /**
     * 秒杀下单
     * @param gid 商品id
     */
    public void placeOrder(int gid) {
        //预扣库存
        decrStock(gid);
        //生成订单
        Integer orderId = createOrder(gid);
        //发送延迟消息
        sendDelayMessage(orderId);
    }

    /**
     * 预扣库存
     * @param gid 商品id
     */
    private void decrStock(int gid) {
        //1. 先判断本地缓存中是否有标记过此商品，如果标记了表示此商品已经售完
        Cache localCache = cacheManager.getCache("localCache");
        if(localCache.get(GoodsEnum.KEY_PREFIX.value() + gid) != null) {
            throw new OrderException(ErrorMessageEnum.SELL_OUT);
        }
        //2. 如果本地缓存没有标记则走redis
        //创建lua访问的key集合
        List<String> keys = Collections.singletonList(GoodsEnum.KEY_PREFIX.value() + gid);
        //创建RedisScript并指定lua脚本文件位置
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("lua/stock.lua")));
        //设置lua执行后返回的数据类型
        redisScript.setResultType(Long.class);
        //执行lua脚本并传入keys集合参数
        Long stock = redisTemplate.execute(redisScript, keys);
        //如果库存为-1则抛异常
        if(stock < 0) {
            //标记到本地缓存
            localCache.put(GoodsEnum.KEY_PREFIX.value() + gid, true);
            throw new OrderException(ErrorMessageEnum.SELL_OUT);
        }
    }

    /**
     * 创建订单
     * @param gid
     * @return
     */
    private Integer createOrder(int gid) {
        try {
            Order order = new Order();
            order.setUserId(1);
            order.setGid(gid);
            order.setStatus(0);
            orderMapper.createOrder(order);
            return order.getOrderId();
        } catch (Exception e) {
            //下单失败要将redis库存回填
            redisTemplate.opsForValue().increment(GoodsEnum.KEY_PREFIX.value() + gid);
            throw new OrderException(ErrorMessageEnum.PLACE_ORDER_FAIL);
        }
    }

    /**
     * 发送延迟消息
     * @param orderId 订单id
     */
    private void sendDelayMessage(Integer orderId) {
        //消息ID
        CorrelationData cdata = new CorrelationData();
        cdata.setId(UUID.randomUUID().toString());
        //发送
        rabbitTemplate.convertAndSend(RabbitConfigure.EXCHANGE_NAME,
                RabbitConfigure.ROUTING_KEY, orderId,
                messageSourceProcessor -> {
                    //设置延迟的时间
                    messageSourceProcessor.getMessageProperties()
                            .setDelayLong(RabbitConfigure.DELAY_TIME);
                    return messageSourceProcessor;
                }, cdata);
    }
}
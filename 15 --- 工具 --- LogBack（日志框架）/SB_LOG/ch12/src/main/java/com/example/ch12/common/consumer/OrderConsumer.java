package com.example.ch12.common.consumer;

import com.example.ch12.common.config.RabbitConfig;          // RabbitMQ 队列/交换机配置
import com.fasterxml.jackson.databind.ObjectMapper;    // JSON 序列化/反序列化
import org.springframework.amqp.rabbit.annotation.RabbitListener;  // 消费者监听注解
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.core.io.ClassPathResource;             // 加载 Lua 脚本
import org.springframework.data.redis.core.StringRedisTemplate;   // Redis 操作模板
import org.springframework.data.redis.core.script.DefaultRedisScript; // Redis Lua 脚本执行
import org.springframework.stereotype.Component;                  // Spring 组件注解

import java.util.List;
import java.util.Map;

@Component
public class OrderConsumer {

    private final StringRedisTemplate redisTemplate;
    private final CacheManager cacheManager;

    public OrderConsumer(StringRedisTemplate redisTemplate, CacheManager cacheManager) {
        this.redisTemplate = redisTemplate;
        this.cacheManager = cacheManager;
    }

    @RabbitListener(queues = RabbitConfig.ORDER_QUEUE)
    public void handleOrder(String msgJson) {
        try {
            Map<String, Object> msg = new ObjectMapper().readValue(msgJson, Map.class);
            String orderId = (String) msg.get("orderId");
            int goodsId = (Integer) msg.get("goodsId");
            int num = (Integer) msg.get("num");

            // 调用 Redis Lua 扣库存
            DefaultRedisScript<Long> script = new DefaultRedisScript<>();
            script.setLocation(new ClassPathResource("lua/seckill.lua"));
            script.setResultType(Long.class);

            Long result = redisTemplate.execute(
                    script,
                    List.of("goodsZSet", "ordersHash"),
                    String.valueOf(goodsId),
                    orderId,
                    String.valueOf(msg.get("userId")),
                    String.valueOf(num)
            );

            if (result == null || result == 0) {
                Cache soldOutCache = cacheManager.getCache("soldOutCache");
                soldOutCache.put(goodsId, true);
                System.out.println("库存不足:" + orderId);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

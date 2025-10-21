package com.example.ch12.service;

import com.example.ch12.common.config.RabbitConfig;
import com.example.ch12.common.exception.GlobalException;
import com.example.ch12.common.exception.OrderException;
import com.example.ch12.entity.Order;
import com.example.ch12.mapper.GoodsMapper;
import com.example.ch12.mapper.OrderMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.*;

// 需要参与抢购的商品数据先进redis预存（库存用zset，分数为库存，值为商品ID）
// 请求直连redis进行操作
// 下单后redis中对应商品库存减少,在redis中缓存订单
// 支付后redis数据同步至数据库

@Service
public class OrderService {
    private final GoodsMapper goodsMapper;
    private final OrderMapper orderMapper;
    private final StringRedisTemplate stringRedisTemplate;
    private ObjectMapper objectMapper;
    private final RabbitTemplate rabbitTemplate;
    private CacheManager cacheManager;

    public OrderService(GoodsMapper goodsMapper, OrderMapper orderMapper, StringRedisTemplate stringRedisTemplate, ObjectMapper objectMapper, RabbitTemplate rabbitTemplate, CacheManager cacheManager) {
        this.goodsMapper = goodsMapper;
        this.orderMapper = orderMapper;
        this.stringRedisTemplate = stringRedisTemplate;
        this.objectMapper = objectMapper;
        this.rabbitTemplate = rabbitTemplate;
        this.cacheManager = cacheManager;

        initStock(List.of(
                Map.of("goodsId", 1, "stock", 1),
                Map.of("goodsId", 2, "stock", 1),
                Map.of("goodsId", 3, "stock", 1)
        ));
    }

    public void initStock(List<Map<String, Object>> goodsList) {
        String goodsKey = "goodsZSet";

        // 清空原库存
        stringRedisTemplate.delete(goodsKey);
        stringRedisTemplate.delete("ordersHash");

        // 初始化库存
        for (Map<String, Object> goods : goodsList) {
            Integer goodsId = (Integer) goods.get("goodsId");
            Integer stock = (Integer) goods.get("stock");
            stringRedisTemplate.opsForZSet().add(goodsKey, String.valueOf(goodsId), stock);
        }
    }

    /* 初版
    public void placeOrder(int goodsId) {
        // 检查库存
        if (goodsMapper.decrStockById(goodsId) == 0) {
            // 库存不足
            throw new OrderException("库存不足", 1000);
        } else {
            // 创建订单
            orderMapper.addOrder(new Order(null, 0, 1, goodsId));
        }
    }
    */





    /*
    // 允许一个用户下一单，一单买一个
    // 哈希订单，key=用户ID，value=订单对象

    // 下单
    public void placeOrderPro(int goodsId, int userId) {
        // 扣除库存
        if (stringRedisTemplate.opsForZSet().incrementScore("goodsZSet", String.valueOf(goodsId), -1) < 0) {
            // 库存不足
            throw new OrderException("库存不足", 1001);
        } else {
            // 创建订单
            try {
                // key：下单用户ID
                // value：订单对象
                stringRedisTemplate.opsForHash().put("ordersHash", String.valueOf(userId), objectMapper.writeValueAsString(new Order(null, 0, userId, goodsId)));
            } catch (JsonProcessingException e) {
                throw new GlobalException("服务器异常:" + e, 500);
            }
        }
    }

    // 付款
    public void payOrder(int goodsId, int userId) {
        // 获取订单
        Object json = stringRedisTemplate.opsForHash().get("ordersHash", String.valueOf(userId));
        try {
            // 反序列Json
            Order order = objectMapper.readValue(json.toString(), Order.class);
            // 同步数据库订单
            orderMapper.addOrder(order);
            // 同步数据库库存
            goodsMapper.decrStockById(goodsId);
            // 删除订单
            stringRedisTemplate.opsForHash().delete("ordersHash", String.valueOf(userId));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    // 取消
    public void cancelOrder(int goodsId, int userId) {
        // 删除订单
        stringRedisTemplate.opsForHash().delete("ordersHash", String.valueOf(userId));
        // 恢复库存
        stringRedisTemplate.opsForZSet().incrementScore("goodsZSet", String.valueOf(goodsId), 1);
    }
     */





    /*
    // 允许一个用户下多单，每单有多个
    // 哈希订单，key=订单ID，value=订单对象

    // 下单
    public String placeOrderProMax(int goodsId, int userId, int num) {
        // 扣除库存
        if (stringRedisTemplate.opsForZSet().incrementScore("goodsZSet", String.valueOf(goodsId), -num) < 0) {
            // 库存不足
            stringRedisTemplate.opsForZSet().incrementScore("goodsZSet", String.valueOf(goodsId), num);
            throw new OrderException("库存不足", 1001);
        } else {
            // 创建订单
            try {
                // 生成订单ID
                String orderId = UUID.randomUUID().toString();
                // 创建订单（这里生成的订单ID用于redis，订单对象中的订单ID在数据库内产生）
                stringRedisTemplate.opsForHash().put(
                        "ordersHash",
                        orderId,
                        objectMapper.writeValueAsString(new Order(null, goodsId, userId, num)));
                // 返回订单ID
                return orderId;
            } catch (JsonProcessingException e) {
                throw new GlobalException("服务器异常:" + e, 500);
            }
        }
    }

    // 付款
    public void payOrderPro(String orderId) {
        try {
            // 获取订单
            Object json = stringRedisTemplate.opsForHash().get("ordersHash", orderId);
            Order order = objectMapper.readValue(json.toString(), Order.class);
            // 同步数据库订单
            orderMapper.addOrder(order);
            // 同步数据库库存
            goodsMapper.decrStocksById(order.getGoodsId(), order.getNum());
            // 删除订单
            stringRedisTemplate.opsForHash().delete("ordersHash", orderId);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    // 取消
    public void cancelOrderPro(String orderId) {
        try {
            // 获取订单
            Object json = stringRedisTemplate.opsForHash().get("ordersHash", orderId);
            Order order = objectMapper.readValue(json.toString(), Order.class);
            // 获取订单下单商品数量
            int num = order.getNum();
            // 删除订单
            stringRedisTemplate.opsForHash().delete("ordersHash", orderId);
            // 恢复库存
            stringRedisTemplate.opsForZSet().incrementScore("goodsZSet", String.valueOf(order.getGoodsId()), num);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    */





    // MQ前置削峰，caffeine售空拦截，lua原子执行（他妈的卡在单机极限了，并发线程死在200上不去了）

    // 下单
    public String placeOrderProMaxUltra(int goodsId, int userId, int num) {
        Cache soldOutCache = cacheManager.getCache("soldOutCache");

        if (soldOutCache.get(goodsId) != null) {
            System.out.println("商品已售罄（本地缓存）");
            throw new OrderException("商品已售罄（本地缓存）", 1002);
        }

        String orderId = UUID.randomUUID().toString();
        Map<String, Object> msg = new HashMap<>();
        msg.put("orderId", orderId);
        msg.put("goodsId", goodsId);
        msg.put("userId", userId);
        msg.put("num", num);

        try {
            String msgJson = new ObjectMapper().writeValueAsString(msg);
            rabbitTemplate.convertAndSend(
                    RabbitConfig.ORDER_EXCHANGE,
                    RabbitConfig.ORDER_ROUTING_KEY,
                    msgJson
            );
        } catch (JsonProcessingException e) {
            throw new RuntimeException("MQ发送失败", e);
        }

        return orderId;
    }

    // 付款
    public void payOrderProMax(String orderId) {
        try {
            // 获取订单
            Object json = stringRedisTemplate.opsForHash().get("ordersHash", orderId);
            Order order = objectMapper.readValue(json.toString(), Order.class);
            // 同步数据库订单
            orderMapper.addOrder(order);
            // 同步数据库库存
            goodsMapper.decrStocksById(order.getGoodsId(), order.getNum());
            // 删除订单
            stringRedisTemplate.opsForHash().delete("ordersHash", orderId);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    // 取消
    public void cancelOrderProMax(String orderId) {
        try {
            // 获取订单
            Object json = stringRedisTemplate.opsForHash().get("ordersHash", orderId);
            Order order = objectMapper.readValue(json.toString(), Order.class);
            // 获取订单下单商品数量
            int num = order.getNum();
            // 删除订单
            stringRedisTemplate.opsForHash().delete("ordersHash", orderId);
            // 恢复库存
            stringRedisTemplate.opsForZSet().incrementScore("goodsZSet", String.valueOf(order.getGoodsId()), num);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    // 商品售空检查
    private Cache soldOutCache() {
        return cacheManager.getCache("soldOutCache");
    }
}

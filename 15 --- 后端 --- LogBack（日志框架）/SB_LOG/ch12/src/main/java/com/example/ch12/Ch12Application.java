package com.example.ch12;

import com.example.ch12.entity.Goods;
import com.example.ch12.mapper.GoodsMapper;
import com.example.ch12.mapper.OrderMapper;
import com.example.ch12.service.OrderService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;

@SpringBootApplication
@MapperScan("com.example.ch12.mapper")
@EnableCaching
public class Ch12Application {
    public static void main(String[] args) {
        SpringApplication.run(Ch12Application.class, args);
    }
}

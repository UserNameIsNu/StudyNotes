package com.example.ch11;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

@SpringBootTest
@Slf4j
class Ch11ApplicationTests {
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    public Ch11ApplicationTests(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Test
    void testStringRedisTemplate() {
        stringRedisTemplate.opsForValue().set("key2", "value2", 30, TimeUnit.SECONDS);
        String val = stringRedisTemplate.opsForValue().get("key1");
        log.info(val);
    }
}

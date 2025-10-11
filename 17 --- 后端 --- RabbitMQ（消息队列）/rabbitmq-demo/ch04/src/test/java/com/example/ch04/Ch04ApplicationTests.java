package com.example.ch04;

import com.example.ch04.dto.Order;
import com.example.ch04.service.OrderProducerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Ch04ApplicationTests {
    @Autowired
    private OrderProducerService service;

    @Test
    void contextLoads() {
        service.publish("message");
        service.publish(new Order(1, "1"));
    }
}

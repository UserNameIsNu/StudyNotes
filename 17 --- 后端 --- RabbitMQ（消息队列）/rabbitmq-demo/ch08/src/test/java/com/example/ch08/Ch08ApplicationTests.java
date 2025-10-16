package com.example.ch08;

import com.example.ch08.service.ProduceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Ch08ApplicationTests {
    @Autowired
    private ProduceService service;

    @Test
    void contextLoads() {
        service.send("123");
    }

}

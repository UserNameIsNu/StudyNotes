package edu.nf.ch07;

import edu.nf.ch07.service.ProduceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Ch07ApplicationTests {

    @Autowired
    private ProduceService service;

    @Test
    void contextLoads() {
        service.send("Hello World");
    }

}

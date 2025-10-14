package edu.nf.ch06;

import edu.nf.ch06.service.ProduceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Ch06ApplicationTests {

    @Autowired
    private ProduceService service;

    @Test
    void testPublish() {
        //service.send("Hello World");
        service.send2("Hello World");
    }

}

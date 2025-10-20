package edu.nf.ch08;

import edu.nf.ch08.service.ProduceService;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
class Ch08ApplicationTests {

    @Autowired
    private ProduceService service;

    @Test
    void testDeadLetter() {
        service.send("Hello World");
    }

}

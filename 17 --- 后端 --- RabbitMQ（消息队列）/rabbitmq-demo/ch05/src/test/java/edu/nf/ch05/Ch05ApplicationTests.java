package edu.nf.ch05;

import edu.nf.ch05.dto.Order;
import edu.nf.ch05.service.OrderProducerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Ch05ApplicationTests {

    @Autowired
    private OrderProducerService service;

    @Test
    void testPublish() {
        service.publish("Hello World");
        Order order = new  Order(1, "AD4654567");
        service.publish(order);
    }

}

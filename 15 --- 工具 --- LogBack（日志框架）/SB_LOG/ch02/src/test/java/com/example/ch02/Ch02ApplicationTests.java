package com.example.ch02;

import com.example.ch02.entity.Student;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class Ch02ApplicationTests {

    @Autowired
    private Student student;

    @Test
    void contextLoads() {
        log.info("UserId: {}", student.getUserId());
        log.debug("UserName: {}", student.getUserName());
        log.error("UserAge: {}", student.getUserAge());
        log.error("cardNum: {}", student.getCard().getCardNum());
        log.error("phones: {}, {}", student.getPhones()[0], student.getPhones()[1]);
        log.error("score: {}", student.getScore());
        log.error("score: {}", student.getTeachers());
    }

}

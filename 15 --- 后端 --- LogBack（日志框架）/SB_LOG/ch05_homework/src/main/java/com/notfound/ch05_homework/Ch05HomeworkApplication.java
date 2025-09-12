package com.notfound.ch05_homework;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.notfound.ch05_homework.mapper")
public class Ch05HomeworkApplication {

    public static void main(String[] args) {
        SpringApplication.run(Ch05HomeworkApplication.class, args);
    }

}

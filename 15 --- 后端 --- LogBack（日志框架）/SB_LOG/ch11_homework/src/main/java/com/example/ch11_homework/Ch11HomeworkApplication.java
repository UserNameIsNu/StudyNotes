package com.example.ch11_homework;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.ch11_homework.mapper")
public class Ch11HomeworkApplication {

    public static void main(String[] args) {
        SpringApplication.run(Ch11HomeworkApplication.class, args);
    }

}

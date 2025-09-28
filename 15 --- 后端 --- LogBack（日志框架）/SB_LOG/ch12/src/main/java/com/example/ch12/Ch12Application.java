package com.example.ch12;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.ch12.mapper")
public class Ch12Application {

    public static void main(String[] args) {
        SpringApplication.run(Ch12Application.class, args);
    }

}

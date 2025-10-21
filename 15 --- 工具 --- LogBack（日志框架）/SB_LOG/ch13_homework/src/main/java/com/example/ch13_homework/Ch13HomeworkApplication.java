package com.example.ch13_homework;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.ch13_homework.mapper")
public class Ch13HomeworkApplication {

	public static void main(String[] args) {
		SpringApplication.run(Ch13HomeworkApplication.class, args);
	}

}

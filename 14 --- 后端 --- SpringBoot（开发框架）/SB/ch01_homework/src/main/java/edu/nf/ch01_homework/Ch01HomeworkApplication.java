package edu.nf.ch01_homework;

import edu.nf.ch01_homework.mapper.CityMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@MapperScan(basePackages = "edu.nf.ch01_homework.mapper")
public class Ch01HomeworkApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(Ch01HomeworkApplication.class, args);
        System.out.println(run.getBean(CityMapper.class));
    }

}

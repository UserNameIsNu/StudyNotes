package edu.nf.like;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author wangl
 * @date 2023/12/6
 */
@SpringBootApplication
@MapperScan("edu.nf.like.mapper")
public class LikeDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(LikeDemoApplication.class, args);
    }

}

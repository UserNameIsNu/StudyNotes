package com.example.ch02.entity;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

// SB会默认读取application.properties或application.yml这两个文件
// 读取的内容一般就会绑定到具体的java对象里面，用来干点解析配置啥的
// 值绑定：可以用@Value注解实现属性绑定（这种方法说是叫固定值的属性绑定），这个注解使用SPEL表达式绑定yml文件的属性名
// 松散绑定：@ConfigurationProperties就是用来实现松散绑定的，这里只需要指定属性名前缀就行，绑定的java类属性不用精确匹配，在yml中可以根据约定使用驼峰模式（userName）或中短杠（user-name）又或全大写加下划线（USER_NAME）
@Data
@Component
@ConfigurationProperties(prefix="student")
public class Student {
//    @Value("${student.userId}")
    private Integer userId;

//    @Value("${student.userName}")
    private String userName;

//    @Value("${student.age}")
    private Integer userAge;

    private Card card;

    private String[] phones;

    private Map<String, Integer> score;

    private List<Teacher> teachers;
}

package edu.nf.demo.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

// 配置类
@Configuration
// 扫包
@ComponentScan(basePackages = "edu.nf.demo")
// 集中其它配置
@Import({MvcConfig.class, MinioConfig.class})
// 根配置类
public class RootConfig {
}
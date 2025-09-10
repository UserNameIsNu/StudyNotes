package edu.nf.demo.config;

import edu.nf.demo.common.MinioFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 配置类
@Configuration
// MinIO配置类
public class MinioConfig {

    /**
     * 装配MinIO工厂
     * @return
     */
    @Bean
    public MinioFactoryBean minioFactoryBean() {
        MinioFactoryBean factoryBean = new MinioFactoryBean(); // 声明工厂
        factoryBean.setHost("http://192.168.23.90:9000"); // 定义服务地址
        factoryBean.setAccount("admin"); // 定义登录账号
        factoryBean.setPassword("admin12345"); // 定义登录密码
        factoryBean.setBucketName("bucket1"); // 定义目标桶名
        return factoryBean; // MinIO工厂对象
    }
}
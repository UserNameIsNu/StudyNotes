package com.example.ch05.config;

import com.example.ch05.filter.FirstFilter;
import com.example.ch05.filter.MyFilter;
import com.example.ch05.filter.TestFilter;
import org.springframework.boot.web.servlet.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
// 是SB提供的，专门用来扫描servlet组件（servlet，filter，listener等）的玩意
// 不过这种方法，在存在多个过滤器（过滤链）时，无法指定过滤顺序，默认按照过滤器文件名首字母顺序排
//@ServletComponentScan("com.example.ch05.filter")
public class MvcConfig {
    // RegistrationBean：动态注册器
    // 用这玩意注册Servlet组件，可以控制过滤执行顺序
    // 有三种注册器，分别用于servlet，filter，listener
//    ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean<>();
//    ServletListenerRegistrationBean servletListenerRegistrationBean = new ServletListenerRegistrationBean<>();


    @Bean
    public FilterRegistrationBean myFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new MyFilter());
        filterRegistrationBean.setName("myFilter");
        filterRegistrationBean.addUrlPatterns("/*");
        // 这玩意用来给过来过滤器排队，数字越小越先执行
        filterRegistrationBean.setOrder(1);
        return filterRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean testFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new TestFilter());
        filterRegistrationBean.setName("testFilter");
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.setOrder(2);
        return filterRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean firstFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new FirstFilter());
        filterRegistrationBean.setName("firstFilter");
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.setOrder(3);
        return filterRegistrationBean;
    }
}

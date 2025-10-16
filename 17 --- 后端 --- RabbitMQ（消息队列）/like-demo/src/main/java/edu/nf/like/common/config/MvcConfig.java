package edu.nf.like.common.config;

import edu.nf.like.web.interceptor.LikesInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author wangl
 * @date 2023/12/8
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LikesInterceptor())
                .addPathPatterns("/like", "/unlike");
    }
}
package edu.nf.demo.config;

import jakarta.servlet.MultipartConfigElement;
import jakarta.servlet.ServletRegistration;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

// 入口配置类
public class WebConfig extends AbstractAnnotationConfigDispatcherServletInitializer {
    // 根配置加载器
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[0];
    }

    // Web容器加载器
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{RootConfig.class};
    }

    /**
     * 动态注册自定义的Servlet配置
     * 这里为：启用DispatcherServlet上传功能
     * @param registration
     */
    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
        // 创建上传配置对象
        // 参数一：上传的根目录
        // 参数二：限制单个文件上传的大小（单位：字节）
        // 参数三：限制本次请求中所有文件的总大小
        // 参数四：当达到指定大小时，将写入文件
        MultipartConfigElement configElement = new MultipartConfigElement(
                "",
                20971520,
                2097152000,
                2097152);
        // 注册上传配置
        registration.setMultipartConfig(configElement);
        // 容器启动时跟着初始化这个配置
        registration.setLoadOnStartup(0);
    }

    // 请求拦截规则（全部来让Spring看看！）
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}
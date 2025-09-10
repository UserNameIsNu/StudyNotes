package edu.nf.ch01;

//import edu.nf.ch01.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * SB应用的核心启动类
 * SB应用就是直接通过main方法启动，不用像之前的SSM要挂在容器里面跑
 * SpringBootApplication注解是一个复合注解
 * 包含Configuration，ComponentScan，EnableAutoConfiguration等注解
 * 所以这个启动类同时也算是一个配置类，还具备扫描与自动启用配置的功能
 * SB的默认扫描路径从当前启动类所在的包开始扫描，包含所有子包
 * 或者也能通过scanBasePackages属性修改扫描路径（不建议的说）
 * 关于EnableAutoConfiguration，这玩意就是通过导入一个选择器将所有自动配置类加载到Spring容器中，实现自动装配
 * 所以后续想把其它三方包集成到SB中基本就是通过其中的选择器来集成了
 */
@SpringBootApplication
public class Ch01Application {
    /**
    * 运行SB应用（初始化SB）
    * 运行后会返回一个初始化好的容器
    * 这个返回的容器会根据当前的部署环境而改变
    * 如没集成Web环境时就会返回一个普通IOC
    * 如集成了Web环境时就会返回一个WebApplicationContext
    */
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Ch01Application.class, args);
//        UserService service = context.getBean(UserService.class);
//        System.out.println(service);
    }
}
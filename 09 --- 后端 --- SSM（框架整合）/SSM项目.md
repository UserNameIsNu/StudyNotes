[TOC]

# SSM项目

## 1.基础SSM结构

没有对象，业务和数据库连接。

只是一个最基本，最小化的SSM项目框框。

当然也暂时没有Mybatis。

且全部会用配置类，配置文件就先不用了（反正也说是不推荐用配置文件）。

新建Java项目，打上Maven。

进去之后删掉自动生成的src目录。

### Meven配置

首先是项目最高级Meven的配置。

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>org.example</groupId>
    <artifactId>MINIO_test</artifactId>
    <version>1.0-SNAPSHOT</version>
    <packaging>pom</packaging>

    <modules>
        <module>ch01</module>
    </modules>

    <properties>
        <maven.compiler.source>21</maven.compiler.source>
        <maven.compiler.target>21</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>

    <dependencies>
        <!--  提供Servlet的API  -->
        <dependency>
            <groupId>jakarta.servlet</groupId>
            <artifactId>jakarta.servlet-api</artifactId>
            <version>6.1.0</version>
            <scope>provided</scope>
        </dependency>

        <!--  提供JSP的API  -->
        <dependency>
            <groupId>jakarta.servlet.jsp</groupId>
            <artifactId>jakarta.servlet.jsp-api</artifactId>
            <version>3.0.0</version>
            <scope>provided</scope>
        </dependency>

        <!--  Spring的核心IoC/DI容器  -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
            <version>6.2.6</version>
        </dependency>

        <!--  Spring的WebMVC模块  -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-webmvc</artifactId>
            <version>6.2.6</version>
        </dependency>
    </dependencies>
</project>
```

然后在项目内新建模块，再配置这个子模块的Meven。

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.example</groupId>
        <artifactId>MINIO_test</artifactId>
        <version>1.0-SNAPSHOT</version>
    </parent>

    <artifactId>ch01</artifactId>
    <!--  也就加这一句，改一下子模块的打包方式  -->
    <packaging>war</packaging>

    <properties>
        <maven.compiler.source>21</maven.compiler.source>
        <maven.compiler.target>21</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>
</project>
```

### 配置视图解析器

```java
package edu.nf.ch01.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

// 定义为配置类
@Configuration
// 启用MVC注解驱动
@EnableWebMvc
public class MVCConfig implements WebMvcConfigurer {
    // 默认渲染根目录中的html文件
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();
        // 所有需要解析的视图文件均在`/`目录下，以`.html`结尾
        internalResourceViewResolver.setPrefix("/");
        internalResourceViewResolver.setSuffix(".html");
        // 注册解析器配置
        registry.viewResolver(internalResourceViewResolver);
    }

    // 定义使用默认Servlet框架（似乎是tomcat里面的，就不用Spring里面的了）来处理静态资源
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
}
```

### 根配置

```java
package edu.nf.ch01.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

// 定义为配置类
@Configuration
// 扫描指定包下的类
@ComponentScan(basePackages = "edu.nf.ch01")
// 将指定类导入到这里（有其它配置类也一起导入到这里）
@Import({MVCConfig.class})
public class RootConfig {
}
```

### 入口配置

```java
package edu.nf.ch01.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebConfig extends AbstractAnnotationConfigDispatcherServletInitializer {
    // 一般用来加载根配置，这里留空
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[0];
    }

    // 一般用来加载Web容器，这里用来加载根配置
    // 为了避免容器套容器所以把所有配置集中到一起，只声明一个容器
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{RootConfig.class};
    }

    // 拦截所有请求，任何请求都不能绕过spring的检查
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}
```

## 2.整合Mybatis的SSM

### Meven配置

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>org.example</groupId>
    <artifactId>SSM-demo</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <maven.compiler.source>21</maven.compiler.source>
        <maven.compiler.target>21</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>

    <packaging>war</packaging>

    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.11.0</version>
                <configuration>
                    <parameters>true</parameters>
                </configuration>
            </plugin>
        </plugins>
    </build>

    <dependencies>
        <dependency>
            <groupId>com.mysql</groupId>
            <artifactId>mysql-connector-j</artifactId>
            <version>8.3.0</version>
        </dependency>

        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid</artifactId>
            <version>1.2.24</version>
        </dependency>

        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis</artifactId>
            <version>3.5.19</version>
        </dependency>

        <!-- mybatis整合spring的插件 -->
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis-spring</artifactId>
            <version>3.0.4</version>
        </dependency>

        <dependency>
            <groupId>com.github.pagehelper</groupId>
            <artifactId>pagehelper</artifactId>
            <version>6.1.0</version>
        </dependency>

        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-databind</artifactId>
            <version>2.19.0</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-jdbc</artifactId>
            <version>6.2.6</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-tx</artifactId>
            <version>6.2.6</version>
        </dependency>

        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.18.32</version>
            <scope>provided</scope>
        </dependency>

        <dependency>
            <groupId>jakarta.servlet</groupId>
            <artifactId>jakarta.servlet-api</artifactId>
            <version>6.1.0</version>
            <scope>provided</scope>
        </dependency>

        <dependency>
            <groupId>jakarta.servlet.jsp</groupId>
            <artifactId>jakarta.servlet.jsp-api</artifactId>
            <version>3.0.0</version>
            <scope>provided</scope>
        </dependency>

        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
            <version>6.2.6</version>
        </dependency>

        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-webmvc</artifactId>
            <version>6.2.6</version>
        </dependency>
    </dependencies>

</project>
```

### druid配置

这里使用了druid连接池做数据库连接池化管理，也是学个新东西，就不用自己搓一个临时连接了。

这玩意是properties文件咯，装在resource目录下。

```properties
# 数据源
driverClassName = com.mysql.cj.jdbc.Driver
url = jdbc:mysql://localhost:3307/java211?useSSL=false&useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai
username = root
password = root





# 连接池设置

# 最大连接数
maxActive = 200
# 最小空闲连接数
minIdle = 5
# 初始化连接数
initialSize = 5
# 是否缓存 PreparedStatements
poolPreparedStatements = false
# 校验连接对象有效性
validationQuery = select 1
```

### 配置配置类

还是视图解析器，根配置个入口配置三个。

这里为了整合Mybatis再加一个Mybatis配置类就行。

视图解析器：

```java
package edu.nf.ssm.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

// 视图解析器配置类
@Configuration // 标记为配置类
@EnableWebMvc // 启动Mvc核心功能（会覆盖默认配置，如视图解析器，拦截器，静态资源等全都要自己手配）
public class MvcConfig implements WebMvcConfigurer {
    /**
     * 视图解析器
     * @param registry
     */
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();
        internalResourceViewResolver.setPrefix("/"); // 所有需要解析的视图文件均在根目录下（根目录就是webapp）
        internalResourceViewResolver.setSuffix(".html"); // 所有需要解析的视图文件均以`.html`结尾
        registry.viewResolver(internalResourceViewResolver); // 注册解析器
    }

    // 使用 Web 容器默认 Servlet 处理器（对于这个项目就是使用tomcat中的servlet处理静态资源）
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
}
```

Mybatis配置（druid连接池和分页插件都丢到这里一起配置）：

```java
package edu.nf.ssm.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.github.pagehelper.PageInterceptor;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.InputStream;
import java.util.Properties;

// MyBatis配置类
@Configuration // 标记为配置类
@MapperScan("edu.nf.ssm.mapper") // 扫描指定目录下的mapper接口
@EnableTransactionManagement // 开启事务代理
public class MybatisConfig {
    /**
     * 配置Druid数据源连接池
     */
    @Bean(initMethod = "init", destroyMethod = "close")
    public DruidDataSource dataSource() throws Exception {
        Properties prop = new Properties();
        InputStream is = MybatisConfig.class.getClassLoader().getResourceAsStream("druid.properties");
        prop.load(is);
        return (DruidDataSource) DruidDataSourceFactory.createDataSource(prop);
    }

    /**
     * 装配SqlSessionFactoryBean，用于将SqlSessionFactory丢进IOC容器中管理
     * @return
     * @throws Exception
     */
    @Bean
    public SqlSessionFactoryBean sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        //注入数据源
        factoryBean.setDataSource(dataSource());
        //设置实体的别名
        factoryBean.setTypeAliasesPackage("edu.nf.ssm.entity");
        //设置mapper映射配置文件的路径
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        factoryBean.setMapperLocations(resolver.getResources("classpath:/mappers/*.xml"));
        //配置分页插件
        PageInterceptor pageInterceptor = new PageInterceptor();
        //设置分页属性
        Properties properties = new Properties();
        properties.setProperty("helperDialect", "mysql");
        properties.setProperty("supportMethodsArguments", "true");
        properties.setProperty("reasonable", "true");
        pageInterceptor.setProperties(properties);
        //将分页拦截器添加到插件中
        factoryBean.setPlugins(pageInterceptor);
        //启用sql日志
        org.apache.ibatis.session.Configuration conf = new org.apache.ibatis.session.Configuration();
        conf.setLogImpl(StdOutImpl.class);
        factoryBean.setConfiguration(conf);
        return factoryBean;
    }

    /**
     * 装配事务管理器
     */
    @Bean
    public PlatformTransactionManager txManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
```

根配置：

```java
package edu.nf.ssm.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

// 根配置类
@Configuration // 标记为配置类
@ComponentScan(basePackages = "edu.nf.ssm") // 扫描指定目录下的Bean
@Import({MvcConfig.class}) // 导入其它配置类
public class RootConfig {
}
```

入口配置：

```java
package edu.nf.ssm.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

// 入口配置类
public class WebConfig extends AbstractAnnotationConfigDispatcherServletInitializer {
    // 设置主配置类（父容器）
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[0];
    }

    // 设置 mvc 配置类（子容器）
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{RootConfig.class};
    }

    // 设置 DispatcherServlet 映射路径
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}
```

### 建立实体

这个项目试着从数据库查城市列表。

故搓一个City对象即可。

因为有lombok这个好插件，创建对象也是非常之轻松。

没啥特殊需要基本就是两个注释的事。

```java
package edu.nf.ssm.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class City {
    private Integer id;
    private String cityName;
    private String cityCode;
    private String province;
}
```

### 建立Mapper映射

然后就是使用Mybatis建立映射。

Mybatis就是个让JDBC更加方便的插件。

先创建mapper接口。

```java
package edu.nf.ssm.mapper;

import edu.nf.ssm.entity.City;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CityMapper {
    List<City> listCity(
            @Param("pageNum") int pageNum,
            @Param("pageSize") int pageSize);
}
```

再创建mapper映射。

这玩意要放在resource下的mappers目录内。

```xml
<?xml version="1.0" encoding="utf-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="edu.nf.ssm.mapper.CityMapper">
    <!--  基于指定对象创建一个对象模板，并映射字段  -->
    <resultMap id="cityMap" type="edu.nf.ssm.entity.City">
        <id property="id" column="city_id"/>
        <result property="cityName" column="city_name"/>
        <result property="cityCode" column="city_code"/>
        <result property="province" column="province"/>
    </resultMap>

    <!--  定义一条SQL语句，引用一个对象模板用于定义查询字段  -->
    <select id="listCity" resultMap="cityMap">
        select city_id, city_name, city_code, province province from city_info
    </select>
</mapper>
```

### 建立Service层

先定义接口。

```java
package edu.nf.ssm.service;

import com.github.pagehelper.PageInfo;
import edu.nf.ssm.entity.City;

public interface CityService {
    PageInfo<City> listCity(int pageNum, int pageSize);
}
```

再定义实现。

```java
package edu.nf.ssm.service.impl;

import com.github.pagehelper.PageInfo;
import edu.nf.ssm.entity.City;
import edu.nf.ssm.mapper.CityMapper;
import edu.nf.ssm.service.CityService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// 标记这个类为Service层
@Service("cityService")
//事务注解（当前类中的方法都会参与到事务中）
//spring的事务是基于AOP实现的
//rollbackFor属性用于设置当遇到什么异常就进行回滚
//propagation属性用于设置事务的传播级别
@Transactional(rollbackFor = RuntimeException.class,
                propagation = Propagation.REQUIRED)
public class CityServiceImpl implements CityService {

    private CityMapper cityMapper;


    public CityServiceImpl(CityMapper cityMapper) {
        this.cityMapper = cityMapper;
    }

    //只读事务，当设置为readOnly时，其他事务将不能参与写操作
    @Transactional(readOnly = true)
    @Override
    public PageInfo<City> listCity(int pageNum, int pageSize) {
        List<City> cityList = cityMapper.listCity(pageNum, pageSize);
        return new PageInfo<>(cityList);
    }
}
```

### 建立Web（Controller）层

然后定义控制器。

```java
package edu.nf.ssm.web;

import com.github.pagehelper.PageInfo;
import edu.nf.ssm.entity.City;
import edu.nf.ssm.service.CityService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// 标记这个类为Web层
@RestController
public class CityController {
    // 整一个Service实例（当然是要匹配的Service）
    private CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping("/list/city") // 定义URL
    public PageInfo<City> listCity(
            @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return cityService.listCity(pageNum, pageSize);
    }
}
```

### 页面

随便整个html页面，用原生JS请求一下。

在f12理应可以看到返回的数据。

```html
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>主页</title>
    </head>
    <body>
        <h1>欢迎来到主页</h1>
        <button id="loadCitiesBtn">加载城市列表</button>
        <script>
            document.getElementById("loadCitiesBtn").addEventListener("click", function() {
                // 固定页码和页数
                const pageNum = 1;
                const pageSize = 10;

                fetch(`/list/city?pageNum=${pageNum}&pageSize=${pageSize}`)
                    .then(response => response.json())
                    .then(data => {
                        console.log("返回数据:", data);
                    })
                    .catch(error => console.error("请求失败:", error));
            });
        </script>
    </body>
</html>
```


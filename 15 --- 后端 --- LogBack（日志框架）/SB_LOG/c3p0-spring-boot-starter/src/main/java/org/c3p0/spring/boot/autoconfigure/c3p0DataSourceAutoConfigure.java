package org.c3p0.spring.boot.autoconfigure;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import javax.sql.DataSource;
import org.c3p0.spring.boot.autoconfigure.properties.C3p0DataSourceProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.beans.PropertyVetoException;


/**
 * @ConditionalOnClass 用于 ComboPooledDataSource 存在时才初始化 c3p0DataSourceAutoConfigure 这个类
 * @AutoConfigureBefore 用于让 c3p0DataSourceAutoConfigure 优先于SB中的 DataSourceAutoConfiguration 前加载
 * @EnableConfigurationProperties 用于将带有 @ConfigurationProperties 的类纳入SB容器管理
 */
@Configuration
@ConditionalOnClass(ComboPooledDataSource.class)
@AutoConfigureBefore(DataSourceAutoConfiguration.class)
// 前面的是SB的数据源连接属性，后面的是c3p0的连接池属性
 @EnableConfigurationProperties({DataSourceProperties.class, C3p0DataSourceProperties.class})
public class c3p0DataSourceAutoConfigure {
    @Autowired
    private DataSourceProperties dataSourceProperties;

    @Autowired
    private C3p0DataSourceProperties c3p0DataSourceProperties;

    @Bean
    // 容器中不存在这个DataSources的Bean就初始化它
    @ConditionalOnMissingBean
    public DataSource dataSources() throws PropertyVetoException {
        // 初始化c3p0连接池
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setJdbcUrl(dataSourceProperties.getUrl());
        dataSource.setDriverClass(dataSourceProperties.getDriverClassName());
        dataSource.setPassword(dataSourceProperties.getPassword());
        dataSource.setUser(dataSourceProperties.getUsername());
        dataSource.setMaxPoolSize(c3p0DataSourceProperties.getMaxPoolSize());
        dataSource.setMinPoolSize(c3p0DataSourceProperties.getMinPoolSize());
        dataSource.setInitialPoolSize(c3p0DataSourceProperties.getInitialPoolSize());
        dataSource.setMaxIdleTime(c3p0DataSourceProperties.getMaxIdleTime());
        return dataSource;
    }
}

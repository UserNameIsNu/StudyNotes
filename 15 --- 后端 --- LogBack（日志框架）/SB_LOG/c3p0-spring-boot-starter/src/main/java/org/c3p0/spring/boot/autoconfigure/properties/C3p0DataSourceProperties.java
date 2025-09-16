package org.c3p0.spring.boot.autoconfigure.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 绑定yml中配置的数据源与连接池信息
 */
@ConfigurationProperties(prefix = "spring.datasource.c3p0")
public class C3p0DataSourceProperties {
    private Integer maxPoolSize = 200;
    private Integer minPoolSize = 5;
    private Integer initialPoolSize = 5;
    private Integer maxIdleTime = 6000;

    public Integer getMaxPoolSize() {
        return maxPoolSize;
    }

    public void setMaxPoolSize(Integer maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    public Integer getMinPoolSize() {
        return minPoolSize;
    }

    public void setMinPoolSize(Integer minPoolSize) {
        this.minPoolSize = minPoolSize;
    }

    public Integer getInitialPoolSize() {
        return initialPoolSize;
    }

    public void setInitialPoolSize(Integer initialPoolSize) {
        this.initialPoolSize = initialPoolSize;
    }

    public Integer getMaxIdleTime() {
        return maxIdleTime;
    }

    public void setMaxIdleTime(Integer maxIdleTime) {
        this.maxIdleTime = maxIdleTime;
    }
}

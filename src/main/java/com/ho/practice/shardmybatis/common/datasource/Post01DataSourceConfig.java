package com.ho.practice.shardmybatis.common.datasource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class Post01DataSourceConfig {

    @Primary
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.post01")
    public DataSourceProperties post01DataSourceProperties() {
        return new DataSourceProperties();
    }

    @Primary
    @Bean
    public DataSource post01DataSource(@Qualifier("post01DataSourceProperties") DataSourceProperties dataSourceProperties) {
        return DataSourceFactory.getHikariDataSource(dataSourceProperties);
    }

}

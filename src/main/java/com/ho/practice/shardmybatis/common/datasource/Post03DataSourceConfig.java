package com.ho.practice.shardmybatis.common.datasource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class Post03DataSourceConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.post03")
    public DataSourceProperties post03DataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource post03DataSource(@Qualifier("post03DataSourceProperties") DataSourceProperties dataSourceProperties) {
        return DataSourceFactory.getHikariDataSource(dataSourceProperties);
    }

}

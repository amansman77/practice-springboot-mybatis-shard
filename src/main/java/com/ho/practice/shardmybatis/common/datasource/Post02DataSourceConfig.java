package com.ho.practice.shardmybatis.common.datasource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class Post02DataSourceConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.post02")
    public DataSourceProperties post02DataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource post02DataSource(@Qualifier("post02DataSourceProperties") DataSourceProperties dataSourceProperties) {
        return DataSourceFactory.getHikariDataSource(dataSourceProperties);
    }

}

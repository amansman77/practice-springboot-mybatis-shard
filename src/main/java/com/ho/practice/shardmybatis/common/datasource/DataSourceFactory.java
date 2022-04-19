package com.ho.practice.shardmybatis.common.datasource;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;

import javax.sql.DataSource;

public class DataSourceFactory {

    public static DataSource getHikariDataSource(DataSourceProperties dataSourceProperties) {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(dataSourceProperties.getUrl());
        dataSource.setUsername(dataSourceProperties.getUsername());
        dataSource.setPassword(dataSourceProperties.getPassword());
        dataSource.setDriverClassName(dataSourceProperties.getDriverClassName());
        dataSource.setPoolName(dataSourceProperties.getName());
        dataSource.setMinimumIdle(dataSourceProperties.getPool().getMinPoolSize());
        dataSource.setMaximumPoolSize(dataSourceProperties.getPool().getMaxPoolSize());
        return dataSource;
    }

    public static DataSource getDataSource(DataSourceProperties dataSourceProperties) {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(dataSourceProperties.getDriverClassName());
        dataSourceBuilder.url(dataSourceProperties.getUrl());
        dataSourceBuilder.username(dataSourceProperties.getUsername());
        dataSourceBuilder.password(dataSourceProperties.getPassword());
        return dataSourceBuilder.build();
    }
}

package com.ho.practice.shardmybatis.common.datasource;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataSourceProperties {
    private String name;
    private String uniqueResourceName;
    private String username;
    private String password;
    private String url;
    private String driverClassName;
    private Pool pool;

    @Getter
    @Setter
    public static class Pool {
        private int minPoolSize;
        private int maxPoolSize;
    }
}

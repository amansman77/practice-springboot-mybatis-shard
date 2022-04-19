package com.ho.practice.shardmybatis.common.datasource;

public class DataSourceLookupKeyContextHolder {

    private static ThreadLocal<String> dataSourceKey = new ThreadLocal<>();
    public static String get() {
        return dataSourceKey.get();
    }
    public static void set(String dataSourceType) {
        DataSourceLookupKeyContextHolder.dataSourceKey.set(dataSourceType);
    }
    public static void clear() {
        dataSourceKey.remove();
    }

}

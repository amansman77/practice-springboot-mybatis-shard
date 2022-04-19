package com.ho.practice.shardmybatis.common.datasource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
public class RoutingDataSourceConfig {

    @Value("${mybatis.mapper-locations}")
    private String mapperLocations;

    @Bean
    public DataSource routingDataSource(@Qualifier("post01DataSource") DataSource dataSource01,
                                        @Qualifier("post02DataSource") DataSource dataSource02,
                                        @Qualifier("post03DataSource") DataSource dataSource03) {
        AbstractRoutingDataSource routingDataSource = new RoutingDataSource();
        routingDataSource.setDefaultTargetDataSource(dataSource01);

        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put("post01", dataSource01);
        targetDataSources.put("post02", dataSource02);
        targetDataSources.put("post03", dataSource03);
        routingDataSource.setTargetDataSources(targetDataSources);
        routingDataSource.afterPropertiesSet();

        return new LazyConnectionDataSourceProxy(routingDataSource);
    }

    @Bean(name = "routeSqlSessionFactory")
    public SqlSessionFactory sqlSession(@Qualifier("routingDataSource") DataSource routingDataSource) throws Exception {
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(routingDataSource);
        sessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mapperLocations));
        return sessionFactoryBean.getObject();
    }

    @Bean(name = "routeSqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("routeSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean
    public PlatformTransactionManager postTransactionManager(@Qualifier("routingDataSource") DataSource routingDataSource) {
        return new DataSourceTransactionManager(routingDataSource);
    }
}

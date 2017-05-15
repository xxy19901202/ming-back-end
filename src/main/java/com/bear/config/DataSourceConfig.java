package com.bear.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

/**
 * @Description: TODO
 * @author bear
 * @date 2017年5月14日上午1:16:08
 *
 */
@Configuration
@MapperScan(basePackages = "com.bear.mapper", sqlSessionTemplateRef = "primarySqlSessionTemplate")
public class DataSourceConfig {

    @Bean(name = "primaryDataSource")
    @Qualifier("primaryDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.primary")
    public DataSource primaryDataSource() {
        return DataSourceBuilder.create().build();
    }

    // @Bean(name = "secondaryDataSource")
    // @Qualifier("secondaryDataSource")
    // @Primary
    // @ConfigurationProperties(prefix = "spring.datasource.secondary")
    // public DataSource secondaryDataSource() {
    // return DataSourceBuilder.create().build();
    // }

    @Bean(name = "primarySqlSessionFactory")
    @Primary
    public SqlSessionFactory primarySqlSessionFactory(@Qualifier("primaryDataSource") DataSource dataSource)
            throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/mapper/*.xml"));
        return bean.getObject();
    }

    // @Bean(name = "secondarySqlSessionFactory")
    // public SqlSessionFactory
    // secondarySqlSessionFactory(@Qualifier("secondaryDataSource") DataSource
    // dataSource)
    // throws Exception {
    // SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
    // bean.setDataSource(dataSource);
    // bean.setMapperLocations(
    // new
    // PathMatchingResourcePatternResolver().getResources("classpath:mybatis/mapper/*.xml"));
    // return bean.getObject();
    // }

    @Bean(name = "primaryTransactionManager")
    @Primary
    public DataSourceTransactionManager primaryTransactionManager(
            @Qualifier("primaryDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    // @Bean(name = "secondaryTransactionManager")
    // public DataSourceTransactionManager secondaryTransactionManager(
    // @Qualifier("secondaryDataSource") DataSource dataSource) {
    // return new DataSourceTransactionManager(dataSource);
    // }

    @Bean(name = "primarySqlSessionTemplate")
    @Primary
    public SqlSessionTemplate primarySqlSessionTemplate(
            @Qualifier("primarySqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    // @Bean(name = "secondarySqlSessionTemplate")
    // public SqlSessionTemplate secondarySqlSessionTemplate(
    // @Qualifier("secondarySqlSessionFactory") SqlSessionFactory
    // sqlSessionFactory) throws Exception {
    // return new SqlSessionTemplate(sqlSessionFactory);
    // }

}

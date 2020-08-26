package com.example.shardingsphere.config;


import com.alibaba.druid.pool.DruidDataSource;
import io.seata.rm.datasource.DataSourceProxy;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;


@Configuration
@MapperScan(basePackages = MainDataSourceConfig.PACKAGE, sqlSessionTemplateRef = "mainSqlSessionTemplate")
public class MainDataSourceConfig {

    static final String PACKAGE = "com.example.shardingsphere.dao.main";
    static final String MAPPER_LOCATION = "classpath:mapper/main/*.xml";

    @Value("${spring.datasource.main.url}")
    private String url;

    @Value("${spring.datasource.main.username}")
    private String user;

    @Value("${spring.datasource.main.password}")
    private String password;

    @Value("${spring.datasource.main.driver-class-name}")
    private String driverClass;

    @Bean(name = "mainDataSource")
    @Primary
    public DataSource mainDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driverClass);
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean(name = "mainDataSourceProxy")
    public DataSourceProxy goodsdbDataSourceProxy(@Qualifier("mainDataSource") DataSource dataSource) {
        //DataSourceProxy dsproxy = new DataSourceProxy(dataSource,"my_test_tx_group");
        DataSourceProxy dsproxy = new DataSourceProxy(dataSource,"DEFAULT");
        //dsproxy.
        return dsproxy;
    }

    @Bean(name = "mainTransactionManager")
    @Primary
    public DataSourceTransactionManager mainTransactionManager(@Qualifier("mainDataSourceProxy")DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "mainSqlSessionFactory")
    @Primary
    public SqlSessionFactory mainSqlSessionFactory(@Qualifier("mainDataSourceProxy") DataSource dataSource)
            throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(MainDataSourceConfig.MAPPER_LOCATION));
        return sessionFactory.getObject();
    }

    // 创建SqlSessionTemplate
    @Bean(name = "mainSqlSessionTemplate")
    public SqlSessionTemplate goodsdbSqlSessionTemplate(@Qualifier("mainSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}

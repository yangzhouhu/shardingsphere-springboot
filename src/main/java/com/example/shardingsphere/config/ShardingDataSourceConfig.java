package com.example.shardingsphere.config;


import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.shardingsphere.api.config.sharding.KeyGeneratorConfiguration;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.InlineShardingStrategyConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


@Configuration
@MapperScan(basePackages = ShardingDataSourceConfig.PACKAGE, sqlSessionFactoryRef = "shardingSqlSessionFactory")
public class ShardingDataSourceConfig {

    static final String PACKAGE = "com.example.shardingsphere.dao.sharding";
    static final String MAPPER_LOCATION = "classpath:mapper/sharding/*.xml";

    @Value("${spring.shardingsphere.datasource.ds1.url}")
    private String url1;

    @Value("${spring.shardingsphere.datasource.ds1.username}")
    private String username1;

    @Value("${spring.shardingsphere.datasource.ds1.password}")
    private String password1;

    @Value("${spring.shardingsphere.datasource.ds1.driver-class-name}")
    private String driverClass1;

    @Value("${spring.shardingsphere.datasource.ds2.url}")
    private String url2;

    @Value("${spring.shardingsphere.datasource.ds2.username}")
    private String username2;

    @Value("${spring.shardingsphere.datasource.ds2.password}")
    private String password2;

    @Value("${spring.shardingsphere.datasource.ds2.driver-class-name}")
    private String driverClass2;

    @Bean(name = "shardingDataSource")
    public DataSource shardingDataSource() throws SQLException {

//        Map<String, DataSource> dataSourceMap = new HashMap<>();
//        // 配置第一个数据源
//        DruidDataSource dataSource1 = new DruidDataSource();
//        dataSource1.setDriverClassName("com.mysql.cj.jdbc.Driver");
//        dataSource1.setUrl("jdbc:mysql://49.233.87.32:3306/sharding_1?characterEncoding=utf-8");
//        dataSource1.setUsername("root");
//        dataSource1.setPassword("yzsydm");
//        dataSourceMap.put("ds1", dataSource1);
//
//        // 配置第二个数据源
//        DruidDataSource dataSource2 = new DruidDataSource();
//        dataSource2.setDriverClassName("com.mysql.cj.jdbc.Driver");
//        dataSource2.setUrl("jdbc:mysql://49.233.87.32:3306/sharding_2?characterEncoding=utf-8");
//        dataSource2.setUsername("root");
//        dataSource2.setPassword("yzsydm");
//        dataSourceMap.put("ds2", dataSource2);


        Map<String, DataSource> dataSourceMap = new HashMap<>();
        // 配置第一个数据源
        DruidDataSource dataSource1 = new DruidDataSource();
        dataSource1.setDriverClassName(driverClass1);
        dataSource1.setUrl(url1);
        dataSource1.setUsername(username1);
        dataSource1.setPassword(password1);
        dataSourceMap.put("ds1", dataSource1);

        // 配置第二个数据源
        DruidDataSource dataSource2 = new DruidDataSource();
        dataSource2.setDriverClassName(driverClass2);
        dataSource2.setUrl(url2);
        dataSource2.setUsername(username2);
        dataSource2.setPassword(password2);
        dataSourceMap.put("ds2", dataSource2);


        // 配置库表规则
        TableRuleConfiguration orderTableRuleConfig = new TableRuleConfiguration("my_sharding","ds$->{1..2}.my_sharding_$->{1..2}");

        // 配置分库 + 分表策略 + 分布式主键
        orderTableRuleConfig.setDatabaseShardingStrategyConfig(new InlineShardingStrategyConfiguration("user_id", "ds$->{user_id % 2 + 1}"));
        orderTableRuleConfig.setTableShardingStrategyConfig(new InlineShardingStrategyConfiguration("order_id", "my_sharding_$->{order_id % 2 + 1}"));
        orderTableRuleConfig.setKeyGeneratorConfig(new KeyGeneratorConfiguration("SNOWFLAKE", "order_id"));
        // 配置分片规则
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
        shardingRuleConfig.getTableRuleConfigs().add(orderTableRuleConfig);

        // 获取数据源对象
        DataSource dataSource = ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRuleConfig, getProperties());
        return dataSource;
    }

    /**
     * 系统参数配置
     *
     * @return
     */
    private Properties getProperties() {
        Properties shardingProperties = new Properties();
        shardingProperties.put("sql.show", true);
        return shardingProperties;
    }
    @Bean(name = "shardingTransactionManager")
    public DataSourceTransactionManager shardingTransactionManager(@Qualifier("shardingDataSource") DataSource dataSource) throws SQLException {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "shardingSqlSessionFactory")
    public SqlSessionFactory shardingSqlSessionFactory(@Qualifier("shardingDataSource") DataSource dataSource)
            throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver ()
                .getResources(ShardingDataSourceConfig.MAPPER_LOCATION));
        return sessionFactory.getObject();
    }

    // 创建SqlSessionTemplate
    @Bean(name = "shardingSqlSessionTemplate")
    public SqlSessionTemplate shardingSqlSessionTemplate(@Qualifier("shardingSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }


//    DataSource getShardingDataSource() {
//        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
//        shardingRuleConfig.getTableRuleConfigs().add(getOrderTableRuleConfiguration());
//        shardingRuleConfig.getBindingTableGroups().add("order_all");
//        //shardingRuleConfig.setDefaultDatabaseShardingStrategyConfig(new StandardShardingStrategyConfiguration("order_id", new ShardingTableStrategy()));
//        shardingRuleConfig.setDefaultTableShardingStrategyConfig(new StandardShardingStrategyConfiguration("order_id", new ShardingTableStrategy(),new RangeShardingTableStrategy()));
//        try {
//            return ShardingDataSourceFactory.createDataSource(createDataSourceMap(), shardingRuleConfig, new Properties());
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

//    private static KeyGeneratorConfiguration getKeyGeneratorConfiguration() {
//        KeyGeneratorConfiguration result = new KeyGeneratorConfiguration("SNOWFLAKE", "order_id");
//        return result;
//    }
//
//    TableRuleConfiguration getOrderTableRuleConfiguration() {
//        TableRuleConfiguration result = new TableRuleConfiguration("my_sharding", "order.order_all_${0..15}");
//        result.setKeyGeneratorConfig(shardingProperties.getDefaultKeyGenerator());
//        return result;
//    }
//
//    @ConfigurationProperties(prefix = "spring.shardingsphere.datasource.order")
//    Map<String, DataSource> createDataSourceMap() {
//        Map<String, DataSource> result = new HashMap<>();
//        // 配置真实数据源
//        Map<String, Object> dataSourceProperties = new HashMap<>();
//        dataSourceProperties.put("DriverClassName", driverClass);
//        dataSourceProperties.put("jdbcUrl", url);
//        dataSourceProperties.put("username", username);
//        dataSourceProperties.put("password", password);
//        try {
//            DataSource ds = DataSourceUtil.getDataSource("com.alibaba.druid.pool.DruidDataSource", dataSourceProperties);
//            result.put("order", ds);
//        } catch (ReflectiveOperationException e) {
//            e.printStackTrace();
//        }
//        return result;
//    }

}

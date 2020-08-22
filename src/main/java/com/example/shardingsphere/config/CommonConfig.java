package com.example.shardingsphere.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

/**
 * @Classname CommonConfig
 * @Description
 * @Date 2020/8/22 22:04
 * @Author yangzhou
 */
@Configuration
public class CommonConfig {

    @Bean
    public PathMatchingResourcePatternResolver resourcePatternResolver(){
        return new PathMatchingResourcePatternResolver();
    }
}

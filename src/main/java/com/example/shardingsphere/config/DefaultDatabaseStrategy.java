package com.example.shardingsphere.config;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class DefaultDatabaseStrategy implements PreciseShardingAlgorithm<Long> {
    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Long> shardingValue) {
        System.out.println("-------------DefaultDatabaseStrategy begin-----------------");
        // 分片键的值
        Long curValue = shardingValue.getValue();
        Long sharding = curValue % 4;
        String curBase = "";
        if (sharding > 1) {
            curBase = "ds1";
        } else {
            curBase = "ds2";
        }
        System.out.println("-------------DefaultDatabaseStrategy end-----------------");
        return curBase;
    }
}
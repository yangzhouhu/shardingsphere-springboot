package com.example.shardingsphere.config;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class DefaultTablesStrategy implements PreciseShardingAlgorithm<Long> {
    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Long> shardingValue) {
        System.out.println("-------------DefaultDataTableStrategy begin-----------------");
        // 分片键的值
        Long curValue = shardingValue.getValue();
        String curTable = "";
        if (curValue % 2 == 1) {
            curTable = "default_table_1";
        } else {
            curTable = "default_table_2";
        }
        System.out.println("-------------DefaultDataTableStrategy end-----------------");
        return curTable;
    }

}
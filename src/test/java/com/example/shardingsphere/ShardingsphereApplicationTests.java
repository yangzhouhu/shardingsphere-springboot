package com.example.shardingsphere;

import com.example.shardingsphere.dao.main.OrderDOMapper;
import com.example.shardingsphere.dao.sharding.MyShardingOrderDOMapper;
import com.example.shardingsphere.entity.MyShardingOrderDO;
import com.example.shardingsphere.entity.OrderDO;
import org.junit.jupiter.api.Test;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Random;

@SpringBootTest
class ShardingsphereApplicationTests {
    @Autowired
    private MyShardingOrderDOMapper myShardingOrderDOMapper;
    @Autowired
    private OrderDOMapper orderDOMapper;


    @Test
//    @Transactional
    void test2() {
        for (Long i = 5L; i < 10; i++) {
            MyShardingOrderDO myShardingOrderDO = new MyShardingOrderDO();
            myShardingOrderDO.setStatus("主键自增测试" + i);
            myShardingOrderDO.setUserId(i);
            myShardingOrderDO.setOrderNo("第" + i + "单");
            myShardingOrderDOMapper.insertWithGenerator(myShardingOrderDO);

        }
        OrderDO orderDO = new OrderDO();
        orderDO.setStatus("主键自增测试");
        orderDO.setUserId(10);
        orderDO.setOrderNo("第" + new Random().nextInt(1000) + "单");
        orderDO.setOrderId(10L);
        System.out.println(orderDO.getOrderId());
        orderDOMapper.insert(orderDO);
        for (Long i = 100L; i < 101L; i++) {
            MyShardingOrderDO myShardingOrderDO = new MyShardingOrderDO();
            myShardingOrderDO.setStatus("主键自增测试" + i);
            myShardingOrderDO.setUserId(i);
            myShardingOrderDO.setOrderNo("第" + i + "单");
            myShardingOrderDOMapper.insertWithGenerator(myShardingOrderDO);
        }
//        System.out.println(1/0);
    }

}

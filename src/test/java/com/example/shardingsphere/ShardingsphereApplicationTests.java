package com.example.shardingsphere;

import com.example.shardingsphere.dao.main.OrderDOMapper;
import com.example.shardingsphere.dao.sharding.MyShardingOrderDOMapper;
import com.example.shardingsphere.entity.MyShardingOrderDO;
import com.example.shardingsphere.entity.OrderDO;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.shardingsphere.transaction.annotation.ShardingTransactionType;
import org.apache.shardingsphere.transaction.core.TransactionType;
import org.apache.shardingsphere.transaction.core.TransactionTypeHolder;
import org.junit.jupiter.api.Test;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Random;

/**
 * 请使用controller接口测试验证
 */
@SpringBootTest
class ShardingsphereApplicationTests {
//    @Autowired
//    private MyShardingOrderDOMapper myShardingOrderDOMapper;
//    @Autowired
//    private OrderDOMapper orderDOMapper;


//    @Test
//    @GlobalTransactional(timeoutMills = 30000,rollbackFor = Exception.class)
//    void test2() {
////        for (Long i = 0L; i < 10; i++) {
//            MyShardingOrderDO myShardingOrderDO = new MyShardingOrderDO();
//            myShardingOrderDO.setStatus("主键自增测试" + 1);
//            myShardingOrderDO.setUserId(1L);
//            myShardingOrderDO.setOrderNo("第" + 1 + "单");
////            TransactionTypeHolder.set(TransactionType.BASE);
////            myShardingOrdearDOMapper.insertWithGenerator(myShardingOrderDO);
//
////        }
//        OrderDO orderDO = new OrderDO();
//        orderDO.setStatus("主键自增测试");
//        orderDO.setUserId(11);
//        orderDO.setOrderNo("第" + new Random().nextInt(11) + "单");
//        orderDO.setOrderId(11L);
//        System.out.println(orderDO.getOrderId());
//        TransactionTypeHolder.set(TransactionType.BASE);
//        orderDOMapper.insert(orderDO);
//        System.out.println("000000000000000000000000000000000000000000000000000");
//
//        int i =1/0;
//        TransactionTypeHolder.set(TransactionType.BASE);
//        myShardingOrderDOMapper.insertWithGenerator(myShardingOrderDO);
////        int o = i/0;
////        for (Long i = 100L; i < 101L; i++) {
////            MyShardingOrderDO myShardingOrderDO = new MyShardingOrderDO();
////            myShardingOrderDO.setStatus("主键自增测试" + i);
////            myShardingOrderDO.setUserId(i);
////            myShardingOrderDO.setOrderNo("第" + i + "单");
////            TransactionTypeHolder.set(TransactionType.BASE);
////            myShardingOrderDOMapper.insertWithGenerator(myShardingOrderDO);
////        }
////        System.out.println(1/0);
//    }
//
////    @Transactional
////    @ShardingTransactionType(TransactionType.XA)
//    public void test3() {
//        for (Long i = 1L; i < 10; i++) {
//            MyShardingOrderDO myShardingOrderDO = new MyShardingOrderDO();
//            myShardingOrderDO.setStatus("主键自增测试" + i);
//            myShardingOrderDO.setUserId(i);
//            if (i >5L) {
//                int o = 1/0;
//            }
//            myShardingOrderDO.setOrderNo("第" + i + "单");
//            myShardingOrderDOMapper.insertWithGenerator(myShardingOrderDO);
//
//        }
//        OrderDO orderDO = new OrderDO();
//        orderDO.setStatus("主键自增测试");
//        orderDO.setUserId(10);
//        orderDO.setOrderNo("第" + new Random().nextInt(1000) + "单");
//        orderDO.setOrderId(10L);
//        System.out.println(orderDO.getOrderId());
//        orderDOMapper.insert(orderDO);
//        for (Long i = 100L; i < 101L; i++) {
//            MyShardingOrderDO myShardingOrderDO = new MyShardingOrderDO();
//            myShardingOrderDO.setStatus("主键自增测试" + i);
//            myShardingOrderDO.setUserId(i);
//            myShardingOrderDO.setOrderNo("第" + i + "单");
//            myShardingOrderDOMapper.insertWithGenerator(myShardingOrderDO);
//        }
//        System.out.println(1/0);
//    }

}

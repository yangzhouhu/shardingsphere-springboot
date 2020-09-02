package com.example.shardingsphere.controller;

import com.example.shardingsphere.dao.main.OrderDOMapper;
import com.example.shardingsphere.dao.sharding.BroadTableDOMapper;
import com.example.shardingsphere.dao.sharding.DefaultTableDOMapper;
import com.example.shardingsphere.dao.sharding.MyShardingOrderDOMapper;
import com.example.shardingsphere.entity.BroadTableDO;
import com.example.shardingsphere.entity.DefaultTableDO;
import com.example.shardingsphere.entity.MyShardingOrderDO;
import com.example.shardingsphere.entity.OrderDO;
import io.seata.common.util.StringUtils;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.shardingsphere.transaction.core.TransactionType;
import org.apache.shardingsphere.transaction.core.TransactionTypeHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @author 莫虎
 * @Title: TetsController
 * @Description: TODO
 * @date 2020/8/26 16:54
 */
@RestController
@RequestMapping
public class TetsController {
    @Autowired
    private MyShardingOrderDOMapper myShardingOrderDOMapper;
    @Autowired
    private OrderDOMapper orderDOMapper;

    @GlobalTransactional(timeoutMills = 20000,rollbackFor = Exception.class)
    @GetMapping("/test")
    void test2() {
        System.out.println("=================================start===============================");
//        MyShardingOrderDO myShardingOrderDO = new MyShardingOrderDO();
//        myShardingOrderDO.setStatus("主键自增测试" + 1);
//        myShardingOrderDO.setUserId(1L);
//        myShardingOrderDO.setOrderNo("第" + 1 + "单");
//        TransactionTypeHolder.set(TransactionType.BASE);// 每一次操作数据库，都需要加上// 不然就不会当前的seata全局事务管理
//        myShardingOrderDOMapper.insertWithGenerator(myShardingOrderDO);

        MyShardingOrderDO myShardingOrderDO2 = new MyShardingOrderDO();
        myShardingOrderDO2.setStatus("主键自增测试" + 2);
        myShardingOrderDO2.setUserId(1L);
        myShardingOrderDO2.setOrderNo("第" + 2 + "单");
        TransactionTypeHolder.set(TransactionType.BASE);
        myShardingOrderDOMapper.insertWithGenerator(myShardingOrderDO2);
//        int i =1/0;
        OrderDO orderDO = new OrderDO();
        orderDO.setStatus("主键自增测试");
        orderDO.setUserId(11);
        orderDO.setOrderNo("第" + new Random().nextInt(11) + "单");
        orderDO.setOrderId(new Random().nextLong());
        System.out.println(orderDO.getOrderId());
        TransactionTypeHolder.set(TransactionType.BASE);
        orderDOMapper.insert(orderDO);
//        httprequest();
        if (orderDO.getOrderId() % 2 > 0) {
            System.out.println("error=================" + orderDO.getOrderId());
            int i =1/0;
        }
        System.out.println("========================end=======================");
    }

    /**
     * 调用其他服务,验证跨服务的分布式事务，这里就启动两个服务,其中一个调用另一个来实验
     */
    private void httprequest() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        System.out.println("RootContext.getXID():" + RootContext.getXID());
        headers.add("TX_XID".toLowerCase(), RootContext.getXID());
        HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);
        String url = "http://127.0.0.1:8081/test";
        ResponseEntity<String> resEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
        System.out.println("response=============================:" + resEntity.toString());
    }


    @Autowired
    private BroadTableDOMapper broadTableDOMapper;

    /**
     * 测试广播表
     */
    @GlobalTransactional(timeoutMills = 20000,rollbackFor = Exception.class)
    @GetMapping("/test-broad")
    void testBroadTable() {
        BroadTableDO broadTableDO = new BroadTableDO();
        broadTableDO.setCode("TEST");
        broadTableDO.setEnable(true);
        TransactionTypeHolder.set(TransactionType.BASE);
        broadTableDOMapper.insertGenerator(broadTableDO);
        broadTableDO.setId(1L);
        TransactionTypeHolder.set(TransactionType.BASE);
        broadTableDOMapper.insert(broadTableDO);
    }



    @Autowired
    private DefaultTableDOMapper defaultTableDOMapper;
    /**
     * 测试默认分库规则
     */
//    @GetMapping("/test-default")
//    void test3() {
//        DefaultTableDO defaultTableDO = new DefaultTableDO();
//        defaultTableDO.setName("TEST_DEFAULT");
//        defaultTableDO.setEnable(true);
//        defaultTableDOMapper.insertGenerator(defaultTableDO);
//        defaultTableDO.setId(1L);
//        defaultTableDOMapper.insert(defaultTableDO);
//    }

}

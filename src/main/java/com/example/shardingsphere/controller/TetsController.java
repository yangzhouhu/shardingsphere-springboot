package com.example.shardingsphere.controller;

import com.example.shardingsphere.dao.main.OrderDOMapper;
import com.example.shardingsphere.dao.sharding.MyShardingOrderDOMapper;
import com.example.shardingsphere.entity.MyShardingOrderDO;
import com.example.shardingsphere.entity.OrderDO;
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

import java.util.Random;

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

    @GlobalTransactional(timeoutMills = 10000,rollbackFor = Exception.class)
    @GetMapping("/test")
    void test2() {
        System.out.println("=================================start===============================");
        MyShardingOrderDO myShardingOrderDO = new MyShardingOrderDO();
        myShardingOrderDO.setStatus("主键自增测试" + 1);
        myShardingOrderDO.setUserId(1L);
        myShardingOrderDO.setOrderNo("第" + 1 + "单");
        TransactionTypeHolder.set(TransactionType.BASE);
        myShardingOrderDOMapper.insertWithGenerator(myShardingOrderDO);
//        int i =1/0;
        OrderDO orderDO = new OrderDO();
        orderDO.setStatus("主键自增测试");
        orderDO.setUserId(11);
        orderDO.setOrderNo("第" + new Random().nextInt(11) + "单");
        orderDO.setOrderId(11L);
        System.out.println(orderDO.getOrderId());
        TransactionTypeHolder.set(TransactionType.BASE);
        orderDOMapper.insert(orderDO);
//        int i =1/0;
//        httprequest();
        System.out.println("========================end=======================");
    }

    /**
     * 调用其他服务，实现分布式事务管理
     */
    private void httprequest() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        System.out.println("RootContext.getXID():" + RootContext.getXID());
        headers.add("TX_XID".toLowerCase(), RootContext.getXID());
        HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);
        String url = "http://127.0.0.1:8080/home/addorder?orderid=2002&isfail=1";
        ResponseEntity<String> resEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
        System.out.println("response=============================:" + resEntity.toString());

    }
}

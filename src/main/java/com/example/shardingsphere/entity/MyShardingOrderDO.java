package com.example.shardingsphere.entity;

/**
 * @Classname MyShardingOrderDO
 * @Description
 * @Date 2020/8/22 8:00
 * @Author yangzhou
 */
public class MyShardingOrderDO {
    private Long orderId;
    private Long userId;
    private String status;
    private String orderNo;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
}

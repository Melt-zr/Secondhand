package com.trade.triorderservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.trade.triorderservice.domain.dto.OrderDTO;
import com.trade.triorderservice.domain.po.Order;

import java.util.List;

public interface OrderService extends IService<Order> {
    void createOrder(OrderDTO orderDTO);

    void paidOrder(String orderNo);

    void deliveredOrder(String orderNo, String trackingNumber);

    void receivedOrder(String orderNo);

    void cancelOrder(String orderNo);

    List<Order> getListOrders(Integer buyerId);
}

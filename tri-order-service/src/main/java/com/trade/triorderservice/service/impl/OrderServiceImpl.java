package com.trade.triorderservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.trade.triorderservice.domain.dto.OrderDTO;
import com.trade.triorderservice.domain.po.Order;
import com.trade.triorderservice.mapper.OrderMapper;
import com.trade.triorderservice.service.OrderService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void createOrder(OrderDTO orderDTO) {

        /*如果订单已创建，返回已创建*/
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_no", orderDTO.getOrderNo());
        if (this.count(queryWrapper) > 0) {
            throw new RuntimeException("订单已存在");
        }
        Order order = new Order()
                .setBuyerId(orderDTO.getBuyerId())
                .setSellerId(orderDTO.getSellerId())
                .setProductId(orderDTO.getProductId())
                .setTotalPrice(orderDTO.getTotalPrice())
                .setAddressId(orderDTO.getAddressId());
        /*生成订单号UUID*/
        order.setOrderNo(UUID.randomUUID().toString());

        order.setStatus(1);
        order.setPaymentTime(new Date());
        this.save(order);

        /*发送消息给商品服务，商品服务下架商品*/
        sendProductUpdateMessage(orderDTO.getProductId(), "soldOut");
    }

    @Override
    public void paidOrder(String orderNo) {
        /*判断订单是否存在*/
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_no", orderNo);
        if (this.count(queryWrapper) == 0) {
            throw new RuntimeException("订单不存在");
        }
        Order order = new Order()
                .setStatus(2);
        this.update(order, queryWrapper);
    }

    @Override
    public void deliveredOrder(String orderNo, String trackingNumber) {
        /*判断订单是否存在*/
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_no", orderNo);
        if (this.count(queryWrapper) == 0) {
            throw new RuntimeException("订单不存在");
        }
        Order order = new Order()
                .setStatus(3)
                .setTrackingNumber(trackingNumber);
        this.update(order, queryWrapper);
    }

    @Override
    public void receivedOrder(String orderNo) {
        /*判断订单是否存在*/
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_no", orderNo);
        if (this.count(queryWrapper) == 0) {
            throw new RuntimeException("订单不存在");
        }
        Order order = new Order()
                .setStatus(4);
        this.update(order, queryWrapper);
    }

    @Override
    public void cancelOrder(String orderNo) {
        /*判断订单是否存在*/
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_no", orderNo);
        if (this.count(queryWrapper) == 0) {
            throw new RuntimeException("订单不存在");
        }

        Order order = new Order().setStatus(0); // 设置为已取消状态
        this.update(order, queryWrapper);

        /* 订单被取消，商品自动被重新上架 */
        sendProductUpdateMessage(order.getProductId(), "reShelf");
    }

    @Override
    public List<Order> getListOrders(Integer buyerId) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("buyer_id", buyerId);
        return this.list(queryWrapper);
    }


    /**
     * 发送商品更新消息到RabbitMQ
     * @param productId 商品ID
     */
    private void sendProductUpdateMessage(Integer productId, String action) {
        Map<String, Object> message = new HashMap<>();
        message.put("productId", productId);
        message.put("action", action); // 标记为已售出/下架
        message.put("timestamp", System.currentTimeMillis());

        // 设置消息转换器为JSON格式
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());

        // 发送到RabbitMQ
        rabbitTemplate.convertAndSend("product-order-exchange", "product.update", message);
    }

}

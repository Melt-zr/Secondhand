根据你提供的项目结构和现有配置，要实现基于事件驱动和RabbitMQ的订单状态管理功能，可以按照以下步骤进行：

## 1. 完善RabbitMQ配置

你已经在 [RabbitConfig](file://D:\OOP\dev\workspace\Resume_Project\Secondary_Trading_Platform\project\Secondhand\tri-order-service\src\main\java\com\trade\triorderservice\config\RabbitConfig.java#L6-L12) 中声明了一个 `DirectExchange`，接下来需要补充队列和绑定关系：

```java
package com.trade.triorderservice.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    
    // 已有的交换机
    @Bean
    public DirectExchange productAndOrderExchange() {
        return new DirectExchange("product-and-order-exchange");
    }
    
    // 声明队列
    @Bean
    public Queue orderQueue() {
        return new Queue("order.queue", true);
    }
    
    // 绑定队列到交换机
    @Bean
    public Binding orderBinding(Queue orderQueue, DirectExchange productAndOrderExchange) {
        return BindingBuilder.bind(orderQueue).to(productAndOrderExchange).with("order.routing.key");
    }
}
```


## 2. 创建订单实体类

创建订单实体类来表示订单信息：

```java
package com.trade.triorderservice.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("orders")
public class Order {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long productId;
    private Long userId;
    private Integer status; // 0已取消 1已下单未支付 2已支付未发货 3已发货未确认收货 4已确认收货
    private BigDecimal amount;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
```


## 3. 创建Mapper接口

```java
package com.trade.triorderservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.trade.triorderservice.entity.Order;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {
}
```


## 4. 创建Service层

```java
package com.trade.triorderservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.trade.triorderservice.entity.Order;

public interface OrderService extends IService<Order> {
    // 创建订单
    Order createOrder(Long productId, Long userId, BigDecimal amount);
    
    // 更新订单状态
    boolean updateOrderStatus(Long orderId, Integer status);
    
    // 取消订单
    boolean cancelOrder(Long orderId);
}
```
```java
package com.trade.triorderservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.trade.triorderservice.entity.Order;
import com.trade.triorderservice.mapper.OrderMapper;
import com.trade.triorderservice.service.OrderService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
    
    @Override
    public Order createOrder(Long productId, Long userId, BigDecimal amount) {
        Order order = new Order();
        order.setProductId(productId);
        order.setUserId(userId);
        order.setAmount(amount);
        order.setStatus(1); // 已下单未支付
        order.setCreateTime(LocalDateTime.now());
        order.setUpdateTime(LocalDateTime.now());
        this.save(order);
        return order;
    }
    
    @Override
    public boolean updateOrderStatus(Long orderId, Integer status) {
        Order order = this.getById(orderId);
        if (order != null) {
            order.setStatus(status);
            order.setUpdateTime(LocalDateTime.now());
            return this.updateById(order);
        }
        return false;
    }
    
    @Override
    public boolean cancelOrder(Long orderId) {
        return updateOrderStatus(orderId, 0); // 设置为已取消状态
    }
}
```


## 5. 创建消息生产者

```java
package com.trade.triorderservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderMessageProducer {
    
    private final RabbitTemplate rabbitTemplate;
    
    public void sendOrderEvent(String eventType, Object orderData) {
        rabbitTemplate.convertAndSend("product-and-order-exchange", "order.routing.key", 
            Map.of("eventType", eventType, "data", orderData));
        log.info("发送订单事件: {} - 数据: {}", eventType, orderData);
    }
}
```


## 6. 创建Controller

```java
package com.trade.triorderservice.controller;

import com.trade.triorderservice.entity.Order;
import com.trade.triorderservice.service.OrderMessageProducer;
import com.trade.triorderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    
    private final OrderService orderService;
    private final OrderMessageProducer orderMessageProducer;
    
    // 创建订单
    @PostMapping
    public Order createOrder(@RequestParam Long productId, 
                            @RequestParam Long userId,
                            @RequestParam BigDecimal amount) {
        Order order = orderService.createOrder(productId, userId, amount);
        // 发送创建订单事件
        orderMessageProducer.sendOrderEvent("ORDER_CREATED", order);
        return order;
    }
    
    // 更新订单状态
    @PutMapping("/{orderId}/status")
    public boolean updateOrderStatus(@PathVariable Long orderId, @RequestParam Integer status) {
        boolean result = orderService.updateOrderStatus(orderId, status);
        if (result) {
            Order order = orderService.getById(orderId);
            // 发送订单状态更新事件
            orderMessageProducer.sendOrderEvent("ORDER_STATUS_UPDATED", order);
        }
        return result;
    }
    
    // 取消订单
    @DeleteMapping("/{orderId}")
    public boolean cancelOrder(@PathVariable Long orderId) {
        boolean result = orderService.cancelOrder(orderId);
        if (result) {
            Order order = orderService.getById(orderId);
            // 发送订单取消事件
            orderMessageProducer.sendOrderEvent("ORDER_CANCELLED", order);
        }
        return result;
    }
}
```


## 7. 创建消息消费者

```java
package com.trade.triorderservice.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderEventListener {
    
    @RabbitListener(queues = "order.queue")
    public void handleOrderEvent(Map<String, Object> message) {
        String eventType = (String) message.get("eventType");
        Object data = message.get("data");
        
        log.info("接收到订单事件: {} - 数据: {}", eventType, data);
        
        // 根据事件类型处理不同的业务逻辑
        switch (eventType) {
            case "ORDER_CREATED":
                handleOrderCreated(data);
                break;
            case "ORDER_STATUS_UPDATED":
                handleOrderStatusUpdated(data);
                break;
            case "ORDER_CANCELLED":
                handleOrderCancelled(data);
                break;
            default:
                log.warn("未知的订单事件类型: {}", eventType);
        }
    }
    
    private void handleOrderCreated(Object data) {
        // 处理订单创建事件
        log.info("处理订单创建事件: {}", data);
    }
    
    private void handleOrderStatusUpdated(Object data) {
        // 处理订单状态更新事件
        log.info("处理订单状态更新事件: {}", data);
    }
    
    private void handleOrderCancelled(Object data) {
        // 处理订单取消事件
        log.info("处理订单取消事件: {}", data);
    }
}
```


## 8. 数据库表结构

创建订单表：

```sql
CREATE TABLE orders (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    product_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    status INT NOT NULL DEFAULT 1,
    amount DECIMAL(10,2) NOT NULL,
    create_time DATETIME NOT NULL,
    update_time DATETIME NOT NULL
);
```


## 9. 配置文件完善

在 [application.yml](file://D:\OOP\dev\workspace\Resume_Project\Secondary_Trading_Platform\project\Secondhand\tri-api\src\main\resources\application.yml) 中添加RabbitMQ配置：

```yaml
spring:
  # 其他配置...
  rabbitmq:
    host: 192.168.150.128
    port: 5672
    username: your_username
    password: your_password
    virtual-host: /
```


通过以上步骤，你就建立了一个基于事件驱动和RabbitMQ的订单管理系统，可以实现订单的创建、状态更新和取消操作，并通过消息队列实现系统间的解耦。
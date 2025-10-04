package com.tri.common.config;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Qualifier;

@Configuration
public class RabbitConfig {

    private static final Logger logger = LoggerFactory.getLogger(RabbitConfig.class);

    public RabbitConfig() {
        logger.info("RabbitConfig 初始化...");
    }

    @PostConstruct
    public void postConstruct() {
        logger.info("RabbitConfig 已构建完成...");
    }

    /**
     * 消息转换器：JSON序列化
     */
    @Bean
    public MessageConverter messageConverter() {
        logger.info("创建 MessageConverter Bean");
        return new Jackson2JsonMessageConverter();
    }

    // ===================== 产品交换机/队列 =====================

    @Bean("productOrderExchange")
    public DirectExchange productOrderExchange() {
        logger.info("创建 productOrderExchange 交换机");
        return new DirectExchange("product-order-exchange");
    }

    @Bean("productQueue")
    public Queue productQueue() {
        logger.info("创建 productQueue 队列");
        return new Queue("product.queue", true);
    }

    @Bean
    public Binding productBinding(@Qualifier("productQueue") Queue productQueue,
                                  @Qualifier("productOrderExchange") DirectExchange productOrderExchange) {
        logger.info("绑定 productQueue 到 productOrderExchange");
        return BindingBuilder.bind(productQueue).to(productOrderExchange).with("product.update");
    }

    // ===================== 订单交换机/队列 =====================

    @Bean("orderUserExchange")
    public DirectExchange orderUserExchange() {
        logger.info("创建 orderUserExchange 交换机");
        return new DirectExchange("order-user-exchange");
    }

    @Bean("orderQueue")
    public Queue orderQueue() {
        logger.info("创建 orderQueue 队列");
        return new Queue("order.queue", true);
    }

    @Bean
    public Binding orderBinding(@Qualifier("orderQueue") Queue orderQueue,
                                @Qualifier("orderUserExchange") DirectExchange orderUserExchange) {
        logger.info("绑定 orderQueue 到 orderUserExchange");
        return BindingBuilder.bind(orderQueue).to(orderUserExchange).with("orderCredit.update");
    }
}

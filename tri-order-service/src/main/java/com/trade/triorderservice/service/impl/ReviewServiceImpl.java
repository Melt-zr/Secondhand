package com.trade.triorderservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.trade.triorderservice.domain.dto.ReviewDTO;
import com.trade.triorderservice.domain.po.Order;
import com.trade.triorderservice.domain.po.Review;
import com.trade.triorderservice.mapper.ReviewMapper;
import com.trade.triorderservice.service.OrderService;
import com.trade.triorderservice.service.ReviewService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ReviewServiceImpl extends ServiceImpl<ReviewMapper, Review> implements ReviewService {

    @Autowired
    private OrderService orderService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void createReview(ReviewDTO reviewDTO) {
        QueryWrapper<Review> queryWrapper = new QueryWrapper<>();
        QueryWrapper<Order> queryWrapper1 = new QueryWrapper<>();
        queryWrapper.eq("order_id", reviewDTO.getOrderId());
        queryWrapper1.eq("order_id", reviewDTO.getOrderId());
        if (orderService.count(queryWrapper1) == 0) {
            throw new RuntimeException("订单不存在");
        }
        if (this.count(queryWrapper) > 0) {
            throw new RuntimeException("该订单已评价");
        }

        Review review = new Review()
                .setOrderId(reviewDTO.getOrderId())
                .setProductId(reviewDTO.getProductId())
                .setReviewerId(reviewDTO.getReviewerId())
                .setReviewedId(reviewDTO.getReviewedId())
                .setRating(reviewDTO.getRating())
                .setReviewType(reviewDTO.getReviewType())
                .setContent(reviewDTO.getContent());

        this.save(review);
        /*
         * 0 买家对卖家的评价，1 卖家对买家的评价
         * 0 不推荐，1 推荐
         * 更新卖家信用/买家信用
         * */
        // 更新被评价人的信用
        double credit = calculatePositiveRate(reviewDTO.getReviewedId(), reviewDTO.getReviewType());
        updateUserCredit(credit, reviewDTO.getReviewedId(), reviewDTO.getReviewType());
    }

    private double calculatePositiveRate(int userId, int reviewType) {
        // 计算总评价数
        QueryWrapper<Review> totalQuery = new QueryWrapper<>();
        totalQuery.eq("reviewed_id", userId)
                .eq("review_type", reviewType);
        long totalCount = this.count(totalQuery);

        // 计算推荐（rating=1）数量
        QueryWrapper<Review> positiveQuery = new QueryWrapper<>();
        positiveQuery.eq("reviewed_id", userId)
                .eq("review_type", reviewType)
                .eq("rating", 1);
        long positiveCount = this.count(positiveQuery);

        return totalCount > 0 ? (double) positiveCount / totalCount * 100 : 0;
    }

    private void updateUserCredit(double credit, int userId, int reviewType) {
        Map<String,  Object> message = new HashMap<>();
        message.put("credit", credit);
        message.put("userId", userId);
        message.put("reviewType", reviewType);
        rabbitTemplate.convertAndSend("order-user-exchange", "orderCredit.update", message);
    }
}

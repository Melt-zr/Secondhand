package com.trade.triuserservice.listener;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.trade.triuserservice.domain.po.User;
import com.trade.triuserservice.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserCreditUpdateListener {
    private final UserMapper userMapper;

    @RabbitListener(queues = "user.credit.update.queue")
    private void handleCreditUpdateMessage(Map<String, Object> message) {
        try {
            log.info("接收到信用分更新消息: {}", message);

            // 从消息中提取参数
            Double credit = (Double) message.get("credit");
            Integer userId = (Integer) message.get("userId");
            Integer reviewType = (Integer) message.get("reviewType"); // 0: 买家信用, 1: 卖家信用

            // 更新用户信用分
            updateUserCredit(userId, credit, reviewType);

            log.info("用户 {} 的信用分已更新为 {}", userId, credit);
        } catch (Exception e) {
            log.error("处理信用分更新消息时发生错误: ", e);
        }
    }

    private void updateUserCredit(Integer userId, Double credit, Integer reviewType) {
        // 构建更新条件
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", userId);

        User user = new User();
        if (reviewType == 0) {
            // 更新买家信用
            user.setBuyerCredit(credit.intValue());
        } else if (reviewType == 1) {
            // 更新卖家信用
            user.setSellerCredit(credit.intValue());
        }

        userMapper.update(user, queryWrapper);
    }
}

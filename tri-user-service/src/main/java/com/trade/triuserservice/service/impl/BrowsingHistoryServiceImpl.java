package com.trade.triuserservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.trade.triuserservice.domain.po.ViewHistory;
import com.trade.triuserservice.mapper.BrowsingHistoryMapper;
import com.trade.triuserservice.service.BrowsingHistoryService;
import com.trade.triuserservice.utils.UserContext;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BrowsingHistoryServiceImpl extends ServiceImpl<BrowsingHistoryMapper, ViewHistory> implements BrowsingHistoryService {
    @Override
    public void addBrowsingHistory(int productId) {
        ViewHistory viewHistory = new ViewHistory();
        viewHistory.setProductId(productId)
                .setUserId(Integer.parseInt(UserContext.getCurrentUserId()))
                .setViewTime(new Date())
                .setIsDeleted(0);
        this.save(viewHistory);
    }

    @Override
    public List<Integer> getBrowsingHistory() {
        QueryWrapper<ViewHistory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", UserContext.getCurrentUserId())
                .eq("is_deleted", 0);
        return this.list(queryWrapper).stream()
                .map(ViewHistory::getProductId)
                .toList();
    }

    @Override
    public void deleteBrowsingHistory(int productId) {
        QueryWrapper<ViewHistory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("product_id", productId)
                .eq("user_id", Integer.parseInt(UserContext.getCurrentUserId()));
        this.remove(queryWrapper);
    }

    @Override
    public void clearBrowsingHistory() {
        QueryWrapper<ViewHistory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", Integer.parseInt(UserContext.getCurrentUserId()));
        this.remove(queryWrapper);
    }


}

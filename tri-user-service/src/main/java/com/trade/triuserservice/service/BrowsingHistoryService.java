package com.trade.triuserservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.trade.triuserservice.domain.po.ViewHistory;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


public interface BrowsingHistoryService extends IService<ViewHistory> {
    void addBrowsingHistory(int productId);

    List<Integer> getBrowsingHistory();

    void deleteBrowsingHistory(int productId);

    void clearBrowsingHistory();

}

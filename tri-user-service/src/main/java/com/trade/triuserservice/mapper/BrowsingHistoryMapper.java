package com.trade.triuserservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.trade.triuserservice.domain.po.ViewHistory;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BrowsingHistoryMapper extends BaseMapper<ViewHistory> {
}

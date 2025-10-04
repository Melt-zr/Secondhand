package com.trade.triorderservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.trade.triorderservice.domain.po.Order;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {
}

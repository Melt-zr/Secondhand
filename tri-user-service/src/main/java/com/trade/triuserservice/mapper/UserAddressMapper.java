package com.trade.triuserservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.trade.triuserservice.domain.dto.UserAddressDTO;
import com.trade.triuserservice.domain.po.UserAddress;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserAddressMapper extends BaseMapper<UserAddress> {
}

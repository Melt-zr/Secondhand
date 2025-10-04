package com.trade.triuserservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.trade.triuserservice.domain.po.FavoriteUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserFavoritesUserMapper extends BaseMapper<FavoriteUser> {
}

package com.trade.triuserservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.trade.triuserservice.domain.po.FavoriteUser;
import com.trade.triuserservice.domain.po.User;
import com.trade.triuserservice.mapper.UserFavoritesUserMapper;
import com.trade.triuserservice.service.UserFavoritesUserService;
import com.trade.triuserservice.utils.UserContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserFavoritesUserServiceImpl extends ServiceImpl<UserFavoritesUserMapper, FavoriteUser> implements UserFavoritesUserService {
    @Override
    public void addShopFavorites(Integer shopId) {
        FavoriteUser favoriteUser = new FavoriteUser()
                .setUserId(Integer.parseInt(UserContext.getCurrentUserId()))
                .setFavUserId(shopId);
        this.save(favoriteUser);
    }

    @Override
    public void cancelShopFavorites(Integer shopId) {
        QueryWrapper<FavoriteUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("shop_id", shopId)
                .eq("user_id", UserContext.getCurrentUserId());
        this.remove(queryWrapper);
    }

    @Override
    public List<Integer> getShopFavoritesList() {
        QueryWrapper<FavoriteUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", UserContext.getCurrentUserId());
        return this.list(queryWrapper).stream().map(FavoriteUser::getFavUserId).toList();
    }
}

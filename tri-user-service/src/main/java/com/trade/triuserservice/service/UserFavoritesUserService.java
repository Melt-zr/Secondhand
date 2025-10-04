package com.trade.triuserservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.trade.triuserservice.domain.po.FavoriteUser;

import java.util.List;

public interface UserFavoritesUserService extends IService<FavoriteUser> {
    void addShopFavorites(Integer shopId);

    void cancelShopFavorites(Integer shopId);

    List<Integer> getShopFavoritesList();
}

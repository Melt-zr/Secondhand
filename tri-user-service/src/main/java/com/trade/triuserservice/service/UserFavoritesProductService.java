package com.trade.triuserservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.trade.triuserservice.domain.po.FavoriteProduct;

import java.util.List;

public interface UserFavoritesProductService extends IService<FavoriteProduct> {
    void addProductFavorites(Integer productId);

    void cancelProductFavorites(Integer productId);

    List<Integer> getProductFavoritesList();

}

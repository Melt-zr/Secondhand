package com.trade.triuserservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.trade.triuserservice.domain.po.FavoriteProduct;
import com.trade.triuserservice.mapper.UserFavoritesProductMapper;
import com.trade.triuserservice.service.UserFavoritesProductService;
import com.trade.triuserservice.utils.UserContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserFavoritesProductServiceImpl extends ServiceImpl<UserFavoritesProductMapper, FavoriteProduct> implements UserFavoritesProductService {
    @Override
    public void addProductFavorites(Integer productId) {
        FavoriteProduct favoriteProduct = new FavoriteProduct()
                .setProductId(productId)
                .setUserId(1);
        this.save(favoriteProduct);
    }

    @Override
    public void cancelProductFavorites(Integer productId) {
        QueryWrapper<FavoriteProduct> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("product_id", productId)
                .eq("user_id", UserContext.getCurrentUserId());
        this.remove(queryWrapper);
    }

    @Override
    public List<Integer> getProductFavoritesList() {
        QueryWrapper<FavoriteProduct> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", UserContext.getCurrentUserId());
        return this.list(queryWrapper).stream().map(FavoriteProduct::getProductId).toList();
    }
}

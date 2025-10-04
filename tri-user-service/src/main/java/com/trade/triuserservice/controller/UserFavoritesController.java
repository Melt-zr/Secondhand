package com.trade.triuserservice.controller;

import com.trade.triuserservice.domain.vo.ResultVO;
import com.trade.triuserservice.service.UserFavoritesProductService;
import com.trade.triuserservice.service.UserFavoritesUserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favorites")
public class UserFavoritesController {

    private UserFavoritesUserService userFavoritesUserService;
    private UserFavoritesProductService userFavoritesProductService;

    /**
     * 添加商品收藏
     * @Param productId 商品ID
     * @Return 添加结果
     * */
    @PostMapping("/favorites/products/{productId}")
    public ResultVO<String> addProductFavorites(@PathVariable Integer productId) {
        try {
            userFavoritesProductService.addProductFavorites(productId);
            return ResultVO.success("修改成功");
        } catch (RuntimeException e) {
            return ResultVO.error(e.getMessage());
        } catch (Exception e) {
            return ResultVO.error("修改失败，请稍后重试");
        }
    }

    /**
     * 取消商品收藏
     * @Param productId 商品ID
     * @Return 取消结果
     * */
    @DeleteMapping("/favorites/products/{productId}")
    public ResultVO<String> cancelProductFavorites(@PathVariable Integer productId) {
        try {
            userFavoritesProductService.cancelProductFavorites(productId);
            return ResultVO.success("修改成功");
        } catch (RuntimeException e) {
            return ResultVO.error(e.getMessage());
        } catch (Exception e) {
            return ResultVO.error("修改失败，请稍后重试");
        }
    }

    /**
     * 查询商品收藏列表
     * @Param
     * @Return List<Integer> 商品Id列表
     * */
    @GetMapping("/favorites/products/productsList")
    public List<Integer> getProductFavoritesList() {
        return userFavoritesProductService.getProductFavoritesList();
    }

    /**
     * 添加店铺收藏
     * @Param shopId 店铺ID
     * @Return 添加结果
     * */
    @PostMapping("/favorites/shops/{shopId}")
    public ResultVO<String> addShopFavorites(@PathVariable Integer shopId) {
        try {
            userFavoritesUserService.addShopFavorites(shopId);
            return ResultVO.success("修改成功");
        } catch (RuntimeException e) {
            return ResultVO.error(e.getMessage());
        } catch (Exception e) {
            return ResultVO.error("修改失败，请稍后重试");
        }
    }

    /**
     * 取消店铺收藏
     * @Param shopId 店铺ID
     * @Return 取消结果
     * */
    @DeleteMapping("/favorites/shops/{shopId}")
    public ResultVO<String> cancelShopFavorites(@PathVariable Integer shopId) {
        try {
            userFavoritesUserService.cancelShopFavorites(shopId);
            return ResultVO.success("修改成功");
        } catch (RuntimeException e) {
            return ResultVO.error(e.getMessage());
        } catch (Exception e) {
            return ResultVO.error("修改失败，请稍后重试");
        }
    }

    /**
     * 查询店铺收藏列表
     * @Param
     * @Return List<Integer> 店铺Id列表
     * */
    @GetMapping("/favorites/shops/shopsList")
    public List<Integer> getShopFavoritesList() {
        return userFavoritesUserService.getShopFavoritesList();
    }

}

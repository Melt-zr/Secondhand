package com.trade.triuserservice.controller;

import com.trade.triuserservice.domain.vo.ResultVO;
import com.trade.triuserservice.service.UserFavoritesProductService;
import com.trade.triuserservice.service.UserFavoritesUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favorites")
@Tag(name = "用户收藏接口", description = "用户收藏接口")
public class UserFavoritesController {

    private UserFavoritesUserService userFavoritesUserService;
    private UserFavoritesProductService userFavoritesProductService;

    /**
     * 添加商品收藏
     * @Param productId 商品ID
     * @Return 添加结果
     * */
    @Operation(summary = "添加商品收藏", description = "添加商品收藏")
    @PostMapping("/products/{productId}")
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
    @Operation(summary = "取消商品收藏", description = "取消商品收藏")
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
    @Operation(summary = "查询商品收藏列表", description = "查询商品收藏列表")
    @GetMapping("/products/productsList")
    public List<Integer> getProductFavoritesList() {
        return userFavoritesProductService.getProductFavoritesList();
    }

    /**
     * 添加店铺收藏
     * @Param shopId 店铺ID
     * @Return 添加结果
     * */
    @Operation(summary = "添加店铺收藏", description = "添加店铺收藏")
    @PostMapping("/shops/{shopId}")
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
    @Operation(summary = "取消店铺收藏", description = "取消店铺收藏")
    @DeleteMapping("/shops/{shopId}")
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
    @Operation(summary = "查询店铺收藏列表", description = "查询店铺收藏列表")
    @GetMapping("/shops/shopsList")
    public List<Integer> getShopFavoritesList() {
        return userFavoritesUserService.getShopFavoritesList();
    }

}

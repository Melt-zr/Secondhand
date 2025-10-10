package com.trade.triuserservice.controller;

import com.trade.triuserservice.domain.vo.ResultVO;
import com.trade.triuserservice.service.BrowsingHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/browsing-history")
@Tag(name = "浏览记录管理", description = "浏览记录相关接口")
public class BrowsingHistoryController {

    @Autowired
    private BrowsingHistoryService browsingHistoryService;

    /**
     * 添加浏览记录
     * @Param productId 商品ID
     * @Return 添加结果
     * */
    @Operation(summary = "添加浏览记录", description = "添加浏览记录")
    @PostMapping("/add/{productId}")
    public ResultVO<String> addBrowsingHistory(@Validated @PathVariable ("productId") int productId) {
        try {
            browsingHistoryService.addBrowsingHistory(productId);
            return ResultVO.success("修改成功");
        } catch (RuntimeException e) {
            return ResultVO.error(e.getMessage());
        } catch (Exception e) {
            return ResultVO.error("修改失败，请稍后重试");
        }
    }

    /**
     * 获取浏览记录
     * @Param userId 用户ID
     * @Return 浏览记录中的商品Id的List
     * */
    @Operation(summary = "获取浏览记录", description = "获取浏览记录")
    @GetMapping("/get")
    public List<Integer> getBrowsingHistory() {
        return browsingHistoryService.getBrowsingHistory();
    }

    /**
     * 删除单条浏览记录
     * @Param productId 商品ID
     * @Param userId 用户ID
     * @Return 删除结果
     * */
    @Operation(summary = "删除单条浏览记录", description = "删除单条浏览记录")
    @DeleteMapping("/delete/{productId}")
    public ResultVO<String> deleteBrowsingHistory(@Validated @PathVariable ("productId") int productId) {
        try {
            browsingHistoryService.deleteBrowsingHistory(productId);
            return ResultVO.success("删除成功");
        } catch (RuntimeException e) {
            return ResultVO.error(e.getMessage());
        } catch (Exception e) {
            return ResultVO.error("删除失败，请稍后重试");
        }
    }

    /**
     * 清空浏览记录
     * @Param
     * @Return 删除结果
     * */
    @Operation(summary = "清空浏览记录", description = "清空浏览记录")
    @DeleteMapping("/clear")
    public ResultVO<String> clearBrowsingHistory() {
        try {
            browsingHistoryService.clearBrowsingHistory();
            return ResultVO.success("清空成功");
        } catch (RuntimeException e) {
            return ResultVO.error(e.getMessage());
        } catch (Exception e) {
            return ResultVO.error("清空失败，请稍后重试");
        }
    }

}

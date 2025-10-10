package com.trade.triorderservice.controller;

import com.trade.triorderservice.domain.dto.ReviewDTO;
import com.trade.triorderservice.domain.vo.ResultVO;
import com.trade.triorderservice.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/review")
@Tag(name = "评价管理", description = "评价相关接口")
public class ReviewController {

    private ReviewService reviewService;

    /**
     * 评论
     * @param reviewDTO 评论信息
     * @return 评论结果
     */
    @Operation(summary = "评论", description = "用户评论商品")
    @RequestMapping("/buyerReview")
    public ResultVO<String> createReview(@Validated @RequestBody ReviewDTO reviewDTO) {
        try {
            reviewService.createReview(reviewDTO);
            return ResultVO.success("评论成功");
        } catch (RuntimeException e) {
            return ResultVO.error(e.getMessage());
        } catch (Exception e) {
            return ResultVO.error("评论失败，请稍后重试");
        }
    }
}

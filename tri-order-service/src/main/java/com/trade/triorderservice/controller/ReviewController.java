package com.trade.triorderservice.controller;

import com.trade.triorderservice.domain.dto.ReviewDTO;
import com.trade.triorderservice.domain.vo.ResultVO;
import com.trade.triorderservice.service.ReviewService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/review")
public class ReviewController {

    private ReviewService reviewService;

    /**
     * 评论
     * @param reviewDTO 评论信息
     * @return 评论结果
     */
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

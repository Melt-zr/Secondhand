package com.trade.triorderservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.trade.triorderservice.domain.dto.ReviewDTO;
import com.trade.triorderservice.domain.po.Review;

public interface ReviewService extends IService<Review> {
    void createReview(ReviewDTO reviewDTO);
}

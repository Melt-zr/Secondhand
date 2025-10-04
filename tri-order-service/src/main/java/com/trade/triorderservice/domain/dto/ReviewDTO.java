package com.trade.triorderservice.domain.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ReviewDTO {

    private Integer reviewId;

    private Integer orderId;

    private Integer productId;

    private Integer reviewerId;

    private Integer reviewedId;

    /*
     * 0 不推荐，1 推荐
     * */
    private Integer rating;

    /*
     * 0 买家对卖家的评价，1 卖家对买家的评价
     * */
    private Integer reviewType;

    private String content;
}

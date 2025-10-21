package com.trade.triorderservice.domain.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class OrderIdentityDTO {
    private String orderNo;

    private Integer buyerId;

    private Integer sellerId;

    private Integer productId;

    /*
     * 0已取消 1已下单未支付 2已支付未发货 3已发货未确认收货 4已确认收货
     * */
    private Integer status;

    private Double totalPrice;
}

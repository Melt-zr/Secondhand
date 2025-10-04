package com.trade.triorderservice.domain.po;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
@TableName("tri_order")
public class Order {
    private String orderNo;

    private Integer buyerId;

    private Integer sellerId;

    private Integer productId;

    /*
    * 0已取消 1已下单未支付 2已支付未发货 3已发货未确认收货 4已确认收货
    * */
    private Integer status;

    private Double totalPrice;

    private Integer addressId;

    private Date paymentTime;

    /* 订单号 */
    private String trackingNumber;

}

package com.trade.triorderservice.domain.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class OrderDTO {

    private String orderNo;

    private Integer buyerId;

    private Integer sellerId;

    private Integer productId;

    private Double totalPrice;

    private Integer addressId;

    private String trackingNumber;
}

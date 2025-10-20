package com.trade.triorderservice.domain.dto;

import com.trade.triorderservice.domain.po.Order;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class OrderListDTO {
    private String buyerId;

    private List<Order> orderList;
}

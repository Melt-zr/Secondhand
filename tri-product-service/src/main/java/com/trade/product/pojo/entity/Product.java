package com.trade.product.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private Long id;
    private String title;
    private String description;
    private Double price;
    private Integer stock;
    private Integer status;
    private Long sellerId;
    private Date createTime;
    private Date updateTime;
}

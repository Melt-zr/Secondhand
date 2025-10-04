package com.trade.triapi.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ProductDTO {
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
package com.trade.triuserservice.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@TableName("tri_user_address")
public class UserAddress {

    private int id;

    private int userId;

    private String receiverName;

    private String receiverPhone;

    private String province;

    private String city;

    private String district;

    private String detailAddress;

    /*
    * 0 非默认，1 默认
    * */
    private int isDefault;
}

package com.trade.triuserservice.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
@TableName("tri_user")
public class User {
    @TableId(value = "id", type = IdType.AUTO)
    private String userId;

    private String userName;

    private String account;

    private String password;

    private String phone;

    /*
    * 0 未知，1 男，2 女
    * */
    private int gender;

    private Date birthday;

    private String regionCode;

    private int sellerCredit;

    private int buyerCredit;

    private Date createTime;

    private int avatarImageId;
}

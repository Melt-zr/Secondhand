package com.trade.triuserservice.domain.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class UserUpdateDTO {

    private String userName;

    /*
     * 0 未知，1 男，2 女
     * */
    private Integer gender;

    private Date birthday;

    private String regionCode;

    private Integer avatarImageId;
}

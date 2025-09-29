package com.trade.triuserservice.domain.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 用户注册响应VO
 */
@Data
@Accessors(chain = true)
public class UserRegisterVO {
    
    /**
     * 用户ID
     */
    private String userId;
    
    /**
     * 用户名
     */
    private String userName;
    
    /**
     * 账号
     */
    private String account;
    
    /**
     * 手机号
     */
    private String phone;
    
    /**
     * 注册时间
     */
    private String createTime;
}

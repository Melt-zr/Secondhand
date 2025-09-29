package com.trade.triuserservice.domain.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class UserRegisterDTO {

    @NotBlank(message = "用户名不能为空")
    private String userName;

    @NotBlank(message = "账号不能为空")
    private String account;

    @NotBlank(message = "密码不能为空")
    private String password;

    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String phone;

    /**
     * 性别：0 未知，1 男，2 女
     */
    private Integer gender;

    private Date birthday;

    private String regionCode;
}
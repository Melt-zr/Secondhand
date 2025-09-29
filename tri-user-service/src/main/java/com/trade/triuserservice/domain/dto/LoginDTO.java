package com.trade.triuserservice.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class LoginDTO {
    @NotBlank(message = "账号不能为空")
    private String account;

    @NotBlank(message = "密码不能为空")
    private String password;
}

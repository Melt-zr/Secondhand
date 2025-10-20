package com.trade.triuserservice.domain.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserIdentityDTO {

    private String userId;

    private String userName;

    private int avatarImageId;
}

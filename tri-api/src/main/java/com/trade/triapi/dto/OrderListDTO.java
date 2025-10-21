package com.trade.triapi.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserIdentityDTO {

    private String userId;

    private String userName;

    private int avatarImageId;
}

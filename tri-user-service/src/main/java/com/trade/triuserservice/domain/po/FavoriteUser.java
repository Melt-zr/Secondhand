package com.trade.triuserservice.domain.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@TableName("tri_favorite_user")
public class FavoriteUser {

    private int id;

    private int userId;

    private int favUserId;
}

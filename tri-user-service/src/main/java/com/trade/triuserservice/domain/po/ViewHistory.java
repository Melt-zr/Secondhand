package com.trade.triuserservice.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
@TableName("tri_view_history")
public class ViewHistory {

    private int id;

    private int userId;

    private int productId;

    private Date viewTime;

    /*
    * 0 未删除，1 已删除
    * */
    private int isDeleted;
}

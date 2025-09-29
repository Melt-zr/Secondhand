package com.trade.triuserservice.domain.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@TableName("tri_recommend_rule")
public class UserRecommendRule {
    @TableId(value = "id")
    private String id;

    private String ruleName;

    private String ruleType;

    private String weight;
}

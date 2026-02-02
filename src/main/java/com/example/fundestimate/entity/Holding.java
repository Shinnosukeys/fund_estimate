package com.example.fundestimate.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("t_holding")
public class Holding {
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private Long accountId;
    private String fundCode;
    private String fundName;
    private BigDecimal principal;      // 持有本金
    private BigDecimal initialProfit;  // 累计收益（昨日）
    
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}

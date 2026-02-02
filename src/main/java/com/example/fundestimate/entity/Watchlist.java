package com.example.fundestimate.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("t_watchlist")
public class Watchlist {
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private Long userId;
    private String fundCode;
    
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}

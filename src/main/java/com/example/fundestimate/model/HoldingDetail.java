package com.example.fundestimate.model;

import lombok.Data;

@Data
public class HoldingDetail {
    private String fundcode;        // 基金代码
    private String name;            // 基金名称
    private Double principal;       // 持有本金 (或当前市值)
    private Double initialProfit;   // 历史累计收益
    
    // 实时计算字段 (由后端计算填充)
    private Double realTimeGain;    // 今日估算收益
    private Double totalProfit;     // 实时总收益
    private Double gszzl;          // 估算涨跌幅
    private String gstime;         // 估值时间
}

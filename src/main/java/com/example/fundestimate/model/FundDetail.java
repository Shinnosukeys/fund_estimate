package com.example.fundestimate.model;

import lombok.Data;
import java.util.List;

@Data
public class FundDetail {
    private String fundcode;      // 基金代码
    private String name;          // 基金名称
    private String jztime;        // 净值日期
    private String dwjz;          // 单位净值
    private String gsz;           // 当前估值
    private String gszzl;         // 估算涨跌幅
    private String gstime;        // 估值时间
    
    // 当日估值走势数据
    private List<TrendPoint> trendData;
    
    @Data
    public static class TrendPoint {
        private String time;      // 时间点 (如 09:30)
        private Double value;     // 估值
        private Double change;    // 涨跌幅
    }
}

package com.example.fundestimate.model;

import lombok.Data;

@Data
public class FundEstimateInfo {
    private String fundcode;      // 基金代码 (对应接口 fundcode)
    private String name;          // 基金名称 (对应接口 name)
    private String jztime;        // 最后更新时间 (对应接口 jztime)
    private String dwjz;          // 单位净值
    private String gsz;           // 估算值
    private String gszzl;         // 估算涨跌幅
    private String gstime;        // 估值时间
}

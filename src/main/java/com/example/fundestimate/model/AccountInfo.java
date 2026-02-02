package com.example.fundestimate.model;

import lombok.Data;
import java.util.List;

@Data
public class AccountInfo {
    private String accountName;      // 账户名称
    private List<HoldingDetail> holdings; // 持仓列表
}

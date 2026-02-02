package com.example.fundestimate.controller;

import com.example.fundestimate.entity.Account;
import com.example.fundestimate.entity.Holding;
import com.example.fundestimate.model.HoldingDetail;
import com.example.fundestimate.service.AccountService;
import com.example.fundestimate.service.FundService;
import com.example.fundestimate.service.HoldingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/account")
@CrossOrigin
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private HoldingService holdingService;

    @Autowired
    private FundService fundService;

    @GetMapping("/list/{userId}")
    public List<Map<String, Object>> getAccounts(@PathVariable Long userId) {
        List<Account> accounts = accountService.getAccountsByUserId(userId);
        List<Map<String, Object>> result = new ArrayList<>();
        
        for (Account account : accounts) {
            Map<String, Object> accMap = new HashMap<>();
            accMap.put("id", account.getId());
            accMap.put("accountName", account.getAccountName());
            
            // 获取该账户下的持仓
            List<Holding> holdings = holdingService.getHoldingsByAccountId(account.getId());
            List<HoldingDetail> holdingDetails = new ArrayList<>();
            
            for (Holding h : holdings) {
                HoldingDetail detail = new HoldingDetail();
                detail.setFundcode(h.getFundCode());
                detail.setName(h.getFundName());
                detail.setPrincipal(h.getPrincipal().doubleValue());
                detail.setInitialProfit(h.getInitialProfit().doubleValue());
                // 计算实时收益
                fundService.calculateHolding(detail);
                holdingDetails.add(detail);
            }
            accMap.put("holdings", holdingDetails);
            result.add(accMap);
        }
        return result;
    }

    @PostMapping("/create")
    public Map<String, Object> createAccount(@RequestBody Map<String, Object> params) {
        Map<String, Object> result = new HashMap<>();
        Long userId = Long.valueOf(params.get("userId").toString());
        String accountName = params.get("accountName").toString();
        
        Account account = accountService.createAccount(userId, accountName);
        result.put("success", true);
        result.put("id", account.getId());
        result.put("accountName", account.getAccountName());
        return result;
    }

    @DeleteMapping("/delete/{accountId}")
    public Map<String, Object> deleteAccount(@PathVariable Long accountId) {
        Map<String, Object> result = new HashMap<>();
        accountService.deleteAccount(accountId);
        result.put("success", true);
        return result;
    }
}

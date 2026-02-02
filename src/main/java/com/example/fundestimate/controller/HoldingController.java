package com.example.fundestimate.controller;

import com.example.fundestimate.entity.Holding;
import com.example.fundestimate.service.HoldingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/holding")
@CrossOrigin
public class HoldingController {

    @Autowired
    private HoldingService holdingService;

    @PostMapping("/create")
    public Map<String, Object> createHolding(@RequestBody Map<String, Object> params) {
        Map<String, Object> result = new HashMap<>();
        Long accountId = Long.valueOf(params.get("accountId").toString());
        String fundCode = params.get("fundCode").toString();
        BigDecimal principal = new BigDecimal(params.get("principal").toString());
        BigDecimal initialProfit = new BigDecimal(params.get("initialProfit").toString());
        
        Holding holding = holdingService.createHolding(accountId, fundCode, principal, initialProfit);
        result.put("success", true);
        result.put("id", holding.getId());
        return result;
    }

    @DeleteMapping("/delete/{holdingId}")
    public Map<String, Object> deleteHolding(@PathVariable Long holdingId) {
        Map<String, Object> result = new HashMap<>();
        holdingService.deleteHolding(holdingId);
        result.put("success", true);
        return result;
    }

    @PutMapping("/update/{holdingId}")
    public Map<String, Object> updateHolding(@PathVariable Long holdingId, @RequestBody Map<String, Object> params) {
        Map<String, Object> result = new HashMap<>();
        BigDecimal principal = new BigDecimal(params.get("principal").toString());
        BigDecimal initialProfit = new BigDecimal(params.get("initialProfit").toString());
        
        Holding holding = holdingService.updateHolding(holdingId, principal, initialProfit);
        if (holding != null) {
            result.put("success", true);
        } else {
            result.put("success", false);
            result.put("message", "持仓不存在");
        }
        return result;
    }
}

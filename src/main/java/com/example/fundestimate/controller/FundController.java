package com.example.fundestimate.controller;

import com.example.fundestimate.model.FundEstimateInfo;
import com.example.fundestimate.model.HoldingDetail;
import com.example.fundestimate.service.FundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/fund")
@CrossOrigin // 允许前端跨域访问
public class FundController {

    @Autowired
    private FundService fundService;

    @GetMapping("/estimate/{code}")
    public FundEstimateInfo getEstimate(@PathVariable String code) {
        return fundService.getFundEstimate(code);
    }

    @GetMapping("/list")
    public List<FundEstimateInfo> getFundList(@RequestParam List<String> codes) {
        List<FundEstimateInfo> results = new ArrayList<>();
        for (String code : codes) {
            FundEstimateInfo info = fundService.getFundEstimate(code);
            if (info != null) {
                results.add(info);
            }
        }
        return results;
    }

    @PostMapping("/calculate-portfolio")
    public List<HoldingDetail> calculatePortfolio(@RequestBody List<HoldingDetail> holdings) {
        for (HoldingDetail holding : holdings) {
            fundService.calculateHolding(holding);
        }
        return holdings;
    }
}

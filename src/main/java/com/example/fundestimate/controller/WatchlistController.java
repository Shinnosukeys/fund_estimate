package com.example.fundestimate.controller;

import com.example.fundestimate.entity.Watchlist;
import com.example.fundestimate.model.FundEstimateInfo;
import com.example.fundestimate.service.FundService;
import com.example.fundestimate.service.WatchlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/watchlist")
@CrossOrigin
public class WatchlistController {

    @Autowired
    private WatchlistService watchlistService;

    @Autowired
    private FundService fundService;

    @GetMapping("/list/{userId}")
    public List<FundEstimateInfo> getWatchlist(@PathVariable Long userId) {
        List<String> fundCodes = watchlistService.getFundCodesByUserId(userId);
        List<FundEstimateInfo> result = new ArrayList<>();
        for (String code : fundCodes) {
            FundEstimateInfo info = fundService.getFundEstimate(code);
            if (info != null) {
                result.add(info);
            }
        }
        return result;
    }

    @PostMapping("/add")
    public Map<String, Object> addToWatchlist(@RequestBody Map<String, Object> params) {
        Map<String, Object> result = new HashMap<>();
        Long userId = Long.valueOf(params.get("userId").toString());
        String fundCode = params.get("fundCode").toString();
        
        Watchlist watchlist = watchlistService.addToWatchlist(userId, fundCode);
        if (watchlist != null) {
            result.put("success", true);
        } else {
            result.put("success", false);
            result.put("message", "该基金已在自选列表中");
        }
        return result;
    }

    @DeleteMapping("/remove/{userId}/{fundCode}")
    public Map<String, Object> removeFromWatchlist(@PathVariable Long userId, @PathVariable String fundCode) {
        Map<String, Object> result = new HashMap<>();
        watchlistService.removeFromWatchlist(userId, fundCode);
        result.put("success", true);
        return result;
    }
}

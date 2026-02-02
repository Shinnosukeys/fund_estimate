package com.example.fundestimate.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.fundestimate.entity.Watchlist;
import com.example.fundestimate.mapper.WatchlistMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WatchlistService {

    @Autowired
    private WatchlistMapper watchlistMapper;

    public List<String> getFundCodesByUserId(Long userId) {
        QueryWrapper<Watchlist> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId).orderByAsc("id");
        List<Watchlist> list = watchlistMapper.selectList(wrapper);
        return list.stream().map(Watchlist::getFundCode).collect(Collectors.toList());
    }

    public Watchlist addToWatchlist(Long userId, String fundCode) {
        // 检查是否已存在
        QueryWrapper<Watchlist> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId).eq("fund_code", fundCode);
        if (watchlistMapper.selectOne(wrapper) != null) {
            return null; // 已存在
        }
        
        Watchlist watchlist = new Watchlist();
        watchlist.setUserId(userId);
        watchlist.setFundCode(fundCode);
        watchlistMapper.insert(watchlist);
        return watchlist;
    }

    public void removeFromWatchlist(Long userId, String fundCode) {
        QueryWrapper<Watchlist> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId).eq("fund_code", fundCode);
        watchlistMapper.delete(wrapper);
    }
}

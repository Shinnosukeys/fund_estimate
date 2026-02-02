package com.example.fundestimate.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.fundestimate.entity.Holding;
import com.example.fundestimate.mapper.HoldingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class HoldingService {

    @Autowired
    private HoldingMapper holdingMapper;

    public List<Holding> getHoldingsByAccountId(Long accountId) {
        QueryWrapper<Holding> wrapper = new QueryWrapper<>();
        wrapper.eq("account_id", accountId).orderByAsc("id");
        return holdingMapper.selectList(wrapper);
    }

    public Holding createHolding(Long accountId, String fundCode, BigDecimal principal, BigDecimal initialProfit) {
        Holding holding = new Holding();
        holding.setAccountId(accountId);
        holding.setFundCode(fundCode);
        holding.setPrincipal(principal);
        holding.setInitialProfit(initialProfit);
        holdingMapper.insert(holding);
        return holding;
    }

    public void deleteHolding(Long holdingId) {
        holdingMapper.deleteById(holdingId);
    }

    public Holding updateHolding(Long holdingId, BigDecimal principal, BigDecimal initialProfit) {
        Holding holding = holdingMapper.selectById(holdingId);
        if (holding != null) {
            holding.setPrincipal(principal);
            holding.setInitialProfit(initialProfit);
            holdingMapper.updateById(holding);
        }
        return holding;
    }

    public Holding getById(Long id) {
        return holdingMapper.selectById(id);
    }
}

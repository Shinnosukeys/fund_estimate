package com.example.fundestimate.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.fundestimate.entity.Account;
import com.example.fundestimate.entity.Holding;
import com.example.fundestimate.mapper.AccountMapper;
import com.example.fundestimate.mapper.HoldingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private HoldingMapper holdingMapper;

    public List<Account> getAccountsByUserId(Long userId) {
        QueryWrapper<Account> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId).orderByAsc("id");
        return accountMapper.selectList(wrapper);
    }

    public Account createAccount(Long userId, String accountName) {
        Account account = new Account();
        account.setUserId(userId);
        account.setAccountName(accountName);
        accountMapper.insert(account);
        return account;
    }

    @Transactional
    public void deleteAccount(Long accountId) {
        // 先删除该账户下的所有持仓
        QueryWrapper<Holding> wrapper = new QueryWrapper<>();
        wrapper.eq("account_id", accountId);
        holdingMapper.delete(wrapper);
        // 再删除账户
        accountMapper.deleteById(accountId);
    }

    public Account getById(Long id) {
        return accountMapper.selectById(id);
    }
}

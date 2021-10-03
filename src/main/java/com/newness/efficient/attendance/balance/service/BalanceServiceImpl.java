package com.newness.efficient.attendance.balance.service;

import com.newness.efficient.attendance.balance.bo.Balance;
import com.newness.efficient.attendance.balance.mapper.BalanceMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class BalanceServiceImpl implements BalanceService {

    @Resource
    private BalanceMapper balanceMapper;

    @Override
    public Balance getTotaledBalance(String username) {
        return balanceMapper.getTotaledBalance(username) == null ? new Balance() : balanceMapper.getTotaledBalance(username);
    }

    /**
     * 获取调休和年假的余额
     * @param username 用户名
     * @return
     */
    @Override
    public Balance getBalances(String username) {
        Balance balance = new Balance();
        balance.setShift(30.0F);
        balance.setAnnually(40F);

        return balance;
    }
}

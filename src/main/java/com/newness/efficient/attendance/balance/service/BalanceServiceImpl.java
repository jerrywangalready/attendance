package com.newness.efficient.attendance.balance.service;

import com.newness.efficient.attendance.balance.mapper.BalanceMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class BalanceServiceImpl implements BalanceService {

    @Resource
    private BalanceMapper balanceMapper;

    @Override
    public Float getTotaledBalance(String username) {
        return balanceMapper.getTotaledBalance(username) == null ? 0 : balanceMapper.getTotaledBalance(username);
    }
}

package com.newness.efficient.attendance.balance.service;

import com.newness.efficient.attendance.balance.bo.Balance;

public interface BalanceService {

    Float getTotaledBalance(String username);

    Balance getBalances(String username);

}

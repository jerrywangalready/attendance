package com.newness.efficient.attendance.balance.service;

import com.newness.efficient.attendance.balance.bo.Balance;

public interface BalanceService {

    Balance getTotaledBalance(String username);

    Balance getBalances(String username);

}

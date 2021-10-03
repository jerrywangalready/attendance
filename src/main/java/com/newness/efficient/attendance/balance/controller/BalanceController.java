package com.newness.efficient.attendance.balance.controller;

import com.newness.efficient.attendance.balance.bo.Balance;
import com.newness.efficient.attendance.balance.service.BalanceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
@RequestMapping("/balance")
public class BalanceController {

    @Resource
    private BalanceService balanceService;

    @RequestMapping("/getBalance")
    public Balance getBalance(@AuthenticationPrincipal UserDetails user){
        return balanceService.getTotaledBalance(user.getUsername());
    }

    @RequestMapping("/getBalanceRecords")
    public Balance getBalanceRecords(@AuthenticationPrincipal UserDetails user) {
        return balanceService.getBalances(user.getUsername());
    }

}

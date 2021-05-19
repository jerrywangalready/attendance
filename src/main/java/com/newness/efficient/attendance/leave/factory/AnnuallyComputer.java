package com.newness.efficient.attendance.leave.factory;

import com.newness.efficient.attendance.balance.bo.Balance;
import com.newness.efficient.attendance.balance.service.BalanceService;
import com.newness.efficient.attendance.leave.bo.Settlement;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * 年假
 */
@Service("annually")
public class AnnuallyComputer extends BasicComputer implements Computer {

    @Resource
    private BalanceService balanceService;

    @Override
    public Settlement compute(String startDate, String endDate, String username) {
        Settlement result = new Settlement();
        result.setUnit("小时");
        Float sum = getTotalDurationWithoutHoliday(startDate, endDate);
        result.setDuration(sum);
        // 获取调休和年假时长
        Balance balances = balanceService.getBalances(username);
        if (balances.getAnnually() >= sum) {
            result.setShift(0F);
            result.setAnnually(sum);
        } else {
            result.setAnnually(balances.getShift());
            Float annually = sum - balances.getShift();
            result.setShift( (annually >= balances.getAnnually() ? balances.getAnnually() : annually));
        }

        result.setRemit(0F);
        result.setAmount(sum);
        return result;
    }
}

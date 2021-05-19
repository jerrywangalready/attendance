package com.newness.efficient.attendance.leave.factory;

import com.newness.efficient.attendance.balance.bo.Balance;
import com.newness.efficient.attendance.balance.service.BalanceService;
import com.newness.efficient.attendance.leave.bo.Settlement;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 病假
 */
@Service("sick")
public class SickComputer extends BasicComputer implements Computer {

    @Resource
    private BalanceService balanceService;

    @Override
    public Settlement compute(String startDate, String endDate, String username) {
        Settlement result = new Settlement();
        result.setUnit("小时");
        Float sum = getTotalDurationWithoutHoliday(startDate, endDate);
        result.setDuration(sum);
        // 减免
        Float remit = sum / 2;
        result.setRemit(remit);
        // 合计
        Float amount = sum - remit;
        result.setAmount(amount);
        // 获取调休和年假时长
        Balance balances = balanceService.getBalances(username);
        if (balances.getShift() >= amount) {
            result.setShift(amount);
            result.setAnnually(0F);
        } else {
            result.setShift(balances.getShift());
            float annually = amount - balances.getShift();
            result.setAnnually((annually >= balances.getAnnually() ? balances.getAnnually() : annually));
        }
        return result;
    }
}

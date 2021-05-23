package com.newness.efficient.attendance.leave.factory;

import com.newness.efficient.attendance.balance.bo.Balance;
import com.newness.efficient.attendance.balance.service.BalanceService;
import com.newness.efficient.attendance.leave.bo.Settlement;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 陪产假
 */
@Service("paternityLeave")
public class PaternityLeaveComputer extends BasicComputer implements Computer {

    @Resource
    private BalanceService balanceService;

    @Override
    public Settlement compute(String startDate, String endDate, String username) {
        Settlement result = new Settlement();
        result.setUnit("天");
        Float sum = getTotalDuration(startDate, endDate) / 8;
        result.setDuration(sum);
        // 减免
        Float remit = 15F;
        result.setRemit(remit);
        // 合计
        Float amount = sum <= remit ? 0F : sum - remit;
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

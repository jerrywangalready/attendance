package com.newness.efficient.attendance.leave.factory;

import com.newness.efficient.attendance.balance.service.BalanceService;
import com.newness.efficient.attendance.leave.bo.Settlement;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 公出
 */
@Service("officeAffairs")
public class OfficeAffairsComputer extends BasicComputer implements Computer {

    @Resource
    private BalanceService balanceService;

    @Override
    public Settlement compute(String startDate, String endDate, String username) {
        Settlement result = new Settlement();
        result.setUnit("小时");
        Float sum = getTotalDurationWithoutHoliday(startDate, endDate);
        result.setDuration(sum);
        // 减免
        result.setRemit(sum);
        // 合计
        result.setAmount(0F);
        // 获取调休和年假时长
        result.setShift(0F);
        result.setAnnually(0F);
        return result;
    }
}

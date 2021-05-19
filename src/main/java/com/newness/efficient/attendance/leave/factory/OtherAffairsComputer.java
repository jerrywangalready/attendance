package com.newness.efficient.attendance.leave.factory;

import com.newness.efficient.attendance.balance.service.BalanceService;
import com.newness.efficient.attendance.leave.bo.Settlement;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * 其它
 */
@Service("other")
public class OtherAffairsComputer extends BasicComputer implements Computer {

    @Resource
    private BalanceService balanceService;

    @Resource
    private ShiftComputer shiftComputer;

    @Override
    public Settlement compute(String startDate, String endDate, String username) {
        return shiftComputer.compute(startDate, endDate, username);
    }
}

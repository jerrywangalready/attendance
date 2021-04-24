package com.newness.efficient.attendance.leave.service;

import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class LeaveServiceImplTest {

    @Test
    void getEveryDayLeaveInfo() {

        LeaveServiceImpl service = new LeaveServiceImpl();
        Map<String, String> param = new HashMap<>();
        param.put("startDate", "2021-03-01 10:00:00");
        param.put("endDate", "2021-03-03 12:00:00");
        List<Map<String, Calendar>> everyDayLeaveInfo = service.getEveryDayLeaveInfo(param);

        everyDayLeaveInfo.forEach(item->{

        System.out.println(item.get("startDate").getTime() +">>>"+item.get("endDate").getTime());
        });
    }
}
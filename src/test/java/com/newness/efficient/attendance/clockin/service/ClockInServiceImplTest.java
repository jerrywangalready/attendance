package com.newness.efficient.attendance.clockin.service;

import com.newness.efficient.attendance.clockin.bo.DayBo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ClockInServiceImplTest {

    @Autowired
    ClockInServiceImpl clockInService;

    @Test
    void getDays() {
        List<DayBo> days = clockInService.getDays("2021", "09");
        System.out.println(days.toString());

    }


    @Test
    void testGetDays() {
        List<DayBo> days = clockInService.getDays("2021", "09");
        days.forEach(item -> {
            System.out.println(item.toString());
        });
    }
}
package com.newness.efficient.attendance.holiday.controller;

import com.newness.efficient.attendance.holiday.service.HolidayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/holiday")
public class HolidayController {

    @Autowired
    private HolidayService holidayService;

    @PostMapping("/initHolidaysByYear")
    public boolean initHolidaysByYear(@RequestBody int year) {
        holidayService.initHolidaysByYear(year);
        return true;
    }

    @PostMapping("/getHolidaysByYear")
    public List<String> getHolidaysByYear(@RequestBody Map<String, String> date) {
        List<Map<String, String>> dates = new ArrayList<>();
        dates.add(date);

        Map<String, String> lastMonth = new HashMap<>();
        if ("1".equals(date.get("month"))) {
            lastMonth.put("year", String.valueOf(Integer.parseInt(date.get("year")) - 1));
            lastMonth.put("month", "12");
        } else {
            lastMonth.put("year", date.get("year"));
            lastMonth.put("month", String.valueOf(Integer.parseInt(date.get("month")) - 1));
        }
        dates.add(lastMonth);

        Map<String, String> nextMonth = new HashMap<>();
        if ("12".equals(date.get("month"))) {
            nextMonth.put("year", String.valueOf(Integer.parseInt(date.get("year")) + 1));
            nextMonth.put("month", "01");
        } else {
            Map<String, String> map = new HashMap<>();
            nextMonth.put("year", date.get("year"));
            nextMonth.put("month", String.valueOf(Integer.parseInt(date.get("month")) + 1));
        }
        dates.add(nextMonth);

        return holidayService.getHolidaysByYear(dates);
    }

    @PostMapping("/updateHolidays")
    public boolean updateHolidays(@RequestBody List<Map<String, String>> list) {
        holidayService.updateHolidays(list);
        return true;
    }
}

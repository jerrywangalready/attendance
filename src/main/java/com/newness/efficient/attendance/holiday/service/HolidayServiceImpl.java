package com.newness.efficient.attendance.holiday.service;

import com.newness.efficient.attendance.holiday.mapper.HolidayMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class HolidayServiceImpl implements HolidayService {

    @Autowired
    private HolidayMapper holidayMapper;

    @Override
    public boolean initHolidaysByYear(int year) {
        List<Map<String, String>> list = getAllWeekendsByYear(year);
        for (Map<String, String> map : list) {
            holidayMapper.addHoliday(map);
        }
//        System.out.println(holidayMapper.test());
        return false;
    }

    @Override
    public List<String> getHolidaysByYear(List<Map<String, String>> dates) {
        return holidayMapper.getHolidaysByYear(dates);
    }

    @Override
    public void updateHolidays(List<Map<String, String>> list) {
        for (Map<String, String> map : list) {
            Map<String, String> holiday = new HashMap<>();
            String[] date = map.get("day").split("-");
            holiday.put("year", date[0]);
            holiday.put("month", date[1]);
            holiday.put("day", date[2]);
            if ("1".equals(map.get("opt"))) {
                holidayMapper.addHoliday(holiday);
            } else {
                holidayMapper.removeHoliday(holiday);
            }
        }
    }

    private List<Map<String, String>> getAllWeekendsByYear(int year) {

        int[] numOfMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        Calendar firstDayOfYear = Calendar.getInstance();
        firstDayOfYear.set(year, Calendar.JANUARY, 1, 0, 0, 0);
        int daysOfYear = firstDayOfYear.getActualMaximum(Calendar.DAY_OF_YEAR);
        if (daysOfYear == 366) {
            numOfMonth[1]++;
        }

        List<Map<String, String>> list = new ArrayList<>();
        for (int month = 0; month < numOfMonth.length; ) {
            int num = numOfMonth[month++];
            for (int day = 1; day <= num; day++) {
                Map<String, String> map = new HashMap<>();
                map.put("year", Integer.toString(year));
                if (month < 10) {
                    map.put("month", "0" + month);
                } else {
                    map.put("month", Integer.toString(month));
                }
                if (day < 10) {
                    map.put("day", "0" + day);
                } else {
                    map.put("day", Integer.toString(day));

                }

                list.add(map);
            }

        }

        int dayOfWeek = firstDayOfYear.get(Calendar.DAY_OF_WEEK);
        int temp = 7 - dayOfWeek;

        List<Map<String, String>> resultList = new ArrayList<>();
        while (temp < daysOfYear) {
            resultList.add(list.get(temp));
            if (temp + 1 < daysOfYear)
                resultList.add(list.get(temp + 1));
            temp += 7;
        }

        for (Map<String, String> map : resultList) {
            System.out.println(map);
        }
        return resultList;
    }
}

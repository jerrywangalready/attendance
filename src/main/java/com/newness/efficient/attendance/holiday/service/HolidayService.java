package com.newness.efficient.attendance.holiday.service;

import java.util.List;
import java.util.Map;

public interface HolidayService {

    boolean initHolidaysByYear(int year);

    List<String> getHolidaysByYear(List<Map<String, String>> dates);

    void updateHolidays(List<Map<String, String>> list);
}

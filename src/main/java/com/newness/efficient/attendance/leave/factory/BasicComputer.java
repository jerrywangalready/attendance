package com.newness.efficient.attendance.leave.factory;

import com.newness.efficient.attendance.holiday.service.HolidayService;
import com.newness.efficient.attendance.utils.DateUtil;
import com.newness.efficient.attendance.utils.ScheduleUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

@Component
public class BasicComputer {

    @Resource
    private HolidayService holidayService;

    public float getTotalDuration(String startDate, String endDate) {
        // todo 将请假时间拆分成每天
        List<Map<String, Calendar>> dayList = new ArrayList<>();
        Calendar startDateC = DateUtil.parseCalendar(startDate);
        Calendar endDateC = DateUtil.parseCalendar(endDate);
        splitTime(dayList, startDateC, endDateC);

        // todo 计算dayList总时长
        float sum = 0;
        for (Map<String, Calendar> map : dayList) {
            sum += computeDurationOfOneDay(map.get("startTime"), map.get("endTime"));
        }

        return sum;
    }

    public float getTotalDurationWithoutHoliday(String startDate, String endDate) {
        // todo 将请假时间拆分成每天
        List<Map<String, Calendar>> dayList = new ArrayList<>();
        Calendar startDateC = DateUtil.parseCalendar(startDate);
        Calendar endDateC = DateUtil.parseCalendar(endDate);
        splitTime(dayList, startDateC, endDateC);

        // todo 获取假期
        Map<String, String> holidays = getHolidays(dayList);

        for (int i = dayList.size() - 1; i >= 0; i--) {
            Map<String, Calendar> leaveMap = dayList.get(i);
            String startTime = DateUtil.CalendarToString(leaveMap.get("startTime"), "yyyy-MM-dd");
            if (holidays.containsKey(startTime)) {
                String during = holidays.get(startTime);
                if (during.equals("d") || leaveMap.get("startTime").get(Calendar.HOUR_OF_DAY) >= 12) {
                    dayList.remove(i);
                } else {
                    Calendar endTime = leaveMap.get("endTime");
                    endTime.set(Calendar.HOUR_OF_DAY, 12);
                    endTime.set(Calendar.MINUTE, 0);
                    endTime.set(Calendar.SECOND, 0);
                    endTime.set(Calendar.MILLISECOND, 0);
                }
            }
        }

        // todo 计算dayList总时长
        float sum = 0;
        for (Map<String, Calendar> map : dayList) {
            sum += computeDurationOfOneDay(map.get("startTime"), map.get("endTime"));
        }

        return sum;
    }


    /**
     * 计算一天内的请假时长
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 时长
     */
    public Float computeDurationOfOneDay(Calendar startTime, Calendar endTime) {
        BigDecimal duration;
        BigDecimal st = new BigDecimal(startTime.getTimeInMillis());
        BigDecimal et = new BigDecimal(endTime.getTimeInMillis());
        if (ScheduleUtil.isOverNoonBreak(startTime, endTime)) {
            Calendar noonBreakStartTime = ScheduleUtil.getNoonBreakStartTime(startTime);
            Calendar noonBreakEndTime = ScheduleUtil.getNoonBreakEndTime(endTime);

            BigDecimal zero = new BigDecimal(0);

            BigDecimal nbst = new BigDecimal(noonBreakStartTime.getTimeInMillis());
            BigDecimal am = nbst.compareTo(st) < 0 ? zero : nbst.subtract(st);

            BigDecimal nbet = new BigDecimal(noonBreakEndTime.getTimeInMillis());
            BigDecimal pm = et.compareTo(nbet) < 0 ? zero : et.subtract(nbet);

            duration = am.add(pm);
        } else {
            duration = et.subtract(st);
        }
        return Float.parseFloat(duration.divide(new BigDecimal(3600 * 1000), BigDecimal.ROUND_UP).toString());
    }

    public void splitTime(List<Map<String, Calendar>> list, Calendar startTime, Calendar endTime) {
        Calendar theDayStartTime = (Calendar) startTime.clone();

        Map<String, Calendar> day = new HashMap<>();
        list.add(day);
        day.put("startTime", theDayStartTime);

        Calendar theDayEndTime = (Calendar) theDayStartTime.clone();
        theDayEndTime.set(Calendar.HOUR_OF_DAY, 18);
        theDayEndTime.set(Calendar.MINUTE, 0);
        theDayEndTime.set(Calendar.SECOND, 0);
        theDayEndTime.set(Calendar.MILLISECOND, 0);

        if (theDayEndTime.compareTo(endTime) < 0) {
            day.put("endTime", theDayEndTime);
            Calendar nextDayStartTime = (Calendar) theDayEndTime.clone();
            nextDayStartTime.add(Calendar.DAY_OF_MONTH, 1);
            nextDayStartTime.set(Calendar.HOUR_OF_DAY, 9);
            splitTime(list, nextDayStartTime, endTime);
        } else {
            day.put("endTime", endTime);
        }
    }

    public Map<String, String> getHolidays(List<Map<String, Calendar>> dayList) {
        Map<String, Map<String, String>> param = new HashMap<>();
        dayList.forEach(day -> {
            Calendar startTime = day.get("startTime");
            String date = DateUtil.CalendarToString(startTime, "yyyy-MM-dd");
            if (!param.containsKey(date)) {
                Map<String, String> body = new HashMap<>();
                body.put("year", String.valueOf(startTime.get(Calendar.YEAR)));
                body.put("month", String.valueOf(startTime.get(Calendar.MONTH) + 1));
                param.put(date, body);
            }
        });

        List<Map<String, String>> paramList = new ArrayList<>();
        param.forEach((key, value) -> paramList.add(value));

        List<Map<String, String>> holidays = holidayService.getHolidaysByYear(paramList);
        Map<String, String> result = new HashMap<>();
        holidays.forEach(item -> result.put(item.get("holiday"), item.get("during")));

        return result;
    }
}

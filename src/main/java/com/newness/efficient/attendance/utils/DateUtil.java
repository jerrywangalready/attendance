package com.newness.efficient.attendance.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    public static final String DATA_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private static final SimpleDateFormat SDF = new SimpleDateFormat(DATA_FORMAT);

    /**
     * @param strDate
     * @return
     */
    public static Calendar parseCalendar(String strDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(parseDate(strDate));
        return calendar;
    }

    /**
     * @param Time HH:mm
     * @return
     */
    public static Calendar parseCalendarByTime(String Time) {
        String[] times = Time.split(":");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(times[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(times[1]));
        calendar.set(Calendar.SECOND, 0);
        return calendar;
    }

    public static String CalendarToString(Calendar calendar) {
        return SDF.format(calendar.getTime());
    }

    public static String CalendarToString(Calendar calendar, String format) {
        SimpleDateFormat newFDF = new SimpleDateFormat(format);
        return newFDF.format(calendar.getTime());
    }

    public static Date parseDate(String strDate) {
        Date date = null;
        try {
            date = SDF.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String DateToString(Date date) {
        return SDF.format(date);
    }

    public static Calendar getStartWorkTimeByDay(Calendar day) {
        Calendar calendar = (Calendar) day.clone();
        calendar.set(Calendar.HOUR, 9);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar;
    }

    public static Calendar getOffWorkTimeByDay(Calendar day) {
        Calendar calendar = (Calendar) day.clone();
        calendar.set(Calendar.HOUR, 18);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar;
    }

    public static Calendar getLastDayByMonth(String year, String month) {
        Date date = parseDate(year + "-" + month + "-01 00:00:00");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        // 当前日历的天数上-1变成最大值 , 此方法不会改变指定字段之外的字段
        calendar.add(Calendar.MONTH, -1);
        calendar.roll(Calendar.DAY_OF_MONTH, -1);
        return calendar;
    }

    public static void main(String[] args) {
        Calendar lastDayByMonth = getLastDayByMonth("2021", "03");
        System.out.println(lastDayByMonth.getTime());
    }
}

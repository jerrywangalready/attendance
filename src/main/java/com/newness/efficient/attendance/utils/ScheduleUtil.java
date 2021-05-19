package com.newness.efficient.attendance.utils;

import java.util.Calendar;

public class ScheduleUtil {

    public static final String WORK_TIME = "09:00";

    public static final String NOON_BREAK_START_TIME = "12:00";

    public static final String NOON_BREAK_END_TIME = "13:00";

    public static final String OFF_WORK_TIME = "18:00";

    public static Calendar getNoonBreakStartTime(Calendar time) {
        return getTimeByTime(time, NOON_BREAK_START_TIME);
    }

    public static Calendar getNoonBreakEndTime(Calendar time) {
        return getTimeByTime(time, NOON_BREAK_END_TIME);
    }

    private static Calendar getTimeByTime(Calendar time, String moment) {
        Calendar copyTime = (Calendar) time.clone();
        int[] hourNMinute = getHourNMinute(moment);
        copyTime.set(Calendar.HOUR_OF_DAY, hourNMinute[0]);
        copyTime.set(Calendar.MINUTE, hourNMinute[1]);
        copyTime.set(Calendar.SECOND, 0);
        copyTime.set(Calendar.MILLISECOND, 0);
        return copyTime;
    }

    private static int[] getHourNMinute(String time) {
        String[] split = time.split(":");
        int hour = Integer.parseInt(split[0]);
        int minute = Integer.parseInt(split[1]);
        return new int[]{hour, minute};
    }

    public static boolean isOverNoonBreak(Calendar startTime, Calendar endTime) {
        Calendar noonBreakStartTime = ScheduleUtil.getNoonBreakStartTime(startTime);
        Calendar noonBreakEndTime = ScheduleUtil.getNoonBreakEndTime(endTime);
        return noonBreakStartTime.compareTo(endTime) <= 0 && startTime.compareTo(noonBreakEndTime) <= 0;
    }
}

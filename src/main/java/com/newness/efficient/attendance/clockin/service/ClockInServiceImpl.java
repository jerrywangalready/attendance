package com.newness.efficient.attendance.clockin.service;

import com.newness.efficient.attendance.clockin.bo.ClockInBo;
import com.newness.efficient.attendance.clockin.bo.ClockInGridBo;
import com.newness.efficient.attendance.clockin.bo.DayBo;
import com.newness.efficient.attendance.clockin.mapper.ClockInMapper;
import com.newness.efficient.attendance.clockin.util.AnalyzedUtil;
import com.newness.efficient.attendance.holiday.mapper.HolidayMapper;
import com.newness.efficient.attendance.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ClockInServiceImpl implements ClockInService {

    @Autowired
    private ClockInMapper clockInMapper;

    @Autowired
    private HolidayMapper holidayMapper;

    @Override
    public void insertBatch(List<ClockInBo> clockInBoList) {
        clockInMapper.insertBatch(clockInBoList);
    }

    @Override
    public List<Map<String, String>> getClockInRecords(ClockInGridBo param) {
        return clockInMapper.getClockInRecords(param);
    }

    @Override
    public List<Map<String, String>> getMonthOfUnAnalyzed() {
        List<Map<String, String>> dateByUnAnalyzed = clockInMapper.getDateByUnAnalyzed();

        if (dateByUnAnalyzed != null && dateByUnAnalyzed.size() > 0) {
            return dateByUnAnalyzed.subList(0, 1);
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public List<DayBo> getDays(String strYear, String strMonth) {

        List<Map<String, String>> holidays = getHolidays(strYear, strMonth);

        int year = Integer.parseInt(strYear);
        int month = Integer.parseInt(strMonth);
        List<String> daysOfTheMonth = getDaysOfTheMonth(year, month);

        List<DayBo> list = new ArrayList<>();
        for (String day : daysOfTheMonth) {
            DayBo bo = new DayBo();
            bo.setDate(day);
            bo.setType("W");
            for (int i = holidays.size() - 1; i >= 0; i--) {
                Map<String, String> holidayMap = holidays.get(i);
                if (day.equals(holidayMap.get("holiday"))) {
                    bo.setType("H");
                    bo.setDuring(holidayMap.get("during"));
                    holidays.remove(i);
                }
            }
            list.add(bo);
        }

        return list;

    }

    private List<Map<String, String>> getHolidays(String year, String month) {
        List<Map<String, String>> list = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        map.put("year", year);
        map.put("month", month);
        list.add(map);
        return holidayMapper.getHolidaysByYear(list);
    }

    private List<String> getDaysOfTheMonth(int year, int month) {
        Calendar instance = Calendar.getInstance();
        instance.set(Calendar.YEAR, year);
        instance.set(Calendar.MONTH, month - 1);
        instance.set(Calendar.DATE, 1);
        instance.roll(Calendar.DATE, -1);
        int maxDate = instance.get(Calendar.DATE);

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);//month 为指定月份任意日期
        cal.set(Calendar.DATE, 1);

        List<String> list = new ArrayList<>();

        for (int i = 0; i < maxDate; i++, cal.add(Calendar.DATE, 1)) {
            Date d = cal.getTime();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String df = simpleDateFormat.format(d);
            list.add(df);
        }

        return list;
    }

    /**
     * 分析当天的打卡情况
     *
     * @param day                            当天信息,用于判断是否半天特殊日
     * @param offWorkTimeOfTheLastWorkingDay 上一个工作日的下班时间
     * @param clockInInfo                    当天打卡信息
     * @param leaveInfos                     当天请假信息
     * @return
     */
    public List<String> analyzeClockInInfoAtWorkingDay(DayBo day, String offWorkTimeOfTheLastWorkingDay, String clockInInfo, List<Map<String, String>> leaveInfos) {

        List<String> unusual = new ArrayList<>();

        if (leaveAllDay(leaveInfos)) {
            return unusual;
        }


        // 获取上班时间
        Calendar startWorkingTime = getStartTime(offWorkTimeOfTheLastWorkingDay, leaveInfos);
        // 半小时迟到判定时间点
        Calendar lateHalfAnHour = getJudgeLateTime(startWorkingTime, 30);
        // 一个小时迟到判定时间点
        Calendar lateAnHour = getJudgeLateTime(startWorkingTime, 60);

        // 获取下班时间
        Calendar offWorkingTime = getOffTime(day, leaveInfos);
        // 半小时早退判断时间点
        Calendar leavingEarlyHalfAnHour = getJudgeLeaveEarlyTime(offWorkingTime, -30);
        // 一个小时迟到判定时间点
        Calendar leavingEarlyAnHour = getJudgeLeaveEarlyTime(offWorkingTime, -60);

        if (neverClockIn(clockInInfo)) {
            unusual.add(AnalyzedUtil.MISSED_CLOCK_IN_ALL_DAY);
        } else {
            String[] clockInInfos = clockInInfo.split(" ");
            List<Calendar> clockInInfoGroup = new ArrayList<>();
            for (String info : clockInInfos) {
                clockInInfoGroup.add(DateUtil.parseCalendarByTime(info));
            }
            Calendar arrive = clockInInfoGroup.get(0);
            Calendar leave = clockInInfoGroup.get(clockInInfoGroup.size() - 1);

            if (isMissClockInWhenStartWork(startWorkingTime, offWorkingTime, arrive)) {
                unusual.add(AnalyzedUtil.MISSED_CLOCK_IN_WHEN_START_WORK);
            } else {
                if (arrive.after(startWorkingTime) && !arrive.after(lateHalfAnHour)) {
                    unusual.add(AnalyzedUtil.LATE_HALF_AN_HOUR);
                } else if (arrive.after(lateHalfAnHour) && !arrive.after(lateAnHour)) {
                    unusual.add(AnalyzedUtil.LATE_ONE_HOUR);
                } else if (arrive.after(lateAnHour)) {
                    unusual.add(AnalyzedUtil.LATE_OVER_AN_HOUR);
                }
            }

            if (isMissClockInWhenOffWork(startWorkingTime, offWorkingTime, leave)) {
                unusual.add(AnalyzedUtil.MISSED_CLOCK_IN_WHEN_OFF_WORK);
            } else {
                if (leave.before(offWorkingTime) && !leave.before(leavingEarlyHalfAnHour)) {
                    unusual.add(AnalyzedUtil.LEAVING_EARLY_IN_HALF_HOUR);
                } else if (leave.before(leavingEarlyHalfAnHour) && !leave.before(leavingEarlyAnHour)) {
                    unusual.add(AnalyzedUtil.LEAVING_EARLY_IN_ONE_HOUR);
                } else if (leave.before(leavingEarlyAnHour)) {
                    unusual.add(AnalyzedUtil.LEAVING_EARLY_OVER_AN_HOUR);
                }
            }

            if (isMissClockInWhenNoonBreak(startWorkingTime, offWorkingTime, clockInInfoGroup)) {
                unusual.add(AnalyzedUtil.MISSED_CLOCK_IN_WHEN_NOON);
            }

        }


        return unusual;
    }


    private boolean isMissClockInWhenStartWork(Calendar startWorkingTime, Calendar offWorkingTime, Calendar arrive) {
        /**
         * 1. 开始时间早于12点,结束时间晚于12点,第一条打卡记录晚于12点,为漏打上班卡
         * 2. 第一条打卡记录晚于下班时间,为上班漏打卡
         */
        // 午休时间
        Calendar noon = DateUtil.parseCalendarByTime("12:00");
        if (startWorkingTime.before(noon) && offWorkingTime.after(noon)) {
            return !arrive.before(noon);
        } else {
            return !arrive.before(offWorkingTime);
        }
    }

    private boolean isMissClockInWhenOffWork(Calendar startWorkingTime, Calendar offWorkingTime, Calendar leave) {
        /**
         * 1. 开始时间早于13点,结束时间晚于13点,最后一条打卡记录早于13点,为下班漏打卡
         * 2. 最后一条打卡记录早于上班时间,为下班漏打卡
         */
        // 下午上班时间
        Calendar afternoon = DateUtil.parseCalendarByTime("13:00");
        if (startWorkingTime.before(afternoon) && offWorkingTime.after(afternoon)) {
            return leave.before(afternoon);
        } else {
            return !leave.after(startWorkingTime);
        }
    }

    private boolean isMissClockInWhenNoonBreak(Calendar startWorkingTime, Calendar offWorkingTime, List<Calendar> clockInInfoGroup) {
        // 午休时间
        Calendar noon = DateUtil.parseCalendarByTime("12:00");
        // 下午上班时间
        Calendar afternoon = DateUtil.parseCalendarByTime("13:00");
        if (startWorkingTime.before(noon) && offWorkingTime.after(noon)) {
            for (Calendar clockIn : clockInInfoGroup) {
                if (!clockIn.before(noon) && !clockIn.after(afternoon)) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    private Calendar getJudgeLateTime(Calendar startWorkingTime, int i) {
        // 午休时间
        Calendar noon = DateUtil.parseCalendarByTime("12:00");
        Calendar judgeTime = addTime(startWorkingTime, i);
        return judgeTime.before(noon) ? judgeTime : DateUtil.parseCalendarByTime("13:00");
    }

    private Calendar getJudgeLeaveEarlyTime(Calendar offWorkingTime, int i) {
        // 下午上班时间
        Calendar afternoon = DateUtil.parseCalendarByTime("13:00");
        Calendar judgeTime = addTime(offWorkingTime, i);
        return judgeTime.after(afternoon) ? judgeTime : DateUtil.parseCalendarByTime("12:00");
    }


    private boolean leaveAllDay(List<Map<String, String>> leaveInfo) {
        int size = leaveInfo.size();
        if (size > 0) {
            String startTime = leaveInfo.get(0).get("startTime");
            String endTime = leaveInfo.get(size - 1).get("endTime");
            // 9:00-10:00     16:00-18:00
            return AnalyzedUtil.DEFAULT_START_WORKING_TIME.equals(startTime) && AnalyzedUtil.DEFAULT_OFF_WORKING_TIME.equals(endTime);
        } else {
            return false;
        }
    }

    private Calendar getStartTime(String offWorkTimeOfTheLastWorkingDay, List<Map<String, String>> leaveInfos) {
        String startTime = AnalyzedUtil.DEFAULT_START_WORKING_TIME;
        for (Map<String, String> leaveInfo : leaveInfos) {
            if (leaveInfo.get("startTime").equals(startTime)) {
                startTime = leaveInfo.get("endTime").equals("12:00") ? "13:00" : leaveInfo.get("endTime");
            }
        }
        Calendar time = DateUtil.parseCalendarByTime(startTime);
        if (overtimeInLastWorkday(offWorkTimeOfTheLastWorkingDay)) {
            delayStartWorkingTime(time, 30);
        }
        return time;
    }

    public Calendar getOffTime(DayBo day, List<Map<String, String>> leaveInfos) {
        String offTime = specialHalfDay(day) ? "12:00" : AnalyzedUtil.DEFAULT_OFF_WORKING_TIME;
        for (int i = leaveInfos.size() - 1; i >= 0; i--) {
            Map<String, String> leaveInfo = leaveInfos.get(i);
            if (leaveInfo.get("endTime").compareTo(offTime) >= 0 && leaveInfo.get("startTime").compareTo(offTime) < 0) {
                offTime = leaveInfo.get("startTime");
            }
        }
        return DateUtil.parseCalendarByTime(offTime);
    }

    private boolean specialHalfDay(DayBo day) {
        return "pm".equals(day.getDuring());
    }

    private boolean notLeave(List<Map<String, String>> leaveInfo) {
        return leaveInfo.isEmpty();
    }

    private boolean neverClockIn(String clockInInfo) {
        return clockInInfo == null || clockInInfo.isEmpty();
    }

    /**
     * @param offWorkTimeOfTheLastWorkingDay eg:20:00
     * @return
     */
    private boolean overtimeInLastWorkday(String offWorkTimeOfTheLastWorkingDay) {
        Calendar overtimeEffectiveTime = DateUtil.parseCalendarByTime(AnalyzedUtil.OVERTIME_EFFECTIVE_TIME);
        Calendar offWorkTime = DateUtil.parseCalendarByTime(offWorkTimeOfTheLastWorkingDay);
        return !offWorkTime.before(overtimeEffectiveTime);
    }

    private void delayStartWorkingTime(Calendar startWorkingTime, int i) {
        startWorkingTime.add(Calendar.MINUTE, i);
    }

    private Calendar addTime(Calendar time, int i) {
        Calendar calendar = (Calendar) time.clone();
        calendar.add(Calendar.MINUTE, i);
        return calendar;
    }

    @Override
    public Map<String, String> getOffWorkTimeOfLastMonthLastDay(String year, String month) {
        Calendar lastDayByMonth = DateUtil.getLastDayByMonth(year, month);
        String date = DateUtil.CalendarToString(lastDayByMonth);
        List<Map<String, String>> clockInfos = clockInMapper.getClockInInfoByDate(date);
        Map<String, String> userRecords = new HashMap<>();
        clockInfos.forEach(item -> userRecords.put(item.get("userName"), item.get("clockInInfo")));
        return userRecords;
    }

    @Override
    public List<String> analyzeClockInInfoAtHoliday(DayBo day, String clockInInfo, List<Map<String, String>> overtimeInfos) {
        List<String> list = new ArrayList<>();
        if (!overtimeInfos.isEmpty()) {
            String[] clockInInfos = clockInInfo.split(" ");
            if (clockInInfos.length > 1) {
                Calendar overtimeStart = DateUtil.parseCalendarByTime(clockInInfos[0]);
                Calendar overtimeEnd = DateUtil.parseCalendarByTime(clockInInfos[clockInInfos.length - 1]);
                Calendar overtimeDuring;
                if (specialHalfDay(day)) {
                    correctTime(overtimeStart);
                }
                if (overtimeStart.before(overtimeEnd)) {
                    long l = overtimeEnd.getTimeInMillis() - overtimeStart.getTimeInMillis();
                    int hours = new Long(l / (1000 * 60 * 60)).intValue();
                    // todo 四舍五入hours得出最终 得加班时间

                } else {
                    list.add(AnalyzedUtil.OVERTIME_MISS_CLOCK_IN_RECORDS);
                }
            } else {
                list.add(AnalyzedUtil.OVERTIME_MISS_CLOCK_IN_RECORDS);
            }
        }
        return list;
    }

    /**
     * 矫正时间,当需要将早于13点的时间按重新定为13点钟
     * @param calendar
     */
    public void correctTime(Calendar calendar) {
        Calendar afternoon = DateUtil.parseCalendarByTime("13:00");
        if (!calendar.after(afternoon)) {
            calendar.setTime(afternoon.getTime());
        }
    }

}

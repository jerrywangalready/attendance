package com.newness.efficient.attendance.clockin.service;

import com.newness.efficient.attendance.clockin.bo.ClockInBo;
import com.newness.efficient.attendance.clockin.bo.ClockInGridBo;
import com.newness.efficient.attendance.clockin.bo.DayBo;

import java.util.List;
import java.util.Map;

public interface ClockInService {

    void insertBatch(List<ClockInBo> clockInBoList);

    List<Map<String, String>> getClockInRecords(ClockInGridBo param);

    List<Map<String, String>> getMonthOfUnAnalyzed();

    List<DayBo> getDays(String year, String month);

    /**
     * 分析工作日当天的打卡情况
     *
     * @param day                            当天信息,用于判断是否半天特殊日
     * @param offWorkTimeOfTheLastWorkingDay 上一个工作日的下班时间
     * @param clockInInfo                    当天打卡信息
     * @param leaveInfos                     当天请假信息
     * @return
     */
    List<String> analyzeClockInInfoAtWorkingDay(DayBo day, String offWorkTimeOfTheLastWorkingDay, String clockInInfo, List<Map<String, String>> leaveInfos);

    /**
     * 分析假期当天的打卡情况
     * @param day 当天信息,用于判断是否是半天特殊日
     * @param clockInInfo 当天打卡信息
     * @param leaveInfos 当天加班信息
     * @return
     */
    List<String> analyzeClockInInfoAtHoliday(DayBo day, String clockInInfo, List<Map<String, String>> leaveInfos);

    Map<String, String> getOffWorkTimeOfLastMonthLastDay(String year, String month);
}

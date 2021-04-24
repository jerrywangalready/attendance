package com.newness.efficient.attendance.clockin.util;

public class AnalyzedUtil {

    // 默认上班时间
    public static final String DEFAULT_START_WORKING_TIME = "09:00";
    // 默认下班时间
    public static final String DEFAULT_OFF_WORKING_TIME = "18:00";
    // 加班生效时间
    public static final String OVERTIME_EFFECTIVE_TIME = "20:00";

    /**
     * 正常
     */
    public static final String NORMAL_WORKDAY = "300";

    /**
     * 半小时内迟到
     */
    public static final String LATE_HALF_AN_HOUR = "311";

    /**
     * 一小时内迟到
     */
    public static final String LATE_ONE_HOUR = "312";

    /**
     * 一小时以上迟到
     */
    public static final String LATE_OVER_AN_HOUR = "313";

    /**
     * 半小时内早退
     */
    public static final String LEAVING_EARLY_IN_HALF_HOUR = "321";

    /**
     * 一小时内早退
     */
    public static final String LEAVING_EARLY_IN_ONE_HOUR = "322";

    /**
     * 一小时以上早退
     */
    public static final String LEAVING_EARLY_OVER_AN_HOUR = "323";

    /**
     * 上班漏打卡
     */
    public static final String MISSED_CLOCK_IN_WHEN_START_WORK = "331";

    /**
     * 中午漏打卡
     */
    public static final String MISSED_CLOCK_IN_WHEN_NOON = "332";

    /**
     * 下班漏打卡
     */
    public static final String MISSED_CLOCK_IN_WHEN_OFF_WORK = "333";

    /**
     * 全天未打卡
     */
    public static final String MISSED_CLOCK_IN_ALL_DAY = "334";

    /**
     * 加班缺少打卡记录:有加班申请,但缺少打卡记录
     */
    public static final String NORMAL_HOLIDAY = "800";

    /**
     * 加班缺少打卡记录:有加班申请,但缺少打卡记录
     */
    public static final String OVERTIME_MISS_CLOCK_IN_RECORDS = "811";

    /**
     * 加班缺少申请:有加班打卡记录,但是无申请
     */
    public static final String OVERTIME_MISS_APPLY = "821";

}

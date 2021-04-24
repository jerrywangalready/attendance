package com.newness.efficient.attendance.clockin.mapper;

import com.newness.efficient.attendance.clockin.bo.ClockInBo;
import com.newness.efficient.attendance.clockin.bo.ClockInGridBo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ClockInMapper {

    void insertBatch(List<ClockInBo> clockInBoList);

    List<Map<String, String>> getClockInRecords(ClockInGridBo param);

    /**
     * @return 未分析过的打卡数据所在的年和月份的信息
     */
    List<Map<String, String>> getDateByUnAnalyzed();

    List<Map<String, String>> getClockInInfoByDate(String date);
}

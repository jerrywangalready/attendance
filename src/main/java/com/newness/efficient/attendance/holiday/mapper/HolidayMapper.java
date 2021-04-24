package com.newness.efficient.attendance.holiday.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface HolidayMapper {

    List<Map<String, String>> getHolidaysByYear(@Param("dates") List<Map<String, String>> dates);

    int addHoliday(Map<String, String> map);
    int updateHoliday(Map<String, String> map);
    int removeHoliday(Map<String, String> map);

}

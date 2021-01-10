package com.newness.efficient.attendance.holiday.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface HolidayMapper {

    List<String> getHolidaysByYear(@Param("dates") List<Map<String, String>> dates);

    int addHoliday(Map<String, String> map);
    int removeHoliday(Map<String, String> map);


}

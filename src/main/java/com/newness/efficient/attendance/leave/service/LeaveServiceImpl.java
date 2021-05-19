package com.newness.efficient.attendance.leave.service;

import com.newness.efficient.attendance.leave.bo.LeaveForm;
import com.newness.efficient.attendance.leave.entity.LeaveProcess;
import com.newness.efficient.attendance.leave.entity.LeaveRecord;
import com.newness.efficient.attendance.leave.mapper.LeaveMapper;
import com.newness.efficient.attendance.utils.DateUtil;
import com.newness.efficient.attendance.utils.IdCreator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class LeaveServiceImpl implements LeaveService {

    @Resource
    private LeaveMapper leaveMapper;

    @Override
    public String saveLeave(LeaveForm param) {
        String leaveId = IdCreator.getId();
        param.setLeaveId(leaveId);
        leaveMapper.saveLeave(param);
        return leaveId;
    }

    /**
     * 获取某年月内所有请假信息,如果一次请假超过一天,会拆成多条
     *
     * @param yearMonth 年月
     * @return
     */
    @Override
    public Map<String, List<Map<String, Calendar>>> getUsersLeaveInfoByMonth(String yearMonth) {
        Map<String, String> param = new HashMap<>();
        param.put("yearMonth", yearMonth);
        List<Map<String, String>> leaveInfo = leaveMapper.getLeaveInfo(param);

        Map<String, List<Map<String, Calendar>>> result = new HashMap<>();
        leaveInfo.forEach(item -> {
            String username = item.get("username");
            if (!result.containsKey(username)) {
                result.put(username, new ArrayList<>());
            }
            List<Map<String, Calendar>> infos = getEveryDayLeaveInfo(item);
            result.get(username).addAll(infos);
        });
        return result;
    }

    public List<Map<String, Calendar>> getEveryDayLeaveInfo(Map<String, String> item) {
        Calendar startDate = DateUtil.parseCalendar(item.get("startDate"));
        Calendar endDate = DateUtil.parseCalendar(item.get("endDate"));

        List<Map<String, Calendar>> leaveInfo = new ArrayList<>();

        Calendar tempDate = (Calendar) startDate.clone();
        while (beforeOrSameDay(tempDate, endDate)) {
            Map<String, Calendar> info = new HashMap<>();
            info.put("startDate", sameDay(tempDate, startDate) ? startDate : DateUtil.getStartWorkTimeByDay(tempDate));
            info.put("endDate", sameDay(tempDate, endDate) ? endDate : DateUtil.getOffWorkTimeByDay(tempDate));
            leaveInfo.add(info);
            nextDay(tempDate);
        }
        return leaveInfo;
    }

    private void nextDay(Calendar calendar) {
        calendar.add(Calendar.DATE, 1);
    }

    private boolean beforeOrSameDay(Calendar day, Calendar compareDay) {
        return sameDay(day, compareDay) || before(day, compareDay);

    }

    private boolean sameDay(Calendar day, Calendar compareDay) {
        return day.get(Calendar.YEAR) == compareDay.get(Calendar.YEAR)
                && day.get(Calendar.MONTH) == compareDay.get(Calendar.MONTH)
                && day.get(Calendar.DAY_OF_MONTH) == compareDay.get(Calendar.DAY_OF_MONTH);
    }

    private boolean before(Calendar day, Calendar compareDay) {
        return day.get(Calendar.YEAR) < compareDay.get(Calendar.YEAR) ||
                day.get(Calendar.YEAR) == compareDay.get(Calendar.YEAR)
                        && day.get(Calendar.MONTH) < compareDay.get(Calendar.MONTH) ||
                day.get(Calendar.YEAR) == compareDay.get(Calendar.YEAR)
                        && day.get(Calendar.MONTH) == compareDay.get(Calendar.MONTH)
                        && day.get(Calendar.DAY_OF_MONTH) < compareDay.get(Calendar.DAY_OF_MONTH);
    }

    @Override
    public void addLeaveRecord(LeaveRecord leaveRecord) {
        leaveMapper.addLeaveRecord(leaveRecord);
    }

    @Override
    public void saveLeaveProcess(LeaveProcess leaveProcess) {
        if (leaveProcess.isProcessIdBlank()) {
            leaveProcess.setProcessId(IdCreator.getId());
            leaveMapper.addProcess(leaveProcess);
        } else {
            leaveMapper.updateProcess(leaveProcess);
        }
    }

    public static void main(String[] args) {
        LeaveProcess leaveProcess = new LeaveProcess();
        System.out.println(leaveProcess.getProcessId());
    }
}

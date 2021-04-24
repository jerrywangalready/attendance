package com.newness.efficient.attendance.clockin.controller;

import com.alibaba.excel.EasyExcel;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.newness.efficient.attendance.clockin.bo.ClockInBo;
import com.newness.efficient.attendance.clockin.bo.ClockInGridBo;
import com.newness.efficient.attendance.clockin.bo.DayBo;
import com.newness.efficient.attendance.clockin.listener.ClockInListener;
import com.newness.efficient.attendance.clockin.service.ClockInService;
import com.newness.efficient.attendance.leave.service.LeaveService;
import com.newness.efficient.attendance.user.bo.Personnel;
import com.newness.efficient.attendance.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/clockIn")
@Slf4j
public class ClockInController {

    @Autowired
    private ClockInService clockInService;

    @Autowired
    private LeaveService leaveService;

    @Autowired
    private UserService userService;

    /**
     * 文件上传
     * <p>1. 创建excel对应的实体对象
     * <p>2. 由于默认一行行的读取excel，所以需要创建excel一行一行的回调监听器
     * <p>3. 直接读即可
     */
    @PostMapping("/import")
    public String importClockInRecords(MultipartFile file) throws IOException {
        EasyExcel.read(file.getInputStream(), ClockInBo.class, new ClockInListener(clockInService)).sheet().doRead();

        List<Map<String, String>> monthOfUnAnalyzed = clockInService.getMonthOfUnAnalyzed();
        for (Map<String, String> map : monthOfUnAnalyzed) {
            analyze(map.get("year"), map.get("month"));
        }
        return "success";
    }

    @PostMapping("/getClockInRecords")
    public PageInfo<Map<String, String>> getClockInRecords(@RequestBody ClockInGridBo param) {
        PageHelper.startPage(param);
        List<Map<String, String>> clockInRecords = clockInService.getClockInRecords(param);
        return new PageInfo<>(clockInRecords);
    }

    private Map<String, List<Map<String, String>>> getUnAnalyzed2UserGroup() {
        ClockInGridBo bo = new ClockInGridBo();
        bo.setAnalyzed(0);
        List<Map<String, String>> list = clockInService.getClockInRecords(bo);
        Map<String, List<Map<String, String>>> usersMap = new HashMap<>();
        list.forEach(item -> {
            String userName = item.get("userName");
            if (!usersMap.containsKey(userName)) {
                usersMap.put(userName, new ArrayList<>());
            }
            usersMap.get(userName).add(item);
        });
        return usersMap;
    }

    private void analyze(String year, String month) {
        // 获取所有在职人员
        List<Personnel> users = getWorkOnUsers();

        List<DayBo> daysOfMonth = clockInService.getDays(year, month);
        // 每个人的未分析的打卡记录 倒序
        Map<String, List<Map<String, String>>> unAnalyzedClockInRecords = getUnAnalyzed2UserGroup();
        // 获取当月所有人的请假信息
        Map<String, List<Map<String, Calendar>>> usersLeaveInfoByMonth = leaveService.getUsersLeaveInfoByMonth(year + "-" + month);
        // todo 获取当月所有人的加班信息

        // 获取上个月最后一天所有人的打卡数据
        Map<String, String> offWorkTimeOfLastMonthLastDay = clockInService.getOffWorkTimeOfLastMonthLastDay(year, month);

        users.forEach(user -> {
            String userName = user.getUserName();
            if (unAnalyzedClockInRecords.containsKey(user.getUserName())) {
                List<Map<String, String>> records = unAnalyzedClockInRecords.get(user.getUserName());
                daysOfMonth.forEach(dayBo -> {
                    for (int i = records.size() - 1; i >= 0; i--) {
                        Map<String, String> rec = records.get(i);
                        if (dayBo.getDate().equals(rec.get("clockInDate"))) {
                            if (isWorkDay(dayBo)) {
                                List<Map<String, String>> leaveInfos = new ArrayList<>();
                                List<Map<String, Calendar>> maps = usersLeaveInfoByMonth.get(userName);
//                                clockInService.analyzeClockInInfoAtWorkingDay(dayBo, offWorkTimeOfLastMonthLastDay.get(userName), rec.get("clockInInfo"), maps);
                            }
                            if (dayBo.getType().equals("H")){

                            }
                            records.remove(i);
                        }
                    }
                });


            } else {
                // todo 全月无打卡记录
            }
        });


        // 循环每个人的打卡信息
        unAnalyzedClockInRecords.forEach((k, v) -> {
//            String offWorkTimeOfTheLastWorkingDay = "";
//            allDays.forEach(theDay -> {
//                // 工作日
//                if ("W".equals(theDay.getType())) {
//                    for (int i = v.size() - 1; i >= 0; i--) {
//                        // 匹配到当天有打卡信息
//                        Map<String, String> map = v.get(i);
//                        if (theDay.getDate().equals(map.get("clockInDate"))) {
//// todo 获取上个月最后一个工作日的下班时间来判定1号是否迟到
//
//
//                            clockInService.analyzeClockInInfoAtWorkingDay(theDay, offWorkTimeOfTheLastWorkingDay,map.get("clockInInfo"),)
//                            v.remove(i);
//                            break;
//                        }
//                    }
//                }
//                for (int i = v.size() - 1; i >= 0; i--) {
//
//                }
//            });
        });
        // todo 通过打卡信息完成分析后,获取请假信息,校核分析结果

    }

    private List<Personnel> getWorkOnUsers() {
        Map<String, String> param = new HashMap<>();
        param.put("state", "1");
        return userService.getUsers(param);
    }

    private boolean isWorkDay(DayBo dayBo) {
        return "W".equals(dayBo.getType()) || "pm".equals(dayBo.getDuring());
    }
}

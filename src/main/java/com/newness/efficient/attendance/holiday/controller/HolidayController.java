package com.newness.efficient.attendance.holiday.controller;

import com.newness.efficient.attendance.holiday.service.HolidayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/holiday")
@Slf4j
public class HolidayController {

    @Autowired
    private HolidayService holidayService;

    @PostMapping("/initHolidaysByYear")
    public boolean initHolidaysByYear(@RequestBody int year) {
        holidayService.initHolidaysByYear(year);
        return true;
    }

    @PostMapping("/getHolidaysByYear")
    public List<Map<String, String>> getHolidaysByYear(@RequestBody Map<String, String> date, HttpServletRequest request) {

//        String key = "gasdwerwefscx232r";
//
//        Calendar calendar = Calendar.getInstance();
//        calendar.add(Calendar.SECOND, 20);
//
//        String token = JWT.create()
//                .withClaim("userId", 123)
//                .withClaim("username", "jwttest")
//                .withExpiresAt(calendar.getTime())
//                .sign(Algorithm.HMAC256(key));
//
//        System.out.println(token);
//
//        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(key)).build();
//
//        DecodedJWT verify = jwtVerifier.verify(token);
//        System.out.println(verify.getClaim("userId").asInt());
//        System.out.println(verify.getClaim("username").asString());
//
//
//        System.out.println(request.getHeader("Authentication-Token"));
//        Enumeration<String> e = request.getHeaderNames();
//        System.out.println(e);

        List<Map<String, String>> dates = new ArrayList<>();
        dates.add(date);

        Map<String, String> lastMonth = new HashMap<>();
        if ("1".equals(date.get("month"))) {
            lastMonth.put("year", String.valueOf(Integer.parseInt(date.get("year")) - 1));
            lastMonth.put("month", "12");
        } else {
            lastMonth.put("year", date.get("year"));
            lastMonth.put("month", String.valueOf(Integer.parseInt(date.get("month")) - 1));
        }
        dates.add(lastMonth);

        Map<String, String> nextMonth = new HashMap<>();
        if ("12".equals(date.get("month"))) {
            nextMonth.put("year", String.valueOf(Integer.parseInt(date.get("year")) + 1));
            nextMonth.put("month", "01");
        } else {
            Map<String, String> map = new HashMap<>();
            nextMonth.put("year", date.get("year"));
            nextMonth.put("month", String.valueOf(Integer.parseInt(date.get("month")) + 1));
        }
        dates.add(nextMonth);

        return holidayService.getHolidaysByYear(dates);
    }

    @PostMapping("/updateHolidays")
    public boolean updateHolidays(@RequestBody List<Map<String, String>> list) {
        holidayService.updateHolidays(list);
        return true;
    }

    @PostMapping("/getBalance")
    public int getBalance() {
          return 0;
    }
}

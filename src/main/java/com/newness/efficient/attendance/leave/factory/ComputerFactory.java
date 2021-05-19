package com.newness.efficient.attendance.leave.factory;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class ComputerFactory {

    @Resource
    Map<String, Computer> computerMap = new HashMap<>();

    {
        computerMap.put("shift", new ShiftComputer());
        computerMap.put("sick", new SickComputer());
        computerMap.put("officeAffairs", new OfficeAffairsComputer());
        computerMap.put("annually", new AnnuallyComputer());
        computerMap.put("privateAffairs", new PrivateAffairsComputer());
        computerMap.put("marriage", new MarriageComputer());
        computerMap.put("birth", new BirthComputer());

        computerMap.put("other", new OtherAffairsComputer());
    }

    public Optional<Computer> getComputer(String type) {
        return Optional.ofNullable(computerMap.get(type));
    }
}

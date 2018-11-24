package com.bester.attendance.controller;

import com.bester.attendance.common.CommonResult;
import com.bester.attendance.entity.Attendance;
import com.bester.attendance.service.AttendanceService;
import com.bester.attendance.service.UserInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class UserAttendanceController {

    @Resource
    private AttendanceService attendanceService;

    @Resource
    private UserInfoService userInfoService;

    @GetMapping("/user/attendance")
    public CommonResult getAttendance() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<Integer> userIdList = userInfoService.userIdList();
        userIdList.forEach(userId ->{

            List<Attendance> userMonthAttendanceList = attendanceService.findMonthAttendanceByUserId(userId);
            Map<Date, List<Attendance>> dateListMap = userMonthAttendanceList.stream().collect(Collectors.groupingBy(Attendance::getAddTime));
            for (Map.Entry<Date, List<Attendance>> entry : dateListMap.entrySet()) {
                entry.getKey();
            }


        });


        return new CommonResult();
    }

}

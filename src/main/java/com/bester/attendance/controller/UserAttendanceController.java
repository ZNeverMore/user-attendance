package com.bester.attendance.controller;

import com.bester.attendance.common.CommonResult;
import com.bester.attendance.entity.Attendance;
import com.bester.attendance.enums.HttpStatus;
import com.bester.attendance.service.AttendanceService;
import com.bester.attendance.service.UserInfoService;
import com.google.common.collect.Maps;
import lombok.Data;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class UserAttendanceController {

    @Resource
    private AttendanceService attendanceService;

    @Resource
    private UserInfoService userInfoService;

    @GetMapping("index")
    public String index() {
        return "hello";
    }

    @GetMapping("/user/attendance")
    public CommonResult getAttendance() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<Integer> userIdList = userInfoService.userIdList();
        if (CollectionUtils.isEmpty(userIdList)) {
            return CommonResult.fail(HttpStatus.NOT_FOUND);
        }
        Map<String, List<UserAttendance>> attendanceMap = Maps.newHashMap();
        userIdList.forEach(userId -> {
            List<UserAttendance> userNormalAttendanceList = new ArrayList<>();
            String userName = userInfoService.getUserNameById(userId);
            List<Attendance> userAllAttendanceList = attendanceService.findAttendanceByUserId(userId);
            if (CollectionUtils.isEmpty(userAllAttendanceList)) {
                return;
            }
            Map<String, List<Attendance>> userDayAttendanceMap = userAllAttendanceList.stream().collect(Collectors.groupingBy(Attendance::getDays));
            for (Map.Entry<String, List<Attendance>> entry : userDayAttendanceMap.entrySet()) {
                List<Attendance> userDayAttendanceList = entry.getValue();
                if (CollectionUtils.isEmpty(userDayAttendanceList) && userDayAttendanceList.size() < 2) {
                    Attendance attendance = userDayAttendanceList.get(0);
                    UserAttendance userAttendance = new UserAttendance(simpleDateFormat.format(attendance.getAddTime()));
                    userNormalAttendanceList.add(userAttendance);
                } else {
                    Date firstIn = userDayAttendanceList.get(0).getAddTime();
                    Date lastOut = userDayAttendanceList.get(userDayAttendanceList.size() - 1).getAddTime();
                    UserAttendance userAttendance = new UserAttendance(simpleDateFormat.format(firstIn), simpleDateFormat.format(lastOut));
                    userNormalAttendanceList.add(userAttendance);
                }
            }
            userNormalAttendanceList.sort(Comparator.comparing(UserAttendance::getFirstIn));
            attendanceMap.put(userName, userNormalAttendanceList);
        });
        return CommonResult.success(attendanceMap);
    }

    @Data
    private class UserAttendance {
        UserAttendance(String firstIn) {
            this.firstIn = firstIn;
        }
        UserAttendance(String firstIn, String lastOut) {
            this.firstIn = firstIn;
            this.lastOut = lastOut;
        }
        private String firstIn;
        private String lastOut;
    }

}

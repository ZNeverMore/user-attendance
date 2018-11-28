package com.bester.attendance.controller;

import com.bester.attendance.common.CommonResult;
import com.bester.attendance.dto.AttendanceDTO;
import com.bester.attendance.enums.HttpStatus;
import com.bester.attendance.service.AttendanceService;
import com.bester.attendance.service.UserInfoService;
import com.google.common.collect.Maps;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zhangqiang
 */
@RestController
public class UserAttendanceController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserAttendanceController.class);

    @Resource
    private AttendanceService attendanceService;

    @Resource
    private UserInfoService userInfoService;

    @GetMapping("index")
    public String index() {
        return "hello";
    }

    @GetMapping("/user/attendance")
    public CommonResult getAttendance(String start, String end) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (StringUtils.isBlank(start) || StringUtils.isBlank(end)) {
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
        try {
            Date startDate = simpleDateFormat.parse(start);
            Date endDate = simpleDateFormat.parse(end);
            if (endDate.before(startDate)) {
                return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
            }
        } catch (ParseException e) {
            LOGGER.error("时间格式错误");
        }
        List<Integer> idList = userInfoService.idList();
        if (CollectionUtils.isEmpty(idList)) {
            return CommonResult.fail(HttpStatus.NOT_FOUND);
        }
        Map<String, List<UserAttendance>> attendanceMap = Maps.newHashMap();
        idList.forEach(id -> {
            List<UserAttendance> userNormalAttendanceList = new ArrayList<>();
            String userName = userInfoService.getUserNameById(id);
            List<AttendanceDTO> userAllAttendanceList = attendanceService.findAttendanceByUserId(id, start, end);
            if (CollectionUtils.isEmpty(userAllAttendanceList)) {
                return;
            }
            Map<String, List<AttendanceDTO>> userDayAttendanceMap = userAllAttendanceList.stream().collect(Collectors.groupingBy(AttendanceDTO::getDays));
            for (Map.Entry<String, List<AttendanceDTO>> entry : userDayAttendanceMap.entrySet()) {
                List<AttendanceDTO> userDayAttendanceList = entry.getValue();
                if (CollectionUtils.isEmpty(userDayAttendanceList) && userDayAttendanceList.size() < 2) {
                    AttendanceDTO attendance = userDayAttendanceList.get(0);
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

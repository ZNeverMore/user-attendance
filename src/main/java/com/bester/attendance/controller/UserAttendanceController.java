package com.bester.attendance.controller;

import com.bester.attendance.common.CommonResult;
import com.bester.attendance.dto.AttendanceDTO;
import com.bester.attendance.entity.UserAttendance;
import com.bester.attendance.enums.HttpStatus;
import com.bester.attendance.service.AttendanceService;
import com.bester.attendance.service.UserInfoService;
import com.bester.attendance.util.ExcelUtil;
import com.google.common.collect.Maps;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;
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
    public CommonResult getAttendance(String start, String end, HttpServletResponse response){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String regex = "^20[1-2]\\d-(0?[1-9]|1[0-2])-(0?[1-9]|[1-2][0-9]|3[0-1])\\s+([0-1]\\d|20|21|22|23):[0-5]\\d:[0-5]\\d$|^$";
        if (!Pattern.matches(regex, start) || !Pattern.matches(regex, end)) {
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
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String startTime;
        String endTime;
        try {
            startTime = dateFormat.parse(start).toString();
            endTime = dateFormat.parse(end).toString();
        } catch (ParseException e) {
            e.printStackTrace();
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
        String fileName = startTime + "至" + endTime + "考勤表.xlsx";
        XSSFWorkbook workbook = ExcelUtil.exportExcel(attendanceMap);
        if (workbook == null) {
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        try {
            response.flushBuffer();
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
            return CommonResult.fail(HttpStatus.ERROR);
        }
        return CommonResult.success(attendanceMap);
    }


}

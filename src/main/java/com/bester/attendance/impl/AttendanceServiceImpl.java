package com.bester.attendance.impl;

import com.bester.attendance.dao.AttendanceDAO;
import com.bester.attendance.dto.AttendanceDTO;
import com.bester.attendance.entity.Attendance;
import com.bester.attendance.service.AttendanceService;
import com.bester.attendance.util.BeansListUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * @author zhangqiang
 */
@Service
public class AttendanceServiceImpl implements AttendanceService {

    @Resource
    private AttendanceDAO attendanceDAO;

    @Override
    public List<AttendanceDTO> findAttendanceByUserId(Integer userId, String start, String end) {
        List<Attendance> attendanceList = attendanceDAO.findAttendanceByUserId(userId, start, end);
        if (CollectionUtils.isEmpty(attendanceList)) {
            return Collections.emptyList();
        }

        return BeansListUtil.copyListProperties(attendanceList, AttendanceDTO.class);
    }
}

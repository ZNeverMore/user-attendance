package com.bester.attendance.impl;

import com.bester.attendance.dao.AttendanceDAO;
import com.bester.attendance.entity.Attendance;
import com.bester.attendance.service.AttendanceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhangqiang
 */
@Service
public class AttendanceServiceImpl implements AttendanceService {

    @Resource
    private AttendanceDAO attendanceDAO;

    @Override
    public List<Attendance> findAttendanceByUserId(Integer userId) {
        return attendanceDAO.findAttendanceByUserId(userId);
    }
}

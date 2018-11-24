package com.bester.attendance.impl;

import com.bester.attendance.dao.AttendanceDAO;
import com.bester.attendance.entity.Attendance;
import com.bester.attendance.service.AttendanceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    @Resource
    private AttendanceDAO attendanceDAO;

    @Override
    public List<Attendance> findMonthAttendanceByUserId(Integer userId) {
        return attendanceDAO.findMonthAttendanceByUserId(userId);
    }
}

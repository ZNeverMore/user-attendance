package com.bester.attendance.service;

import com.bester.attendance.entity.Attendance;

import java.util.List;

public interface AttendanceService {

    List<Attendance> findAttendanceByUserId(Integer userId);

}

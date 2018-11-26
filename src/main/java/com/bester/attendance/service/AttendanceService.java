package com.bester.attendance.service;

import com.bester.attendance.entity.Attendance;

import java.util.List;

/**
 * @author zhangqiang
 */
public interface AttendanceService {

    /**
     * 查找用户的虹膜记录
     *
     * @param userId
     * @return
     */
    List<Attendance> findAttendanceByUserId(Integer userId);

}

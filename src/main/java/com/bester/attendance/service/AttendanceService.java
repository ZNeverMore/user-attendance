package com.bester.attendance.service;

import com.bester.attendance.dto.AttendanceDTO;

import java.util.List;

/**
 * @author zhangqiang
 */
public interface AttendanceService {

    /**
     * 查找用户的虹膜记录
     *
     * @param userId 用户ID
     * @param start 开始时间
     * @param end 结束时间
     * @return
     */
    List<AttendanceDTO> findAttendanceByUserId(Integer userId, String start, String end);

}

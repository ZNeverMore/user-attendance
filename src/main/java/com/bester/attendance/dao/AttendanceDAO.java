package com.bester.attendance.dao;

import com.bester.attendance.entity.Attendance;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AttendanceDAO {

    List<Attendance> findAttendanceByUserId(@Param("userId") Integer userId);

}

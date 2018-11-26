package com.bester.attendance.dao;

import com.bester.attendance.entity.Attendance;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zhangqiang
 */
public interface AttendanceDAO {

    /**
     * 查找用户的虹膜记录
     *
     * @param userId
     * @return
     */
    List<Attendance> findAttendanceByUserId(@Param("userId") Integer userId);

}

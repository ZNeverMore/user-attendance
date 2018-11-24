package com.bester.attendance.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserInfoDAO {


    List<Integer> userIdList();

    String getUserNameById(@Param("userId") Integer userId);

}

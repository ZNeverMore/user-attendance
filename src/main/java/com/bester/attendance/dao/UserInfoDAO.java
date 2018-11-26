package com.bester.attendance.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zhangqiang
 */
public interface UserInfoDAO {

    /**
     * 查找用户ID列表
     *
     * @return
     */
    List<Integer> userIdList();

    /**
     * 通过用户ID查找用户名
     *
     * @param userId
     * @return
     */
    String getUserNameById(@Param("userId") Integer userId);

}

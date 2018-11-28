package com.bester.attendance.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zhangqiang
 */
public interface UserInfoDAO {

    /**
     * 查找ID列表
     *
     * @return
     */
    List<Integer> idList();

    /**
     * 通过用户ID查找用户名
     *
     * @param userId
     * @return
     */
    String getUserNameById(@Param("id") Integer userId);

}

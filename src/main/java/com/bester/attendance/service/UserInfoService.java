package com.bester.attendance.service;

import java.util.List;

/**
 * @author zhangqiang
 */
public interface UserInfoService {

    /**
     * 查找用户ID列表
     *
     * @return
     */
    List<Integer> userIdList();

    /**
     * 根据用户ID查找用户名
     *
     * @param userId
     * @return
     */
    String getUserNameById(Integer userId);

}

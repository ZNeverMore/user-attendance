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
    List<Integer> idList();

    /**
     * 根据用户ID查找用户名
     *
     * @param id
     * @return
     */
    String getUserNameById(Integer id);

}

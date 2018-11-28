package com.bester.attendance.impl;

import com.bester.attendance.dao.UserInfoDAO;
import com.bester.attendance.service.UserInfoService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * @author zhangqiang
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Resource
    private UserInfoDAO userInfoDAO;

    @Override
    public List<Integer> idList() {
        return userInfoDAO.idList();
    }

    @Override
    public String getUserNameById(Integer id) {
        return userInfoDAO.getUserNameById(id);
    }
}

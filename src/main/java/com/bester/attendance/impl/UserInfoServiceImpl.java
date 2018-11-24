package com.bester.attendance.impl;

import com.bester.attendance.dao.UserInfoDAO;
import com.bester.attendance.service.UserInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Resource
    private UserInfoDAO userInfoDAO;

    @Override
    public List<Integer> userIdList() {
        return userInfoDAO.userIdList();
    }

    @Override
    public String getUserNameById(Integer userId) {
        return userInfoDAO.getUserNameById(userId);
    }
}

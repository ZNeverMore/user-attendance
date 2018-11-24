package com.bester.attendance.service;

import java.util.List;

public interface UserInfoService {

    List<Integer> userIdList();

    String getUserNameById(Integer userId);

}

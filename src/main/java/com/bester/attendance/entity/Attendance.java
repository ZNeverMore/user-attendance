package com.bester.attendance.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author zhangqiang
 */
@Data
public class Attendance {

    private Integer id;

    private Integer userId;

    private Date addTime;

    private String days;

}

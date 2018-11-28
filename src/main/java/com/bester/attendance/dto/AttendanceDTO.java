package com.bester.attendance.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author zhangqiang
 */
@Data
public class AttendanceDTO {

    /**
     *主键ID
     */
    private Integer id;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 录入时间
     */
    private Date addTime;

    /**
     * 录入时间（yyyy-MM-dd)
     */
    private String days;

}

package com.bester.attendance.entity;

import lombok.Data;

/**
 * @author zhangqiang
 */
@Data
public class UserAttendance {

    public UserAttendance(String firstIn) {
        this.firstIn = firstIn;
    }

    public UserAttendance(String firstIn, String lastOut) {
        this.firstIn = firstIn;
        this.lastOut = lastOut;
    }

    private String firstIn;
    private String lastOut;

}

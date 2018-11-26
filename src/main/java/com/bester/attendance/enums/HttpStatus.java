package com.bester.attendance.enums;

/**
 * @author zhangqiang
 */

public enum HttpStatus {

    /**
     * 成功
     */
    OK(200,"成功"),

    /**
     * 用户未登录
     */
    UNAUTHORIZED(401, "用户未登录"),

    /**
     * 参数错误
     */
    PARAMETER_ERROR(403,"参数错误"),

    /**
     * 找不到资源
     */
    NOT_FOUND(404,"找不到资源"),

    /**
     * 服务端出错
     */
    ERROR(500,"服务端错误");

    public int value;

    public String message;

    HttpStatus(int value, String message) {
        this.value = value;
        this.message = message;
    }

}

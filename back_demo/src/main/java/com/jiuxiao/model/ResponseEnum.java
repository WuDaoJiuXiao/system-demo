package com.jiuxiao.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Description 返回结果枚举类
 * @Author 悟道九霄
 * @Date 2023/10/9 15:16
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ResponseEnum {

    DEFAULT_SUCCESS(10000, "成功"),
    DEFAULT_ERROR(-10000, "失败"),
    AUTH_NOT_ALLOWED_ERROR(-10001, "权限不足，请联系管理员"),
    NOT_LOGIN_ERROR(-10002, "未登录，请先登录"),
    SQL_DEFAULT_ERROR(-10003, "数据库异常"),

    USER_LOGIN_SUCCESS(20001, "登陆成功"),
    USER_LOGOUT_SUCCESS(20002, "登出成功"),
    USER_INFO_SUCCESS(20003, "用户信息查询成功"),
    USER_LOGIN_USERNAME_OR_PASSWORD_ERROR(-20001, "用户名或密码错误"),
    USER_ACCOUNT_LOCKED_ERROR(-20002, "账户被禁用或权限不足");

    /* 返回的状态码 */
    private Integer code;

    /* 返回的消息 */
    private String message;
}

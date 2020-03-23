/*
 * 文件名称：AuthStatus.java
 * 系统名称：[系统名称]
 * 模块名称：[模块名称]
 * 软件版权：Copyright (c) 2011-2018, liming20110711@163.com All Rights Reserved.
 * 功能说明：[请在此处输入功能说明]
 * 开发人员：Rushing0711
 * 创建日期：20181111 08:23
 * 修改记录：
 * <Version>        <DateSerial>        <Author>        <Description>
 * 1.0.0            20181111-01         Rushing0711     M201811110823 新建文件
 ********************************************************************************/
package com.coding.component.system.exception;

import lombok.Getter;

@Getter
public enum AppStatus {
    SUCCESS(9000, "成功"),

    AUTH_UNAUTHENTICATION(2100, "未认证！"),
    AUTH_USERNAME_PASSWORD_ERROR(2200, "用户名或密码错误"),
    AUTH_TYPE_NOT_SUPPORT(2201, "不支持的登录方式"),
    AUTH_USERNAME_ERROR(2202, "用户不存在"),
    AUTH_PASSWORD_ERROR(2203, "密码错误"),
    AUTH_USERNAME_DISABLED(2301, "账号封停"),
    AUTH_USERNAME_LOCKED(2302, "账号锁定"),
    AUTH_USERNAME_EXPIRED(2303, "账号已过期"),
    AUTH_LOGIN_EXPIRED(2400, "登录信息超时"),
    AUTH_PERMISSION_FORBIDDEN(2500, "用户权限不足"),
    ;

    private Integer code;

    private String message;

    AppStatus(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}

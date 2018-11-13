/*
 * 文件名称：AuthResponse.java
 * 系统名称：[系统名称]
 * 模块名称：[模块名称]
 * 软件版权：Copyright (c) 2011-2018, liming20110711@163.com All Rights Reserved.
 * 功能说明：[请在此处输入功能说明]
 * 开发人员：Rushing0711
 * 创建日期：20181106 15:44
 * 修改记录：
 * <Version>        <DateSerial>        <Author>        <Description>
 * 1.0.0            20181106-01         Rushing0711     M201811061544 新建文件
 ********************************************************************************/
package com.coding.component.security;

import com.coding.component.system.api.AppResponse;
import com.coding.component.system.exception.AppStatus;

public class AuthResponse extends AppResponse {

    private static final long serialVersionUID = -3663932480918437337L;

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public static AuthResponse getAuthResponse(AppStatus appStatus) {
        AuthResponse authResponse = new AuthResponse();
        if (appStatus != null) {
            authResponse.setErrorCode(appStatus.getCode());
            authResponse.setErrorMessage(appStatus.getMessage());
        }
        return authResponse;
    }
}

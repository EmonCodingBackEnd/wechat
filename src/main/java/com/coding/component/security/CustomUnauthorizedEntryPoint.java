/*
 * 文件名称：UnauthorizedEntryPoint.java
 * 系统名称：[系统名称]
 * 模块名称：[模块名称]
 * 软件版权：Copyright (c) 2011-2018, liming20110711@163.com All Rights Reserved.
 * 功能说明：[请在此处输入功能说明]
 * 开发人员：Rushing0711
 * 创建日期：20181104 16:45
 * 修改记录：
 * <Version>        <DateSerial>        <Author>        <Description>
 * 1.0.0            20181104-01         Rushing0711     M201811041645 新建文件
 ********************************************************************************/
package com.coding.component.security;

import com.coding.component.system.exception.AppStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomUnauthorizedEntryPoint implements AuthenticationEntryPoint {
    @Autowired private ObjectMapper objectMapper; // Json转化工具

    @Override
    public void commence(
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            AuthenticationException authException)
            throws IOException, ServletException {
        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        httpServletResponse.setContentType("application/json;charset=UTF-8"); // 响应类型

        AuthResponse authResponse = AuthResponse.getAuthResponse(AppStatus.AUTH_UNAUTHENTICATION);
        authResponse.setErrorMessage("请先登录！");
        httpServletResponse.getWriter().write(objectMapper.writeValueAsString(authResponse));
    }
}

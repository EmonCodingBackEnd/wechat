/*
 * 文件名称：CustomAuthenticationFailureHandler.java
 * 系统名称：[系统名称]
 * 模块名称：[模块名称]
 * 软件版权：Copyright (c) 2011-2018, liming20110711@163.com All Rights Reserved.
 * 功能说明：[请在此处输入功能说明]
 * 开发人员：Rushing0711
 * 创建日期：20181103 10:14
 * 修改记录：
 * <Version>        <DateSerial>        <Author>        <Description>
 * 1.0.0            20181103-01         Rushing0711     M201811031014 新建文件
 ********************************************************************************/
package com.coding.wechat.component.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Autowired private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception)
            throws IOException, ServletException {
        log.info("登录验证失败");
        response.setContentType("application/json;charset=UTF-8");

        String msg;
        if (exception instanceof UsernameNotFoundException
                || exception instanceof BadCredentialsException) {
            msg = "用户名或密码输入错误，登录失败!";
        } else if (exception instanceof DisabledException) {
            msg = "账户被禁用，登录失败，请联系管理员!";
        } else {
            msg = "登录失败，请联系管理员!";
        }
        CustomResponse customResponse = new CustomResponse();
        customResponse.setErrorCode(5100);
        customResponse.setErrorMessage(msg);
        response.getWriter().write(objectMapper.writeValueAsString(customResponse));
    }
}

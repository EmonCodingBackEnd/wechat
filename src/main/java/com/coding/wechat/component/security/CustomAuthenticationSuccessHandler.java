/*
 * 文件名称：CustomAuthenticationSuccessHandler.java
 * 系统名称：[系统名称]
 * 模块名称：[模块名称]
 * 软件版权：Copyright (c) 2011-2018, liming20110711@163.com All Rights Reserved.
 * 功能说明：[请在此处输入功能说明]
 * 开发人员：Rushing0711
 * 创建日期：20181103 10:10
 * 修改记录：
 * <Version>        <DateSerial>        <Author>        <Description>
 * 1.0.0            20181103-01         Rushing0711     M201811031010 新建文件
 ********************************************************************************/
package com.coding.wechat.component.security;

import com.coding.wechat.component.jwt.JwtTokenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired private ObjectMapper objectMapper; // Json转化工具
    @Autowired private JwtTokenUtil jwtTokenUtil;
    @Autowired private StringRedisTemplate stringRedisTemplate;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {

        log.info("登录验证成功");
        CustomUser userDetails = (CustomUser) authentication.getPrincipal();
        String token = jwtTokenUtil.generateToken(userDetails);
        stringRedisTemplate
                .opsForValue()
                .set(userDetails.getUsername(), token, JwtTokenUtil.expiration, TimeUnit.SECONDS);

        response.setContentType("application/json;charset=UTF-8"); // 响应类型
        AppResponse appResponse = new AppResponse();
        appResponse.setToken(token);
        response.getWriter().write(objectMapper.writeValueAsString(appResponse));
    }
}

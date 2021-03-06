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
package com.coding.component.security;

import com.coding.component.cache.redis.RedisCache;
import com.coding.component.security.auth.AuthService;
import com.coding.component.security.auth.LoginResponse;
import com.coding.component.security.auth.LoginSession;
import com.coding.component.security.jwt.JwtRedisKeyUtil;
import com.coding.component.security.jwt.JwtTokenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired private RedisCache redisCache;
    @Autowired private AuthService authService;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        log.info("登录验证成功");
        response.setContentType("application/json;charset=UTF-8"); // 响应类型

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String token = jwtTokenUtil.generateToken(userDetails);
        String redisKey = JwtRedisKeyUtil.getRedisKeyByUsername(userDetails.getUsername());
        stringRedisTemplate
                .opsForValue()
                .set(
                        redisKey,
                        String.valueOf(userDetails.getUserId()),
                        JwtTokenUtil.expiration,
                        TimeUnit.SECONDS);

        LoginSession loginSession = redisCache.userSession(userDetails.getUserId());
        LoginResponse loginResponse = new LoginResponse();
        BeanUtils.copyProperties(loginSession, loginResponse);
        loginResponse.setToken(token);

        authService.loginSuccess(loginSession);
        response.getWriter().write(objectMapper.writeValueAsString(loginResponse));
    }
}

/*
 * 文件名称：AuthController.java
 * 系统名称：[系统名称]
 * 模块名称：[模块名称]
 * 软件版权：Copyright (c) 2011-2018, liming20110711@163.com All Rights Reserved.
 * 功能说明：[请在此处输入功能说明]
 * 开发人员：Rushing0711
 * 创建日期：20181104 13:22
 * 修改记录：
 * <Version>        <DateSerial>        <Author>        <Description>
 * 1.0.0            20181104-01         Rushing0711     M201811041322 新建文件
 ********************************************************************************/
package com.coding.wechat.controller;

import com.coding.wechat.component.jwt.JwtTokenUtil;
import com.coding.wechat.component.security.AppResponse;
import com.coding.wechat.component.security.CustomUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class JWTController {

    @Autowired private StringRedisTemplate stringRedisTemplate;
    @Autowired private UserDetailsService userDetailsService;
    @Autowired private JwtTokenUtil jwtTokenUtil;

    @RequestMapping(value = "/auth/refresh", method = RequestMethod.GET)
    public AppResponse refreshAndGetAuthenticationToken(HttpServletRequest request)
            throws AuthenticationException {
        String refreshedToken = null;

        String authHeader = request.getHeader(JwtTokenUtil.TOKEN_HEADER);
        final String authToken = authHeader.substring(JwtTokenUtil.TOKEN_PREFIX.length());
        String username = jwtTokenUtil.getUsernameFromToken(authToken);
        CustomUser userDetails = (CustomUser) userDetailsService.loadUserByUsername(username);
        if (jwtTokenUtil.canTokenBeRefreshed(authToken, userDetails.getLastPasswordResetDate())) {
            refreshedToken = jwtTokenUtil.refreshToken(authToken);
        }

        AppResponse appResponse = new AppResponse();
        if (refreshedToken == null) {
            log.info("Token刷新失败");
            appResponse.setErrorCode(5100);
            appResponse.setErrorMessage("Token刷新失败");
        } else {
            stringRedisTemplate
                    .opsForValue()
                    .set(
                            userDetails.getUsername(),
                            refreshedToken,
                            JwtTokenUtil.expiration,
                            TimeUnit.SECONDS);
            appResponse.setToken(refreshedToken);
            log.info("Token刷新成功");
        }
        return appResponse;
    }
}

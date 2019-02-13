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
package com.coding.component.security.auth;

import com.coding.component.cache.redis.RedisCache;
import com.coding.component.cache.redis.RedisKeyType;
import com.coding.component.cache.redis.annotation.LoginCache;
import com.coding.component.cache.redis.annotation.RedisKeyCapturer;
import com.coding.component.security.AuthResponse;
import com.coding.component.security.CustomUserDetails;
import com.coding.component.security.CustomWebAuthenticationDetails;
import com.coding.component.security.jwt.JwtRedisKeyUtil;
import com.coding.component.security.jwt.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private AuthService authService;
    @Autowired
    private RedisCache redisCache;

    @PostMapping(value = "/refresh")
    public AuthResponse refreshAndGetAuthenticationToken() throws AuthenticationException {
        String refreshedToken = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        CustomWebAuthenticationDetails details =
                (CustomWebAuthenticationDetails) authentication.getDetails();
        final String authToken = details.getToken();
        if (jwtTokenUtil.canTokenBeRefreshed(authToken, userDetails.getLastPasswordResetDate())) {
            refreshedToken = jwtTokenUtil.refreshToken(authToken);
        }

        AuthResponse appResponse = new AuthResponse();
        if (refreshedToken == null) {
            log.info("Token刷新失败");
            appResponse.setErrorCode(5100);
            appResponse.setErrorMessage("Token刷新失败");
        } else {
            String redisKey = JwtRedisKeyUtil.getRedisKeyByUsername(userDetails.getUsername());
            stringRedisTemplate
                    .opsForValue()
                    .set(
                            redisKey,
                            String.valueOf(userDetails.getUserId()),
                            JwtTokenUtil.expiration,
                            TimeUnit.SECONDS);
            appResponse.setToken(refreshedToken);
            log.info("Token刷新成功");
        }
        return appResponse;
    }

    @PostMapping(value = "/switchSystem")
    public LoginResponse switchSystem(@LoginCache LoginSession loginSession, String systemType) {
        Long userId = loginSession.getUserId();
        authService.switchSystem(userId, Integer.valueOf(systemType));

        loginSession = redisCache.userSession(userId);
        LoginResponse loginResponse = new LoginResponse();
        BeanUtils.copyProperties(loginSession, loginResponse);
        return loginResponse;
    }

    @PostMapping(value = "/switchShop")
    @RedisKeyCapturer(value = "userId", prefix = RedisKeyType.USERINFO_SESSION)
    public LoginResponse switchShop(@LoginCache("userId") Long userId, String shopId) {
        authService.switchShop(userId, Long.valueOf(shopId));

        LoginSession loginSession = redisCache.userSession(userId);
        LoginResponse loginResponse = new LoginResponse();
        BeanUtils.copyProperties(loginSession, loginResponse);
        return loginResponse;
    }
}

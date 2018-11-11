/*
 * 文件名称：CustomUserDetailsService.java
 * 系统名称：[系统名称]
 * 模块名称：[模块名称]
 * 软件版权：Copyright (c) 2011-2018, liming20110711@163.com All Rights Reserved.
 * 功能说明：[请在此处输入功能说明]
 * 开发人员：Rushing0711
 * 创建日期：20181103 09:58
 * 修改记录：
 * <Version>        <DateSerial>        <Author>        <Description>
 * 1.0.0            20181103-01         Rushing0711     M201811030958 新建文件
 ********************************************************************************/
package com.coding.component.security;

import com.coding.component.cache.redis.RedisCache;
import com.coding.component.security.jwt.JwtRedisKeyUtil;
import com.coding.component.security.auth.AuthService;
import com.coding.component.security.auth.LoginAuthority;
import com.coding.component.security.auth.LoginSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired private RedisCache redisCache;
    @Autowired private AuthService authService;
    @Autowired private StringRedisTemplate stringRedisTemplate;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String[] authParams =
                StringUtils.delimitedListToStringArray(username, CustomSecurityConstant.delimiter);
        String authType = authParams[0];
        String authName = authParams[1];

        LoginAuthority loginAuthority;
        String redisKey = JwtRedisKeyUtil.getRedisKeyByUsername(username);
        if (stringRedisTemplate.opsForValue().getOperations().hasKey(redisKey)) {
            String userId = stringRedisTemplate.opsForValue().get(redisKey);
            LoginSession loginSession = redisCache.userSession(Long.valueOf(userId));
            loginAuthority = loginSession.getLoginAuthority();
        } else {
            loginAuthority = authService.login(authType, authName);
        }

        Long userId = loginAuthority.getUserId();
        String password = loginAuthority.getPassword();
        List<GrantedAuthority> authorityList =
                loginAuthority
                        .getRoleIdList()
                        .stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
        return new CustomUserDetails(userId, username, password, authorityList);
    }

    public static void main(String[] args) {
        PasswordEncoder pe = new BCryptPasswordEncoder();
        System.out.println(pe.encode("123456"));
    }
}

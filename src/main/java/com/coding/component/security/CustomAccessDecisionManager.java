/*
 * 文件名称：CustomAccessDecisionManager.java
 * 系统名称：[系统名称]
 * 模块名称：[模块名称]
 * 软件版权：Copyright (c) 2011-2018, liming20110711@163.com All Rights Reserved.
 * 功能说明：[请在此处输入功能说明]
 * 开发人员：Rushing0711
 * 创建日期：20181103 12:23
 * 修改记录：
 * <Version>        <DateSerial>        <Author>        <Description>
 * 1.0.0            20181103-01         Rushing0711     M201811031223 新建文件
 ********************************************************************************/
package com.coding.component.security;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class CustomAccessDecisionManager implements AccessDecisionManager {
    @Override
    public void decide(
            Authentication authentication,
            Object object,
            Collection<ConfigAttribute> configAttributes)
            throws AccessDeniedException, InsufficientAuthenticationException {
        // 迭代器遍历目标url的权限列表
        for (ConfigAttribute ca : configAttributes) {
            // 当前请求需要的权限
            String needPermission = ca.getAttribute();
            if ("ROLE_LOGIN".equals(needPermission)) {
                if (authentication instanceof AnonymousAuthenticationToken) {
                    throw new BadCredentialsException("未登录");
                } else {
                    return;
                }
            }

            // 遍历当前用户所具有的权限
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

            for (GrantedAuthority authority : authorities) {
                // 如果是系统管理员
                if (authority.getAuthority().equals(CustomSecurityConstant.ADMIN)) {
                    return;
                }
                if (authority.getAuthority().equals(needPermission)) {
                    return;
                }
            }
        }

        // 执行到这里说明没有匹配到应有权限
        throw new AccessDeniedException("权限不足!");
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}

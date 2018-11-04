/*
 * 文件名称：RbacAuthorityService.java
 * 系统名称：[系统名称]
 * 模块名称：[模块名称]
 * 软件版权：Copyright (c) 2011-2018, liming20110711@163.com All Rights Reserved.
 * 功能说明：[请在此处输入功能说明]
 * 开发人员：Rushing0711
 * 创建日期：20181104 18:39
 * 修改记录：
 * <Version>        <DateSerial>        <Author>        <Description>
 * 1.0.0            20181104-01         Rushing0711     M201811041839 新建文件
 ********************************************************************************/
package com.coding.wechat.component.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Component("rbacauthorityservice")
public class RbacAuthorityService {
    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {

        // 得到的principal的信息是用户名还是整个用户信息取决于在自定义的authenticationProvider中传参的方式
        Object userInfo = authentication.getPrincipal();

        boolean hasPermission = false;

        if (userInfo instanceof UserDetails) {

            String username = ((UserDetails) userInfo).getUsername();

            Collection<? extends GrantedAuthority> authorities =
                    ((UserDetails) userInfo).getAuthorities();
            for (GrantedAuthority authority : authorities) {
                if (authority.getAuthority().equals("ROLE_ADMIN")) {
                    // admin 可以访问的资源
                    Set<String> urls = new HashSet<>();
                    urls.add("/sys/**");
                    urls.add("/test/**");
                    for (String url : urls) {
                        if (antPathMatcher.match(url, request.getRequestURI())) {
                            hasPermission = true;
                            break;
                        }
                    }
                }
            }
            // user可以访问的资源
            Set<String> urls = new HashSet<>();
            urls.add("/test/**");
            for (String url : urls) {
                if (antPathMatcher.match(url, request.getRequestURI())) {
                    hasPermission = true;
                    break;
                }
            }
            return hasPermission;
        } else {
            return false;
        }
    }
}

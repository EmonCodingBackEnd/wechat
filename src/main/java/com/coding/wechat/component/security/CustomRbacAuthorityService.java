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

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@Component("rbacauthorityservice")
public class CustomRbacAuthorityService {
    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    private static List<CustomMenu> menuList = null;

    public CustomRbacAuthorityService() {
        loadResourcePermission();
    }

    /** 加载资源权限 */
    private void loadResourcePermission() {
        menuList = new ArrayList<>();
        CustomMenu menu = new CustomMenu(1057479924283162624L, "/hq/goods/**");
        menu.getRoleList().add(new CustomRole(1007439783886909440L, "角色A", 1));
        menu.getRoleList().add(new CustomRole(1007439907706957824L, "角色B", 2));
        menuList.add(menu);
        menu = new CustomMenu(1057480031791562752L, "/hq/goods/list/**");
        menu.getRoleList().add(new CustomRole(1007439907706957824L, "角色B", 2));
        menu.getRoleList().add(new CustomRole(1007439907706957820L, "角色D", 4));
        menuList.add(menu);
        menu = new CustomMenu(1057480080684564480L, "/hq/goods/category/**");
        menu.getRoleList().add(new CustomRole(1011921894543003648L, "角色C", 3));
        menuList.add(menu);
    }

    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        String url = UrlUtils.buildRequestUrl(request);

        // 完全开放，不需要登录，也不需要有对应角色
        if (antPathMatcher.match("/pub/**", url)) {
            return true;
        }

        // 访问该资源需要什么权限
        Collection<ConfigAttribute> configAttributes;
        {
            List<String> unionRoleIdList = new ArrayList<>();
            for (CustomMenu menu : menuList) {
                if (antPathMatcher.match(menu.getUrlPattern(), url)
                        && menu.getRoleList().size() > 0) {
                    List<CustomRole> roleList = menu.getRoleList();
                    List<String> roleIdList =
                            roleList.stream()
                                    .map(e -> String.valueOf(e.getId()))
                                    .collect(Collectors.toList());
                    unionRoleIdList.addAll(roleIdList);
                }
            }
            if (unionRoleIdList.size() > 0) {
                unionRoleIdList = unionRoleIdList.stream().distinct().collect(Collectors.toList());
                configAttributes =
                        SecurityConfig.createList(unionRoleIdList.toArray(new String[] {}));
            } else {
                // 没有匹配上的资源，都是登录访问
                configAttributes = SecurityConfig.createList("ROLE_LOGIN");
            }
        }

        {
            // 匹配的数量
            int matchCounter = 0;

            // 迭代器遍历目标url的权限列表
            for (ConfigAttribute ca : configAttributes) {
                // 当前请求需要的权限
                String needPermission = ca.getAttribute();
                if ("ROLE_LOGIN".equals(needPermission)) {
                    if (authentication instanceof AnonymousAuthenticationToken) {
                        throw new BadCredentialsException("未登录");
                    } else {
                        return true;
                    }
                }

                // 遍历当前用户所具有的权限
                Collection<? extends GrantedAuthority> authorities =
                        authentication.getAuthorities();

                for (GrantedAuthority authority : authorities) {
                    // 如果是超级用户
                    if (authority.getAuthority().equals(String.valueOf("10000"))) {
                        return true;
                    }
                    if (authority.getAuthority().equals(needPermission)) {
                        matchCounter++;
                        //                    return;
                    }
                }
            }
            if (configAttributes.size() == matchCounter) {
                return true;
            }

            // 执行到这里说明没有匹配到应有权限
            throw new AccessDeniedException("权限不足!");
        }
    }
}

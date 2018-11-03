/*
 * 文件名称：CustomFilterInvocationSecurityMetadataSource.java
 * 系统名称：[系统名称]
 * 模块名称：[模块名称]
 * 软件版权：Copyright (c) 2011-2018, liming20110711@163.com All Rights Reserved.
 * 功能说明：[请在此处输入功能说明]
 * 开发人员：Rushing0711
 * 创建日期：20181103 11:01
 * 修改记录：
 * <Version>        <DateSerial>        <Author>        <Description>
 * 1.0.0            20181103-01         Rushing0711     M201811031101 新建文件
 ********************************************************************************/
package com.coding.wechat.component.security;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomFilterInvocationSecurityMetadataSource
        implements FilterInvocationSecurityMetadataSource {

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    private static List<CustomMenu> menuList = null;

    public CustomFilterInvocationSecurityMetadataSource() {
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
        menuList.add(menu);
        menu = new CustomMenu(1057480080684564480L, "/hq/goods/category/**");
        menu.getRoleList().add(new CustomRole(1011921894543003648L, "角色C", 3));
        menuList.add(menu);
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object)
            throws IllegalArgumentException {
        String url = ((FilterInvocation) object).getRequestUrl();
        if (antPathMatcher.match("/login_prepare", url)) {
            return null;
        }

        List<String> unionRoleIdList = new ArrayList<>();
        for (CustomMenu menu : menuList) {
            if (antPathMatcher.match(menu.getUrlPattern(), url) && menu.getRoleList().size() > 0) {
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
            return SecurityConfig.createList(unionRoleIdList.toArray(new String[] {}));
        }

        // 没有匹配上的资源，都是登录访问
        return SecurityConfig.createList("ROLE_LOGIN");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}

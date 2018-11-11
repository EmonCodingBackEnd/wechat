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
package com.coding.component.security;

import com.coding.component.cache.redis.cache.RedisCache;
import com.coding.component.security.auth.SystemInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomFilterInvocationSecurityMetadataSource
        implements FilterInvocationSecurityMetadataSource {

    public static final String EMPTY = "";

    @Autowired
    private RedisCache redisCache;

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object)
            throws IllegalArgumentException {
        String url = ((FilterInvocation) object).getRequestUrl();

        List<String> unionRoleIdList = new ArrayList<>();
        List<SystemInfo.MenuDTO> menuDTOList = redisCache.systemInfo().getMenuDTOList();
        for (SystemInfo.MenuDTO menuDTO : menuDTOList) {
            String urlPattern =
                    StringUtils.hasText(menuDTO.getUrlPattern()) ? menuDTO.getUrlPattern() : EMPTY;
            if (antPathMatcher.match(urlPattern, url) && menuDTO.getRoleList().size() > 0) {
                unionRoleIdList.addAll(menuDTO.getRoleList());
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

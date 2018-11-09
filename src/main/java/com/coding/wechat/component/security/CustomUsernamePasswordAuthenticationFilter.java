/*
 * 文件名称：CustomUsernamePasswordAuthenticationFilter.java
 * 系统名称：[系统名称]
 * 模块名称：[模块名称]
 * 软件版权：Copyright (c) 2011-2018, liming20110711@163.com All Rights Reserved.
 * 功能说明：[请在此处输入功能说明]
 * 开发人员：Rushing0711
 * 创建日期：20181109 10:02
 * 修改记录：
 * <Version>        <DateSerial>        <Author>        <Description>
 * 1.0.0            20181109-01         Rushing0711     M201811091002 新建文件
 ********************************************************************************/
package com.coding.wechat.component.security;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class CustomUsernamePasswordAuthenticationFilter
        extends UsernamePasswordAuthenticationFilter {

    @Override
    protected String obtainUsername(HttpServletRequest request) {
        String authType = request.getParameter("authType");
        authType =
                StringUtils.isEmpty(authType)
                        ? CustomSecurityConstant.EMPTY
                        : StringUtils.trimAllWhitespace(authType);
        return authType.concat(CustomSecurityConstant.delimiter).concat(super.obtainUsername(request));
    }
}

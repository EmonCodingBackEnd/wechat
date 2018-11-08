/*
 * 文件名称：CustomRequestDetails.java
 * 系统名称：[系统名称]
 * 模块名称：[模块名称]
 * 软件版权：Copyright (c) 2011-2018, liming20110711@163.com All Rights Reserved.
 * 功能说明：[请在此处输入功能说明]
 * 开发人员：Rushing0711
 * 创建日期：20181108 14:09
 * 修改记录：
 * <Version>        <DateSerial>        <Author>        <Description>
 * 1.0.0            20181108-01         Rushing0711     M201811081409 新建文件
 ********************************************************************************/
package com.coding.wechat.component.security;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 封装登录的请求细节.
 *
 * <p>创建时间: <font style="color:#00FFFF">20181108 14:09</font><br>
 * 作为 UsernamePasswordAuthenticationFilter 类的setDetails方法参数
 *
 * @author Rushing0711
 * @version 1.0.0
 * @since 1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CustomWebAuthenticationDetails extends WebAuthenticationDetails {
    private static final long serialVersionUID = -8321053595128994690L;

    public CustomWebAuthenticationDetails(HttpServletRequest request) {
        super(request);
        this.remoteAddress = request.getRemoteAddr();

        HttpSession session = request.getSession(false);
        this.sessionId = (session != null) ? session.getId() : null;

        this.authType = request.getParameter("authType");
    }

    private final String remoteAddress;

    private final String sessionId;

    /** 认证类型. */
    private String authType;
}

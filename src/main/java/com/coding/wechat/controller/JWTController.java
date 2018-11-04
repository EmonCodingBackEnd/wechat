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

import com.coding.wechat.component.jwt.AuthService;
import com.coding.wechat.component.jwt.JwtTokenUtil;
import com.coding.wechat.component.security.AppResponse;
import com.coding.wechat.component.security.CustomUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
public class JWTController {

    @Autowired private AuthService authService;

    @RequestMapping(value = "/auth/register", method = RequestMethod.POST)
    public CustomUser register(@RequestBody CustomUser addedUser) throws AuthenticationException {
        return authService.register(addedUser);
    }

    @RequestMapping(value = "/auth/refresh", method = RequestMethod.GET)
    public AppResponse refreshAndGetAuthenticationToken(HttpServletRequest request)
            throws AuthenticationException {
        String token = request.getHeader(JwtTokenUtil.TOKEN_HEADER);
        String refreshedToken = authService.refresh(token);
        AppResponse appResponse = new AppResponse();
        if (refreshedToken == null) {
            log.info("Token刷新失败");
            appResponse.setErrorCode(5100);
            appResponse.setErrorMessage("Token刷新失败");
        } else {
            appResponse.setToken(refreshedToken);
            log.info("Token刷新成功");
        }
        return appResponse;
    }
}

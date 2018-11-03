/*
 * 文件名称：SecurityController.java
 * 系统名称：[系统名称]
 * 模块名称：[模块名称]
 * 软件版权：Copyright (c) 2011-2018, liming20110711@163.com All Rights Reserved.
 * 功能说明：[请在此处输入功能说明]
 * 开发人员：Rushing0711
 * 创建日期：20181103 17:42
 * 修改记录：
 * <Version>        <DateSerial>        <Author>        <Description>
 * 1.0.0            20181103-01         Rushing0711     M201811031742 新建文件
 ********************************************************************************/
package com.coding.wechat.controller;

import com.coding.wechat.component.security.AppResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@Slf4j
public class SecurityController {
    @Autowired private ObjectMapper objectMapper;

    @GetMapping("/login_prepare")
    public void introduction(HttpServletResponse response) throws IOException {
        log.info("未登录");
        response.setContentType("application/json;charset=UTF-8");
        AppResponse appResponse = new AppResponse();
        appResponse.setErrorCode(5100);
        appResponse.setErrorMessage("未登录");
        response.getWriter().write(objectMapper.writeValueAsString(appResponse));
    }
}

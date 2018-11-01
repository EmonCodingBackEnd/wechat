/*
 * 文件名称：SpringSessionController.java
 * 系统名称：[系统名称]
 * 模块名称：[模块名称]
 * 软件版权：Copyright (c) 2011-2018, liming20110711@163.com All Rights Reserved.
 * 功能说明：[请在此处输入功能说明]
 * 开发人员：Rushing0711
 * 创建日期：20181102 00:13
 * 修改记录：
 * <Version>        <DateSerial>        <Author>        <Description>
 * 1.0.0            20181102-01         Rushing0711     M201811020013 新建文件
 ********************************************************************************/
package com.coding.wechat.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Spring Session.
 *
 * <p>创建时间: <font style="color:#00FFFF">20181102 00:14</font><br>
 * [请在此输入功能详述]
 *
 * @author Rushing0711
 * @version 1.0.0
 * @since 1.0.0
 */
@RestController
@RequestMapping("/session")
@Slf4j
public class SpringSessionController {

    @ResponseBody
    @RequestMapping(value = "/setSession")
    public Map<String, Object> setSession(HttpServletRequest request) {
        request.getSession().setAttribute("username", "admin");
        Map<String, Object> map = new HashMap<>();
        map.put("sessionId", request.getSession().getId());
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/getSession")
    public Map<String, Object> getSession(HttpServletRequest request) {
        String userName = (String) request.getSession().getAttribute("username");
        Map<String, Object> map = new HashMap<>();
        map.put("userName", userName);
        map.put("sessionId", request.getSession().getId());
        return map;
    }
}

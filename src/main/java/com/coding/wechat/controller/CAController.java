/*
 * 文件名称：CAController.java
 * 系统名称：[系统名称]
 * 模块名称：[模块名称]
 * 软件版权：Copyright (c) 2011-2018, liming20110711@163.com All Rights Reserved.
 * 功能说明：[请在此处输入功能说明]
 * 开发人员：Rushing0711
 * 创建日期：20180427 16:52
 * 修改记录：
 * <Version>        <DateSerial>        <Author>        <Description>
 * 1.0.0            20180427-01         Rushing0711     M201804271652 新建文件
 ********************************************************************************/
package com.coding.wechat.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

/**
 * [请在此输入功能简述].
 *
 * <p>创建时间: <font style="color:#00FFFF">20180427 16:53</font><br>
 * [请在此输入功能详述]
 *
 * @author Rushing0711
 * @version 1.0.0
 * @since 1.0.0
 */
@Controller
@Slf4j
public class CAController {

    @GetMapping("/")
    public String introduction(Map<String, Object> map) {
        String redirectUrl = "http://www.emon.vip/.well-known/pki-validation/fileauth.txt";
        return "redirect:" + redirectUrl;
    }

}

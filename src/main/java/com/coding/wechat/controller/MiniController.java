/*
 * 文件名称：MiniController.java
 * 系统名称：[系统名称]
 * 模块名称：[模块名称]
 * 软件版权：Copyright (c) 2011-2018, liming20110711@163.com All Rights Reserved.
 * 功能说明：[请在此处输入功能说明]
 * 开发人员：Rushing0711
 * 创建日期：20180504 14:13
 * 修改记录：
 * <Version>        <DateSerial>        <Author>        <Description>
 * 1.0.0            20180504-01         Rushing0711     M201805041413 新建文件
 ********************************************************************************/
package com.coding.wechat.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

/**
 * 小程序后端.
 *
 * <p>创建时间: <font style="color:#00FFFF">20180504 14:13</font><br>
 * [请在此输入功能详述]
 *
 * @author Rushing0711
 * @version 1.0.0
 * @since 1.0.0
 */
@RestController
@RequestMapping("/mini")
@Slf4j
public class MiniController {

    @GetMapping(value = "/message", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String checkSignature(
            @RequestParam(value = "name") String name, @RequestParam("age") int age) {
        log.info("name={},age={}", name, age);
        return "测试";
    }
}

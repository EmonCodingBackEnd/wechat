/*
 * 文件名称：EmonController.java
 * 系统名称：一萌笔记
 * 模块名称：首页模块
 * 软件版权：Copyright (c) 2011-2018, liming20110711@163.com All Rights Reserved.
 * 功能说明：负责首页展示内容的生成
 * 开发人员：Rushing0711
 * 创建日期：20180317 09:29
 * 修改记录：
 * <Version>        <DateSerial>        <Author>        <Description>
 * 1.0.0            20180317-01         Rushing0711     M201803170929 新建文件
 ********************************************************************************/
package com.coding.wechat.controller;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * 负责首页展示内容的生成.
 *
 * <p>创建时间: <font style="color:#00FFFF">20180317 09:30</font><br>
 * [请在此输入功能详述]
 *
 * @author Rushing0711
 * @version 1.0.0
 * @since 1.0.0
 */
@Controller
@RequestMapping("/website")
public class WebsiteController {

    @GetMapping("/introduction")
    public ModelAndView introduction(Map<String, Object> map) {
        DateTime beginCreateDateTime = new DateTime(2018, 2, 27, 0, 0, 0);
        DateTime beginParseDateTime = new DateTime(2018, 3, 20, 0, 0, 0);
        DateTime currentDateTime = new DateTime();

        String currentDate = currentDateTime.toString("yyyy年MM月dd日");
        int websiteRanDay = Days.daysBetween(beginCreateDateTime, currentDateTime).getDays() + 1;
        int websiteParseDay = Days.daysBetween(beginParseDateTime, currentDateTime).getDays() + 1;

        map.put("currentDate", currentDate);
        map.put("websiteRanDay", websiteRanDay);
        map.put("websiteParseDay", websiteParseDay);

        return new ModelAndView("index", map);
    }
}

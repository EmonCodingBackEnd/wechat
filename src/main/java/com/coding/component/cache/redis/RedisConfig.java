/*
 * 文件名称：RedisConfig.java
 * 系统名称：[系统名称]
 * 模块名称：[模块名称]
 * 软件版权：Copyright (c) 2011-2018, liming20110711@163.com All Rights Reserved.
 * 功能说明：[请在此处输入功能说明]
 * 开发人员：Rushing0711
 * 创建日期：20181107 16:24
 * 修改记录：
 * <Version>        <DateSerial>        <Author>        <Description>
 * 1.0.0            20181107-01         Rushing0711     M201811071624 新建文件
 ********************************************************************************/
package com.coding.component.cache.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "redis")
@Slf4j
public class RedisConfig {

    public static final String prefixHead = "wechat";

    public static final String delimiter = ":";

    public static final String EMPTY = "";
}

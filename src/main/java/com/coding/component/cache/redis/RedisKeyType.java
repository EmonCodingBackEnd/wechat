/*
 * 文件名称：RedisKeyType.java
 * 系统名称：[系统名称]
 * 模块名称：[模块名称]
 * 软件版权：Copyright (c) 2011-2018, liming20110711@163.com All Rights Reserved.
 * 功能说明：[请在此处输入功能说明]
 * 开发人员：Rushing0711
 * 创建日期：20181107 16:02
 * 修改记录：
 * <Version>        <DateSerial>        <Author>        <Description>
 * 1.0.0            20181107-01         Rushing0711     M201811071602 新建文件
 ********************************************************************************/
package com.coding.component.cache.redis;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public enum RedisKeyType {
    USERINFO_SESSION("user", "cache", "string", "用户登录缓存信息"),
    SYSTEM_INFO("system", "cache", "string", "系统缓存信息"),
    ORDER("order", "lock", "string", "下单时锁定商品库存"),
    ;

    RedisKeyType(String serverName, String purpose, String dataType, String description) {
        keys.add(RedisConfig.prefixHead);
        keys.add(StringUtils.trimWhitespace(serverName));
        keys.add(StringUtils.trimWhitespace(purpose));
        keys.add(StringUtils.trimWhitespace(dataType));
        keys.add(RedisConfig.EMPTY);
        this.description = description;
    }

    private List<String> keys = new ArrayList<>();
    private String description;

    public String getKey() {
        return StringUtils.collectionToDelimitedString(keys, RedisConfig.delimiter);
    }

    public String getDescription() {
        return description;
    }
}

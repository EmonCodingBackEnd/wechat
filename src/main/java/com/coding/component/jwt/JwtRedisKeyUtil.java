/*
 * 文件名称：JwtRedisKeyUtil.java
 * 系统名称：[系统名称]
 * 模块名称：[模块名称]
 * 软件版权：Copyright (c) 2011-2018, liming20110711@163.com All Rights Reserved.
 * 功能说明：[请在此处输入功能说明]
 * 开发人员：Rushing0711
 * 创建日期：20181106 12:37
 * 修改记录：
 * <Version>        <DateSerial>        <Author>        <Description>
 * 1.0.0            20181106-01         Rushing0711     M201811061237 新建文件
 ********************************************************************************/
package com.coding.component.jwt;

import com.coding.component.cache.redis.RedisKeyGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JwtRedisKeyUtil {

    private static RedisKeyGenerator redisKeyGenerator;

    @Autowired
    public void setRedisKeyGenerator(RedisKeyGenerator redisKeyGenerator) {
        JwtRedisKeyUtil.redisKeyGenerator = redisKeyGenerator;
    }

    public static String getRedisKeyByUsername(String username) {
        return redisKeyGenerator.generate("user", "token", "string", username);
    }
}

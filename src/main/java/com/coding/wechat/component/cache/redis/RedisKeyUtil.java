/*
 * 文件名称：RedisKeyUtil.java
 * 系统名称：[系统名称]
 * 模块名称：[模块名称]
 * 软件版权：Copyright (c) 2011-2018, liming20110711@163.com All Rights Reserved.
 * 功能说明：[请在此处输入功能说明]
 * 开发人员：Rushing0711
 * 创建日期：20181106 15:00
 * 修改记录：
 * <Version>        <DateSerial>        <Author>        <Description>
 * 1.0.0            20181106-01         Rushing0711     M201811061500 新建文件
 ********************************************************************************/
package com.coding.wechat.component.cache.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class RedisKeyUtil {

    private static String prefixHead;

    @Value("${redis.key.prefix:eden}")
    public void setPrefixHead(String prefixHead) {
        RedisKeyUtil.prefixHead = prefixHead;
    }

    private static final String delimiter = ":";

    private static RedisKeyGenerator redisKeyGenerator;

    @Autowired
    public void setRedisKeyGenerator(RedisKeyGenerator redisKeyGenerator) {
        RedisKeyUtil.redisKeyGenerator = redisKeyGenerator;
    }

    /** 订单锁的Redis Key. */
    public static String getRedisKeyByGoodsId(String goodsId) {
        return redisKeyGenerator.generate("order", "lock", "string", goodsId);
    }

    public enum RedisKeyType {
        USERINFO("user", "cache", "string", "用户登录后的相关信息"),
        ORDER("order", "lock", "string", "下单时锁定商品库存"),
        ;

        RedisKeyType(String serverName, String purpose, String dataType, String description) {
            keys.add(prefixHead);
            keys.add(StringUtils.trimWhitespace(serverName));
            keys.add(StringUtils.trimWhitespace(purpose));
            keys.add(StringUtils.trimWhitespace(dataType));
            this.description = description;
        }

        private List<String> keys = new ArrayList<>();
        private String description;

        public String getKey() {
            return StringUtils.collectionToDelimitedString(keys, delimiter);
        }

        public String getDescription() {
            return description;
        }

        @Override
        public String toString() {
            return getKey();
        }
    }
}

/*
 * 文件名称：LoginCache.java
 * 系统名称：[系统名称]
 * 模块名称：[模块名称]
 * 软件版权：Copyright (c) 2011-2018, liming20110711@163.com All Rights Reserved.
 * 功能说明：[请在此处输入功能说明]
 * 开发人员：Rushing0711
 * 创建日期：20181111 19:20
 * 修改记录：
 * <Version>        <DateSerial>        <Author>        <Description>
 * 1.0.0            20181111-01         Rushing0711     M201811111920 新建文件
 ********************************************************************************/
package com.coding.component.cache.redis.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LoginCache {
    /** 来自LoginSession的字段属性. */
    @AliasFor("fieldName")
    String value() default "";

    @AliasFor("value")
    String fieldName() default "";
}

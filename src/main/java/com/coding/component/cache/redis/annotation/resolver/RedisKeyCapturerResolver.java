/*
 * 文件名称：RedisKeyCaptureResolver.java
 * 系统名称：[系统名称]
 * 模块名称：[模块名称]
 * 软件版权：Copyright (c) 2011-2018, liming20110711@163.com All Rights Reserved.
 * 功能说明：[请在此处输入功能说明]
 * 开发人员：Rushing0711
 * 创建日期：20181112 04:11
 * 修改记录：
 * <Version>        <DateSerial>        <Author>        <Description>
 * 1.0.0            20181112-01         Rushing0711     M201811120411 新建文件
 ********************************************************************************/
package com.coding.component.cache.redis.annotation.resolver;

import com.coding.component.cache.redis.annotation.RedisKeyCapturer;
import com.coding.component.cache.redis.RedisCache;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;

@Aspect
@Component
@Slf4j
public class RedisKeyCapturerResolver {

    @Pointcut("@annotation(redisKeyCapturer)")
    public void publicMethods(RedisKeyCapturer redisKeyCapturer) {}

    @AfterReturning(value = "publicMethods(redisKeyCapturer)", argNames = "jp,redisKeyCapturer")
    public void instrumentMetered(JoinPoint jp, RedisKeyCapturer redisKeyCapturer) {
        String fieldName = redisKeyCapturer.value();
        String prefix = redisKeyCapturer.prefix().getKey();

        String[] paramNames = ((CodeSignature) jp.getSignature()).getParameterNames();
        Object[] paramValues = jp.getArgs();
        String redisKey = null;
        for (int i = 0; i < paramNames.length; i++) {
            String paramName = paramNames[i];
            Object paramValue = paramValues[i];
            if (ClassUtils.isPrimitiveOrWrapper(paramValue.getClass())
                    || String.class.isAssignableFrom(paramValue.getClass())) {
                if (paramName.equals(StringUtils.trimWhitespace(fieldName))) {
                    redisKey = prefix + paramValue;
                    break;
                }
            } else {
                Field field = ReflectionUtils.findField(paramValue.getClass(), fieldName);
                if (field != null) {
                    ReflectionUtils.makeAccessible(field);
                    try {
                        Object fieldValue = field.get(paramValue);
                        redisKey = prefix + fieldValue;
                        break;
                    } catch (IllegalAccessException e) {
                        log.error("【RedisKeyCapturerResolver】获取RedisKey错误", e);
                    }
                }
            }
        }
        if (redisKey != null) {
            RedisCache.deleteInfo(redisKey);
        } else {
            log.error(
                    "【RedisKeyCapturerResolver】无法获取注解@RedisKeyCapturer指定的redis key信息，请检查value指定的属性名称是否正确");
        }
    }
}

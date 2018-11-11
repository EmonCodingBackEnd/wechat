/*
 * 文件名称：RedisKeyCapturerArgumentResolver.java
 * 系统名称：[系统名称]
 * 模块名称：[模块名称]
 * 软件版权：Copyright (c) 2011-2018, liming20110711@163.com All Rights Reserved.
 * 功能说明：[请在此处输入功能说明]
 * 开发人员：Rushing0711
 * 创建日期：20181111 18:45
 * 修改记录：
 * <Version>        <DateSerial>        <Author>        <Description>
 * 1.0.0            20181111-01         Rushing0711     M201811111845 新建文件
 ********************************************************************************/
package com.coding.component.cache.redis.annotation.resolver;

import com.coding.component.cache.redis.annotation.RedisKeyCapturer;
import org.springframework.core.MethodParameter;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class RedisKeyCapturerArgumentResolver implements HandlerMethodArgumentResolver {

    // 判断是否支持要转换的参数类型
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        Annotation[] annotations = parameter.getParameterAnnotations();
        return parameter.hasParameterAnnotation(RedisKeyCapturer.class);
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory)
            throws Exception {
        Annotation annotation = parameter.getParameterAnnotation(RedisKeyCapturer.class);
        RedisKeyCapturer redisKeyCapturer = (RedisKeyCapturer) annotation;

        Class clazz = parameter.getParameterType();
        if (ClassUtils.isPrimitiveOrWrapper(clazz)) {
            Object key = redisKeyCapturer.prefix().getKey();
        } else {
            String fieldName = redisKeyCapturer.value();
            if (StringUtils.hasText(fieldName)) {
                Field field =
                        ReflectionUtils.findField(clazz, StringUtils.trimWhitespace(fieldName));
                if (field != null) {
                    ReflectionUtils.makeAccessible(field);
                }
            }
        }
        return "";
    }
}

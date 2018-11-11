/*
 * 文件名称：LoginCacheArgumentResolver.java
 * 系统名称：[系统名称]
 * 模块名称：[模块名称]
 * 软件版权：Copyright (c) 2011-2018, liming20110711@163.com All Rights Reserved.
 * 功能说明：[请在此处输入功能说明]
 * 开发人员：Rushing0711
 * 创建日期：20181111 19:28
 * 修改记录：
 * <Version>        <DateSerial>        <Author>        <Description>
 * 1.0.0            20181111-01         Rushing0711     M201811111928 新建文件
 ********************************************************************************/
package com.coding.component.cache.redis.annotation.resolver;

import com.coding.component.cache.redis.annotation.LoginCache;
import com.coding.component.cache.redis.cache.RedisCache;
import com.coding.component.security.CustomUserDetails;
import com.coding.component.security.CustomWebAuthenticationDetails;
import com.coding.component.security.auth.LoginSession;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.util.NumberUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

@Component
public class LoginCacheArgumentResolver implements HandlerMethodArgumentResolver {

    private RedisCache redisCache;

    public LoginCacheArgumentResolver(RedisCache redisCache) {
        this.redisCache = redisCache;
    }

    // 判断是否支持要转换的参数类型
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasLoginCacheAnnotation = parameter.hasParameterAnnotation(LoginCache.class);
        boolean isString = String.class.isAssignableFrom(parameter.getParameterType());
        boolean isPrimitiveOrWrapper =
                ClassUtils.isPrimitiveOrWrapper(parameter.getParameterType());
        boolean isLoginSession = parameter.getParameterType().isAssignableFrom(LoginSession.class);
        return hasLoginCacheAnnotation && (isString || isPrimitiveOrWrapper || isLoginSession);
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory)
            throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            CustomWebAuthenticationDetails details =
                    (CustomWebAuthenticationDetails) authentication.getDetails();
            Long userId = userDetails.getUserId();
            LoginSession loginSession = redisCache.userSession(userId);

            Class clazz = parameter.getParameterType();
            if (clazz.isInstance(loginSession)) {
                return loginSession;
            } else {
                Annotation annotation = parameter.getParameterAnnotation(LoginCache.class);
                LoginCache loginCache = (LoginCache) annotation;
                String fieldName = loginCache.value();
                if (StringUtils.hasText(fieldName)) {
                    Field field =
                            ReflectionUtils.findField(
                                    LoginSession.class, StringUtils.trimWhitespace(fieldName));
                    if (field != null) {
                        ReflectionUtils.makeAccessible(field);
                        Object fieldValue = field.get(loginSession);
                        return getValue(fieldValue, clazz);
                    } else {
                        throw new Exception("指定的属性名不存在");
                    }
                } else {
                    throw new Exception("没有指定要提取的属性名");
                }
            }
        } else {
            throw new Exception("无法获取登录信息");
        }
    }

    private Object getValue(Object fieldValue, Class clazz) throws Exception {
        if (String.class.isAssignableFrom(clazz)) {
            return fieldValue;
        }
        if (ClassUtils.isPrimitiveWrapper(clazz) && fieldValue == null) {
            return null;
        }
        if (byte.class == clazz || Byte.class == clazz) {
            return NumberUtils.parseNumber(fieldValue.toString(), Byte.class);
        }
        if (short.class == clazz || Short.class == clazz) {
            return NumberUtils.parseNumber(fieldValue.toString(), Short.class);
        }
        if (int.class == clazz || Integer.class == clazz) {
            return NumberUtils.parseNumber(fieldValue.toString(), Integer.class);
        }
        if (long.class == clazz || Long.class == clazz) {
            return NumberUtils.parseNumber(fieldValue.toString(), Long.class);
        }
        if (float.class == clazz || Float.class == clazz) {
            return NumberUtils.parseNumber(fieldValue.toString(), Float.class);
        }
        if (double.class == clazz || Double.class == clazz) {
            return NumberUtils.parseNumber(fieldValue.toString(), Double.class);
        }
        return fieldValue;
    }
}

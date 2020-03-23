/*
 * 文件名称：WebMvcConfigurer.java
 * 系统名称：[系统名称]
 * 模块名称：[模块名称]
 * 软件版权：Copyright (c) 2011-2018, liming20110711@163.com All Rights Reserved.
 * 功能说明：[请在此处输入功能说明]
 * 开发人员：Rushing0711
 * 创建日期：20181111 18:59
 * 修改记录：
 * <Version>        <DateSerial>        <Author>        <Description>
 * 1.0.0            20181111-01         Rushing0711     M201811111859 新建文件
 ********************************************************************************/
package com.coding.component.cache.redis.annotation.resolver;

import com.coding.component.cache.redis.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

@Configuration
public class WebMvcConfigurer extends WebMvcConfigurerAdapter {

    @Autowired private RedisCache redisCache;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new LoginCacheArgumentResolver(redisCache));
//        argumentResolvers.add(new RedisKeyCapturerArgumentResolver());
        super.addArgumentResolvers(argumentResolvers);
    }
}

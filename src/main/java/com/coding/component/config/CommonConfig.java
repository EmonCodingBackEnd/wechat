/*
 * 文件名称：CommonConfig.java
 * 系统名称：[系统名称]
 * 模块名称：项目通用配置
 * 软件版权：Copyright (c) 2011-2018, liming20110711@163.com All Rights Reserved.
 * 功能说明：[请在此处输入功能说明]
 * 开发人员：Rushing0711
 * 创建日期：20180519 15:42
 * 修改记录：
 * <Version>        <DateSerial>        <Author>        <Description>
 * 1.0.0            20180519-01         Rushing0711     M201805191542 新建文件
 ********************************************************************************/
package com.coding.component.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 项目通用配置.
 *
 * <p>创建时间: <font style="color:#00FFFF">20180519 15:43</font><br>
 * [请在此输入功能详述]
 *
 * @author Rushing0711
 * @version 1.0.0
 * @since 1.0.0
 */
@Data
@Component
@ConfigurationProperties(prefix = "common")
@Slf4j
public class CommonConfig {

    private String uploadUrl;

    // 订单支付超时时间设置单位分钟
    private Integer orderTimeOut;

    // 超时时间和微信预支付有效时间的时间差 单位秒
    private Integer wxOderTimeDiff;

    private int corePoolSize;

    private int maxPoolSize;

    private int queueCapacity;

    private int keeyAliveSecond;

    private int awaitTerminationSeconds;

    @PostConstruct
    public void init() {
        log.info("【系统常量初始化】开始......");
        log.info("【系统常量初始化】结束");
    }
}

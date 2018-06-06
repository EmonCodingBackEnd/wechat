/*
 * 文件名称：BaseHttpClient.java
 * 系统名称：[系统名称]
 * 模块名称：Http基类
 * 软件版权：Copyright (c) 2011-2018, liming20110711@163.com All Rights Reserved.
 * 功能说明：[请在此处输入功能说明]
 * 开发人员：Rushing0711
 * 创建日期：20180607 07:49
 * 修改记录：
 * <Version>        <DateSerial>        <Author>        <Description>
 * 1.0.0            20180607-01         Rushing0711     M201806070749 新建文件
 ********************************************************************************/
package com.coding.wechat.utils.http;

import org.apache.commons.codec.Charsets;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.ConnectionConfig;

/**
 * Http基类.
 *
 * <p>创建时间: <font style="color:#00FFFF">20180607 07:49</font><br>
 * [请在此输入功能详述]
 *
 * @author Rushing0711
 * @version 1.0.0
 * @since 1.0.0
 */
public abstract class BaseHttpClient {

    /*
     * 路由(MAX_PER_ROUTE)是对最大连接数(MAX_TOTAL)的细分，
     * 整个连接池的限制数量实际使用DefaultMaxPerRoute并非是MaxTotal。
     * 设置过小无法支持大并发(ConnectionPoolTimeoutException: Timeout waiting for connection from pool)
     */
    /** 最大支持的连接数. */
    protected static final int DEFAULT_MAX_TOTAL = 512;
    /** 针对某一个域名的最大连接数. */
    protected static final int DEFAULT_MAX_PER_ROUTE = 200;

    protected static final int VALIDATE_AFTER_INACTIVITY = 1000;

    /** 跟目标服务建立连接超时时间，根据业务情况而定. */
    protected static final int DEFAULT_CONNECTION_TIMEOUT = 5000;
    /** 请求的超时时间(建立连接后，等待response返回的时间). */
    protected static final int DEFAULT_SOCKET_TIMEOUT = 5000;
    /** 从连接池中获取连接的超时时间. */
    protected static final int DEFAULT_TIMEOUT = 5000;

    /** 默认连接配置. */
    protected static final ConnectionConfig defaultConnectionConfig;

    /** 默认请求配置. */
    private static final RequestConfig defaultRequestConfig;

    static {
        // 默认连接配置
        defaultConnectionConfig = ConnectionConfig.custom().setCharset(Charsets.UTF_8).build();
        // 默认请求配置
        defaultRequestConfig =
                RequestConfig.custom()
                        .setConnectTimeout(DEFAULT_CONNECTION_TIMEOUT)
                        .setSocketTimeout(DEFAULT_SOCKET_TIMEOUT)
                        .setConnectionRequestTimeout(DEFAULT_TIMEOUT)
                        .build();
    }
}

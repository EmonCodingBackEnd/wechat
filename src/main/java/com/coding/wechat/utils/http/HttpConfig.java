/*
 * 文件名称：HttpConfig.java
 * 系统名称：[系统名称]
 * 模块名称：Http配置参数
 * 软件版权：Copyright (c) 2011-2018, liming20110711@163.com All Rights Reserved.
 * 功能说明：[请在此处输入功能说明]
 * 开发人员：Rushing0711
 * 创建日期：20180607 21:58
 * 修改记录：
 * <Version>        <DateSerial>        <Author>        <Description>
 * 1.0.0            20180607-01         Rushing0711     M201806072158 新建文件
 ********************************************************************************/
package com.coding.wechat.utils.http;

import org.apache.commons.codec.Charsets;
import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.apache.http.ssl.SSLContexts;

import javax.net.ssl.SSLContext;

/**
 * Http配置参数.
 *
 * <p>创建时间: <font style="color:#00FFFF">20180607 21:58</font><br>
 * [请在此输入功能详述]
 *
 * @author Rushing0711
 * @version 1.0.0
 * @since 1.0.0
 */
public class HttpConfig {

    public static final String userAgent = "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)";

    /*
     * 路由(MAX_PER_ROUTE)是对最大连接数(MAX_TOTAL)的细分，
     * 整个连接池的限制数量实际使用DefaultMaxPerRoute并非是MaxTotal。
     * 设置过小无法支持大并发(ConnectionPoolTimeoutException: Timeout waiting for connection from pool)
     */
    /** 最大支持的连接数. */
    public static final int DEFAULT_MAX_TOTAL = 600;
    /** 针对某一个域名的最大连接数. */
    public static final int DEFAULT_MAX_PER_ROUTE = 200;

    public static final int DEFAULT_VALIDATE_AFTER_INACTIVITY = 1000;

    /** 跟目标服务建立连接超时时间，根据业务情况而定. */
    public static final int DEFAULT_CONNECTION_TIMEOUT = 5000;
    /** 请求的超时时间(建立连接后，等待response返回的时间). */
    public static final int DEFAULT_SOCKET_TIMEOUT = 5000;
    /** 从连接池中获取连接的超时时间. */
    public static final int DEFAULT_TIMEOUT = 5000;

    /** 默认连接配置. */
    public static final ConnectionConfig defaultConnectionConfig;

    /** 默认链接保持策略. */
    public static final ConnectionKeepAliveStrategy defaultConnectionStrategy;

    /** 默认重试处理器. */
    public static final DefaultHttpRequestRetryHandler defaultHttpRequestRetryHandler =
            new DefaultHttpRequestRetryHandler();

    static {
        // 默认连接配置
        defaultConnectionConfig = ConnectionConfig.custom().setCharset(Charsets.UTF_8).build();

        // 连接保持策略
        defaultConnectionStrategy =
                (response, context) -> {
                    HeaderElementIterator it =
                            new BasicHeaderElementIterator(
                                    response.headerIterator(HTTP.CONN_KEEP_ALIVE));
                    while (it.hasNext()) {
                        HeaderElement he = it.nextElement();
                        String param = he.getName();
                        String value = he.getValue();
                        if (value != null && param.equalsIgnoreCase("timeout")) {
                            return Long.parseLong(value) * 1000;
                        }
                    }
                    return 60 * 1000; // 如果没有约定，则默认定义时长为60s
                };
    }
}

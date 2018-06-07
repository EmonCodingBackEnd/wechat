/*
 * 文件名称：HttpAyncRetryClient.java
 * 系统名称：[系统名称]
 * 模块名称：Http同步重试
 * 软件版权：Copyright (c) 2011-2018, liming20110711@163.com All Rights Reserved.
 * 功能说明：[请在此处输入功能说明]
 * 开发人员：Rushing0711
 * 创建日期：20180607 07:50
 * 修改记录：
 * <Version>        <DateSerial>        <Author>        <Description>
 * 1.0.0            20180607-01         Rushing0711     M201806070750 新建文件
 ********************************************************************************/
package com.coding.wechat.utils.http.client;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

/**
 * Http同步重试.
 *
 * <p>创建时间: <font style="color:#00FFFF">20180607 07:50</font><br>
 * [请在此输入功能详述]
 *
 * @author Rushing0711
 * @version 1.0.0
 * @since 1.0.0
 */
public abstract class HttpSyncRetryClient extends BaseHttpClient {

    private static final CloseableHttpClient httpSyncRetryClient;

    static {
        // 设置连接池
        PoolingHttpClientConnectionManager poolingConnMgr =
                new PoolingHttpClientConnectionManager(defaultSocketFactoryRegistry);
        poolingConnMgr.setMaxTotal(DEFAULT_MAX_TOTAL);
        poolingConnMgr.setDefaultMaxPerRoute(DEFAULT_MAX_PER_ROUTE);
        poolingConnMgr.setValidateAfterInactivity(VALIDATE_AFTER_INACTIVITY);
        poolingConnMgr.setDefaultConnectionConfig(defaultConnectionConfig);

        // 设置重试处理器
        DefaultHttpRequestRetryHandler retryHandler = new DefaultHttpRequestRetryHandler();

        httpSyncRetryClient =
                HttpClients.custom()
                        .setConnectionManager(poolingConnMgr)
                        .setDefaultRequestConfig(defaultRequestConfig)
                        .setKeepAliveStrategy(defaultConnectionStrategy)
                        .setUserAgent(userAgent)
                        .setRetryHandler(retryHandler)
                        .build();
    }
}

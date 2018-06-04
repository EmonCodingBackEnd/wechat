/*
 * 文件名称：HttpClientUtils.java
 * 系统名称：[系统名称]
 * 模块名称：基于HttpClient的Http工具类
 * 软件版权：Copyright (c) 2011-2018, liming20110711@163.com All Rights Reserved.
 * 功能说明：[请在此处输入功能说明]
 * 开发人员：Rushing0711
 * 创建日期：20180604 19:39
 * 修改记录：
 * <Version>        <DateSerial>        <Author>        <Description>
 * 1.0.0            20180604-01         Rushing0711     M201806041939 新建文件
 ********************************************************************************/
package com.coding.wechat.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import java.util.Map;

/**
 * 基于HttpClient的Http工具类.
 *
 * <p>创建时间: <font style="color:#00FFFF">20180604 19:39</font><br>
 * [请在此输入功能详述]
 *
 * @author Rushing0711
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
public abstract class HttpClientUtils {
    private static PoolingHttpClientConnectionManager connManager;
    private static final RequestConfig defaultRequestConfig;
    private static final CloseableHttpClient httpClient;
    private static final CookieStore cookieStore;
    private static final int MAX_CONNECT_TIMEOUT = 6000;
    private static final int MAX_SOCKET_TIMEOUT = 15000;
    private static final int MAX_CONNECT_REQUEST_TIMEOUT = 8000;

    static {
        // 设置连接池
        connManager = new PoolingHttpClientConnectionManager();
        // 设置连接池大小
        connManager.setMaxTotal(100);
        connManager.setDefaultMaxPerRoute(connManager.getMaxTotal());
        connManager.setValidateAfterInactivity(1000);

        defaultRequestConfig =
                RequestConfig.custom()
                        // 设置连接超时
                        .setConnectTimeout(MAX_CONNECT_TIMEOUT)
                        // 设置读取超时
                        .setSocketTimeout(MAX_SOCKET_TIMEOUT)
                        // 设置从连接池获取连接实例的超时
                        .setConnectionRequestTimeout(MAX_CONNECT_REQUEST_TIMEOUT)
                        .build();

        // Use custom cookie store if necessary.
        cookieStore = new BasicCookieStore();

        // Create an HttpClient with the given custom dependencies and configuration.
        httpClient =
                HttpClients.custom()
                        .setConnectionManager(connManager)
                        .setDefaultCookieStore(cookieStore)
                        .setDefaultRequestConfig(defaultRequestConfig)
                        .build();
    }

    public static String doGet(String url, Map<String, Object> params) {
        String apiUrl = url;
        StringBuffer param = new StringBuffer();
        int i = 0;
        for (String key : params.keySet()) {
            if (i == 0) {
                param.append("?");
            } else {
                param.append("&");
            }
            param.append(key).append("=").append(params.get(key));
            i++;
        }
        apiUrl += param;
        String result = null;

        HttpGet httpGet = new HttpGet("http://httpbin.org/get");

        // Request configuration can be overridden at the request level.
        // They will take precedence over the one set at the client level.
        RequestConfig requestConfig =
                RequestConfig.copy(defaultRequestConfig)
                        .setSocketTimeout(5000)
                        .setConnectTimeout(5000)
                        .setConnectionRequestTimeout(5000)
                        .setProxy(new HttpHost("myotherproxy", 8080))
                        .build();
        httpGet.setConfig(requestConfig);

        // Execution context can be customized locally.
        final HttpClientContext context = HttpClientContext.create();
        // Contextual attributes set the local context level will take
        // precedence over those set at the client level.
        context.setCookieStore(cookieStore);

        log.info("【HTTP请求】uri={}", httpGet.getURI());
        //        CloseableHttpResponse response = httpClient.execute(httpGet, context);

        return null;
    }
}

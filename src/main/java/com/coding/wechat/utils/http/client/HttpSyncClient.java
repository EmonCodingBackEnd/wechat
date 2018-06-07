/*
 * 文件名称：HttpSyncClient.java
 * 系统名称：[系统名称]
 * 模块名称：Http同步
 * 软件版权：Copyright (c) 2011-2018, liming20110711@163.com All Rights Reserved.
 * 功能说明：[请在此处输入功能说明]
 * 开发人员：Rushing0711
 * 创建日期：20180607 07:48
 * 修改记录：
 * <Version>        <DateSerial>        <Author>        <Description>
 * 1.0.0            20180607-01         Rushing0711     M201806070748 新建文件
 ********************************************************************************/
package com.coding.wechat.utils.http.client;

import com.coding.wechat.utils.http.support.HttpMethod;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.AbstractResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * Http同步.
 *
 * <p>创建时间: <font style="color:#00FFFF">20180607 07:48</font><br>
 * [请在此输入功能详述]
 *
 * @author Rushing0711
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
public abstract class HttpSyncClient extends BaseHttpClient {

    private static final CloseableHttpClient httpSyncClient;

    static {
        // 设置连接池
        PoolingHttpClientConnectionManager poolingConnMgr =
                new PoolingHttpClientConnectionManager(defaultSocketFactoryRegistry);
        poolingConnMgr.setMaxTotal(DEFAULT_MAX_TOTAL);
        poolingConnMgr.setDefaultMaxPerRoute(DEFAULT_MAX_PER_ROUTE);
        poolingConnMgr.setValidateAfterInactivity(VALIDATE_AFTER_INACTIVITY);
        poolingConnMgr.setDefaultConnectionConfig(defaultConnectionConfig);

        httpSyncClient =
                HttpClients.custom()
                        .setConnectionManager(poolingConnMgr)
                        .setDefaultRequestConfig(defaultRequestConfig)
                        .setKeepAliveStrategy(defaultConnectionStrategy)
                        .setUserAgent(userAgent)
                        .build();
    }

    public static CloseableHttpClient getInstance() {
        return httpSyncClient;
    }

    public static String send(
            HttpClient client,
            HttpMethod httpMethod,
            String url,
            String paramString,
            Header[] headers,
            Charset charset)
            throws IOException {
        return send(client, httpMethod, url, null, paramString, headers, charset);
    }

    public static String send(
            HttpClient client,
            HttpMethod httpMethod,
            String url,
            Map<String, String> paramMap,
            Header[] headers,
            Charset charset)
            throws IOException {
        return send(client, httpMethod, url, paramMap, null, headers, charset);
    }

    private static String send(
            HttpClient client,
            HttpMethod httpMethod,
            String url,
            Map<String, String> paramMap,
            String paramString,
            Header[] headers,
            Charset charset)
            throws IOException {
        return send(
                client,
                httpMethod,
                url,
                paramMap,
                paramString,
                headers,
                charset,
                new AbstractResponseHandler<String>() {
                    @Override
                    public String handleEntity(HttpEntity entity) throws IOException {
                        return EntityUtils.toString(entity, charset);
                    }
                });
    }

    public static void send(
            HttpClient client,
            HttpMethod httpMethod,
            String url,
            Map<String, String> paramMap,
            String paramString,
            Header[] headers,
            Charset charset,
            OutputStream outputStream) throws IOException {
        send(
                client,
                httpMethod,
                url,
                paramMap,
                paramString,
                headers,
                charset,
                new AbstractResponseHandler<String>() {
                    @Override
                    public String handleEntity(HttpEntity entity) throws IOException {
                        entity.writeTo(outputStream);
                        return null;
                    }
                });
    }


}

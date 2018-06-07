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
package com.coding.wechat.utils.http.client;

import com.coding.wechat.utils.http.support.HttpMethod;
import com.coding.wechat.utils.http.support.HttpSupport;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.Charsets;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.apache.http.ssl.SSLContexts;
import org.springframework.util.Assert;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
@Slf4j
public abstract class BaseHttpClient {

    protected static final String userAgent =
            "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)";

    /*
     * 路由(MAX_PER_ROUTE)是对最大连接数(MAX_TOTAL)的细分，
     * 整个连接池的限制数量实际使用DefaultMaxPerRoute并非是MaxTotal。
     * 设置过小无法支持大并发(ConnectionPoolTimeoutException: Timeout waiting for connection from pool)
     */
    /** 最大支持的连接数. */
    protected static final int DEFAULT_MAX_TOTAL = 600;
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
    protected static final RequestConfig defaultRequestConfig;

    /** 默认Socket注册器，支持http和https. */
    protected static final Registry<ConnectionSocketFactory> defaultSocketFactoryRegistry;

    /** 默认链接保持策略. */
    protected static final ConnectionKeepAliveStrategy defaultConnectionStrategy;

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

        // 支持http和https
        SSLContext sslContext = SSLContexts.createSystemDefault();
        defaultSocketFactoryRegistry =
                RegistryBuilder.<ConnectionSocketFactory>create()
                        .register("https", new SSLConnectionSocketFactory(sslContext))
                        .register("http", PlainConnectionSocketFactory.INSTANCE)
                        .build();

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

    protected static <T> T send(
            HttpClient client,
            HttpMethod httpMethod,
            String url,
            Map<String, String> paramMap,
            String paramString,
            Header[] headers,
            Charset charset,
            ResponseHandler<T> responseHandler)
            throws IOException {
        Assert.notNull(url, "url must be not null");

        HttpRequestBase httpRequest = HttpMethod.getHttpRequest(url, httpMethod);
        httpRequest.setHeaders(headers);

        String urlHost;
        String urlParam;
        // 实现了接口HttpEntityEnclosingRequest的类，可以支持设置Entity
        if (HttpEntityEnclosingRequest.class.isAssignableFrom(httpRequest.getClass())) {
            if (paramMap != null) {

                List<NameValuePair> pairList = new ArrayList<>();

                // 检查url中是否存在参数
                url = HttpSupport.checkHasParams(url, pairList);

                // 追加参数
                List<NameValuePair> paramList = HttpSupport.convertToPairList(paramMap);
                pairList.addAll(paramList);

                // 设置参数
                ((HttpEntityEnclosingRequestBase) httpRequest)
                        .setEntity(new UrlEncodedFormEntity(pairList, charset));
                urlHost = url;
                urlParam = paramList.toString();
            } else {
                Assert.notNull(paramString, "paramString must be not null");
                // 设置参数
                ((HttpEntityEnclosingRequestBase) httpRequest)
                        .setEntity(new StringEntity(paramString, charset));
                urlHost = url;
                urlParam = paramString;
            }
        } else {
            int idx = url.indexOf("?");
            urlHost = url.substring(0, (idx > 0 ? idx - 1 : url.length() - 1));
            urlParam = url.substring(idx + 1);
        }
        if (log.isDebugEnabled()) {
            log.info("【Http】请求url={},params={}", urlHost, urlParam);
        } else {
            log.info("【Http】请求url={}", urlHost);
        }

        return execute(client, httpRequest, responseHandler);
    }

    private static <T> T execute(
            HttpClient client, HttpRequestBase httpRequest, ResponseHandler<T> responseHandler)
            throws IOException {
        T result = client.execute(httpRequest, responseHandler);
        log.debug("【Http】应答result={}", result.toString());
        return result;
    }


}

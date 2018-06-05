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
package com.coding.wechat.utils.http;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.Charsets;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpEntity;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.AbstractResponseHandler;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.nio.reactor.IOReactorException;
import org.apache.http.protocol.HTTP;
import org.apache.http.ssl.SSLContexts;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    /*
     * 路由(MAX_PER_ROUTE)是对最大连接数(MAX_TOTAL)的细分，
     * 整个连接池的限制数量实际使用DefaultMaxPerRoute并非是MaxTotal。
     * 设置过小无法支持大并发(ConnectionPoolTimeoutException: Timeout waiting for connection from pool)
     */
    /** 最大支持的连接数. */
    private static final int DEFAULT_MAX_TOTAL = 512;
    /** 针对某一个域名的最大连接数. */
    private static final int DEFAULT_MAX_PER_ROUTE = 100;

    private static final int VALIDATE_AFTER_INACTIVITY = 1000;

    /** 跟目标服务建立连接超时时间，根据业务情况而定. */
    private static final int DEFAULT_CONNECTION_TIMEOUT = 5000;
    /** 请求的超时时间(建立连接后，等待response返回的时间). */
    private static final int DEFAULT_SOCKET_TIMEOUT = 3000;
    /** 从连接池中获取连接的超时时间. */
    private static final int DEFAULT_TIMEOUT = 2000;

    private static final ConnectionConfig defaultConnectionConfig; // 默认连接配置
    private static final RequestConfig defaultRequestConfig; // 默认请求配置
    private static final CloseableHttpClient httpSyncClient; // 同步
    private static final CloseableHttpAsyncClient asyncHttpClient; // 异步

    static {

        // 默认连接配置
        defaultConnectionConfig = ConnectionConfig.custom().setCharset(Charsets.UTF_8).build();

        // 支持http和https
        SSLContext sslContext = SSLContexts.createSystemDefault();
        Registry<ConnectionSocketFactory> socketFactoryRegistry =
                RegistryBuilder.<ConnectionSocketFactory>create()
                        .register("https", new SSLConnectionSocketFactory(sslContext))
                        .register("http", PlainConnectionSocketFactory.INSTANCE)
                        .build();

        // 设置连接池
        PoolingHttpClientConnectionManager poolingConnMgr =
                new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        poolingConnMgr.setMaxTotal(DEFAULT_MAX_TOTAL);
        poolingConnMgr.setDefaultMaxPerRoute(DEFAULT_MAX_PER_ROUTE);
        poolingConnMgr.setValidateAfterInactivity(VALIDATE_AFTER_INACTIVITY);
        poolingConnMgr.setDefaultConnectionConfig(defaultConnectionConfig);

        // 默认请求配置
        defaultRequestConfig =
                RequestConfig.custom()
                        .setConnectTimeout(DEFAULT_CONNECTION_TIMEOUT)
                        .setSocketTimeout(DEFAULT_SOCKET_TIMEOUT)
                        .setConnectionRequestTimeout(DEFAULT_TIMEOUT)
                        .build();

        // 连接保持策略
        ConnectionKeepAliveStrategy defaultConnectionStrategy =
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


        httpSyncClient =
                HttpClients.custom()
                        .setConnectionManager(poolingConnMgr)
                        .setDefaultRequestConfig(defaultRequestConfig)
                        .setKeepAliveStrategy(defaultConnectionStrategy)
                        .build();

        // ===============================================================

        DefaultConnectingIOReactor ioReactor = null;
        try {
            ioReactor =
                    new DefaultConnectingIOReactor(
                            IOReactorConfig.custom().setSoKeepAlive(true).build());
        } catch (IOReactorException e) {
            log.error("【Http异步】初始化失败", e);
        }

        PoolingNHttpClientConnectionManager poolingNConnMgr =
                new PoolingNHttpClientConnectionManager(ioReactor);
        poolingNConnMgr.setMaxTotal(DEFAULT_MAX_TOTAL);
        poolingNConnMgr.setDefaultMaxPerRoute(DEFAULT_MAX_PER_ROUTE);
        poolingNConnMgr.setDefaultConnectionConfig(defaultConnectionConfig);

        asyncHttpClient =
                HttpAsyncClients.custom()
                        .setThreadFactory(
                                new BasicThreadFactory.Builder()
                                        .namingPattern("AysncHttpThread-%d")
                                        .build())
                        .setConnectionManager(poolingNConnMgr)
                        .setDefaultRequestConfig(defaultRequestConfig)
                        .build();
    }

    public static String doGet(String apiUrl) throws IOException {
        return doGetEncoded(apiUrl, null, null, new BasicResponseHandler());
    }

    public static String doGet(String apiUrl, Map<String, Object> params) throws IOException {
        return doGetEncoded(apiUrl, params, null, new BasicResponseHandler());
    }

    public static String doGetTimeout(String apiUrl, int timeout) throws IOException {
        return doGetEncoded(apiUrl, null, timeout, new BasicResponseHandler());
    }

    public static String doGetTimeout(String apiUrl, Map<String, Object> params, int timeout)
            throws IOException {
        return doGetEncoded(apiUrl, params, timeout, new BasicResponseHandler());
    }

    public static void doGet(String apiUrl, OutputStream out) throws IOException {
        doGetEncoded(
                apiUrl,
                null,
                null,
                new AbstractResponseHandler() {
                    @Override
                    public Object handleEntity(HttpEntity entity) throws IOException {
                        entity.writeTo(out);
                        return null;
                    }
                });
    }

    public static void doGet(String apiUrl, Map<String, Object> params, OutputStream out)
            throws IOException {
        doGetEncoded(
                apiUrl,
                params,
                null,
                new AbstractResponseHandler() {
                    @Override
                    public Object handleEntity(HttpEntity entity) throws IOException {
                        entity.writeTo(out);
                        return null;
                    }
                });
    }

    private static <T> T doGetEncoded(
            String apiUrl,
            Map<String, Object> params,
            Integer timeout,
            ResponseHandler<? extends T> responseHandler)
            throws IOException {
        return doGet(apiUrl, params, false, timeout, responseHandler);
    }

    private static <T> T doGet(
            String apiUrl,
            Map<String, Object> params,
            boolean needIRetry,
            Integer timeout,
            ResponseHandler<? extends T> responseHandler)
            throws IOException {
        if (params == null) {
            params = new HashMap<>();
        }
        CloseableHttpClient closeableHttpClient;
        if (needIRetry) {
            // TODO: 2018/6/6 重试请求，暂时不支持
            closeableHttpClient = null;
        } else {
            closeableHttpClient = httpSyncClient;
        }
        return doGet(apiUrl, params, closeableHttpClient, timeout, responseHandler);
    }

    private static <T> T doGet(
            String apiUrl,
            Map<String, Object> params,
            CloseableHttpClient closeableHttpClient,
            Integer timeout,
            ResponseHandler<? extends T> responseHandler)
            throws IOException {
        if (StringUtils.isEmpty(apiUrl)) {
            throw new IllegalArgumentException("request apiUrl must be not null or empty string");
        }

        List<BasicNameValuePair> pairList = new ArrayList<>();
        if (params != null) {
            for (String key : params.keySet()) {
                pairList.add(new BasicNameValuePair(key, params.get(key).toString()));
            }
            String param = URLEncodedUtils.format(pairList, Charsets.UTF_8);
            if (!StringUtils.isEmpty(param)) {
                if (apiUrl.contains("?")) {
                    apiUrl = apiUrl + "&" + param;
                } else {
                    apiUrl = apiUrl + "?" + param;
                }
            }
        }

        HttpGet httpGet = new HttpGet(apiUrl);
        if (timeout != null) {
            RequestConfig requestConfig =
                    RequestConfig.copy(defaultRequestConfig).setSocketTimeout(timeout).build();
            httpGet.setConfig(requestConfig);
        }
        httpGet.setHeader("accept", "*/*");
        httpGet.setHeader("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");

        T result;
        try {
            log.info("【HttpClient】请求 method=GET,uri={}", httpGet.getURI());
            // 创建响应处理器处理服务器响应内容
            result = closeableHttpClient.execute(httpGet, responseHandler);
            log.info("【HttpClient】应答 method=GET,uri={}", httpGet.getURI());
        } catch (IOException e) {
            log.error("【HttpClient】异常", e);
            throw e;
        } finally {
        }
        return result;
    }

    public static String doPost(String apiUrl) throws IOException {
        return doPostEncoded(apiUrl, null, null, new BasicResponseHandler());
    }

    public static String doPost(String apiUrl, Map<String, Object> params) throws IOException {
        return doPostEncoded(apiUrl, params, null, new BasicResponseHandler());
    }

    public static String doPostTimeout(String apiUrl, int timeout) throws IOException {
        return doPostEncoded(apiUrl, null, timeout, new BasicResponseHandler());
    }

    public static String doPostTimeout(String apiUrl, Map<String, Object> params, int timeout)
            throws IOException {
        return doPostEncoded(apiUrl, params, timeout, new BasicResponseHandler());
    }

    public static String doPostJson(String apiUrl) throws IOException {
        return doPostJson(apiUrl, null, null, new BasicResponseHandler());
    }

    public static String doPostJson(String apiUrl, String paramJson) throws IOException {
        return doPostJson(apiUrl, paramJson, null, new BasicResponseHandler());
    }

    public static String doPostJsonTimeout(String apiUrl, int timeout) throws IOException {
        return doPostJson(apiUrl, null, timeout, new BasicResponseHandler());
    }

    public static String doPostJsonTimeout(String apiUrl, String paramJson, int timeout)
            throws IOException {
        return doPostJson(apiUrl, paramJson, timeout, new BasicResponseHandler());
    }

    public static String doPostXml(String apiUrl) throws IOException {
        return doPostXml(apiUrl, null, null, new BasicResponseHandler());
    }

    public static String doPostXml(String apiUrl, String paramJson) throws IOException {
        return doPostXml(apiUrl, paramJson, null, new BasicResponseHandler());
    }

    public static String doPostXmlTimeout(String apiUrl, int timeout) throws IOException {
        return doPostXml(apiUrl, null, timeout, new BasicResponseHandler());
    }

    public static String doPostXmlTimeout(String apiUrl, String paramJson, int timeout)
            throws IOException {
        return doPostXml(apiUrl, paramJson, timeout, new BasicResponseHandler());
    }

    public static void doPost(String apiUrl, OutputStream out) throws IOException {
        doPostEncoded(
                apiUrl,
                null,
                null,
                new AbstractResponseHandler() {
                    @Override
                    public Object handleEntity(HttpEntity entity) throws IOException {
                        entity.writeTo(out);
                        return null;
                    }
                });
    }

    public static void doPost(String apiUrl, Map<String, Object> params, OutputStream out)
            throws IOException {
        doPostEncoded(
                apiUrl,
                params,
                null,
                new AbstractResponseHandler() {
                    @Override
                    public Object handleEntity(HttpEntity entity) throws IOException {
                        entity.writeTo(out);
                        return null;
                    }
                });
    }

    public static void doPostJson(String apiUrl, OutputStream out) throws IOException {
        doPostJson(
                apiUrl,
                null,
                null,
                new AbstractResponseHandler() {
                    @Override
                    public Object handleEntity(HttpEntity entity) throws IOException {
                        entity.writeTo(out);
                        return null;
                    }
                });
    }

    public static void doPostJson(String apiUrl, String params, OutputStream out)
            throws IOException {
        doPostJson(
                apiUrl,
                params,
                null,
                new AbstractResponseHandler() {
                    @Override
                    public Object handleEntity(HttpEntity entity) throws IOException {
                        entity.writeTo(out);
                        return null;
                    }
                });
    }

    public static void doPostXml(String apiUrl, OutputStream out) throws IOException {
        doPostXml(
                apiUrl,
                null,
                null,
                new AbstractResponseHandler() {
                    @Override
                    public Object handleEntity(HttpEntity entity) throws IOException {
                        entity.writeTo(out);
                        return null;
                    }
                });
    }

    public static void doPostXml(String apiUrl, String params, OutputStream out)
            throws IOException {
        doPostXml(
                apiUrl,
                params,
                null,
                new AbstractResponseHandler() {
                    @Override
                    public Object handleEntity(HttpEntity entity) throws IOException {
                        entity.writeTo(out);
                        return null;
                    }
                });
    }

    private static <T> T doPostEncoded(
            String apiUrl,
            Map<String, Object> params,
            Integer timeout,
            ResponseHandler<? extends T> responseHandler)
            throws IOException {
        return doPost(apiUrl, params, false, timeout, responseHandler, ContentType.ENCODED, null);
    }

    private static <T> T doPostJson(
            String apiUrl,
            String paramStr,
            Integer timeout,
            ResponseHandler<? extends T> responseHandler)
            throws IOException {
        return doPost(apiUrl, null, false, timeout, responseHandler, ContentType.JSON, paramStr);
    }

    private static <T> T doPostXml(
            String apiUrl,
            String paramStr,
            Integer timeout,
            ResponseHandler<? extends T> responseHandler)
            throws IOException {
        return doPost(apiUrl, null, false, timeout, responseHandler, ContentType.XML, paramStr);
    }

    private static <T> T doPost(
            String apiUrl,
            Map<String, Object> params,
            boolean needIRetry,
            Integer timeout,
            ResponseHandler<? extends T> responseHandler,
            ContentType contentType,
            String paramStr)
            throws IOException {
        if (params == null) {
            params = new HashMap<>();
        }
        CloseableHttpClient closeableHttpClient;
        if (needIRetry) {
            // TODO: 2018/6/6 重试请求，暂时不支持
            closeableHttpClient = null;
        } else {
            closeableHttpClient = httpSyncClient;
        }
        if (contentType == null) {
            contentType = ContentType.ENCODED;
        }
        return doPost(
                apiUrl,
                params,
                closeableHttpClient,
                timeout,
                responseHandler,
                contentType,
                paramStr);
    }

    private static <T> T doPost(
            String apiUrl,
            Map<String, Object> params,
            CloseableHttpClient closeableHttpClient,
            Integer timeout,
            ResponseHandler<? extends T> responseHandler,
            ContentType contentType,
            String paramStr)
            throws IOException {
        if (StringUtils.isEmpty(apiUrl)) {
            throw new IllegalArgumentException("request apiUrl must be not null or empty string");
        }

        HttpPost httpPost = new HttpPost(apiUrl);
        if (ContentType.ENCODED.equals(contentType)) {
            // 组装请求数据
            List<BasicNameValuePair> pairList = new ArrayList<>();
            if (params != null) {
                for (String key : params.keySet()) {
                    pairList.add(new BasicNameValuePair(key, params.get(key).toString()));
                }
            }
            httpPost.setEntity(new UrlEncodedFormEntity(pairList, Charsets.UTF_8));
        }
        if (ContentType.XML.equals(contentType)) {
            if (!StringUtils.isEmpty(paramStr)) {
                // 构建消息实体
                StringEntity entity = new StringEntity(paramStr, Charsets.UTF_8);
                entity.setContentEncoding(Charsets.UTF_8.name());
                entity.setContentType(MediaType.APPLICATION_XML_VALUE);
                httpPost.setEntity(entity);
            }
        }
        if (ContentType.JSON.equals(contentType)) {
            if (!StringUtils.isEmpty(paramStr)) {
                // 构建消息实体
                StringEntity entity = new StringEntity(paramStr, Charsets.UTF_8);
                entity.setContentEncoding(Charsets.UTF_8.name());
                entity.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
                httpPost.setEntity(entity);
            }
        }

        // 个性化设置超时时间
        if (timeout != null) {
            RequestConfig requestConfig =
                    RequestConfig.copy(defaultRequestConfig).setSocketTimeout(timeout).build();
            httpPost.setConfig(requestConfig);
        }
        httpPost.setHeader("accept", "*/*");
        httpPost.setHeader("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");

        T result = null;
        try {
            log.info("【HttpClient】请求 method=POST,uri={}", httpPost.getURI());
            result = closeableHttpClient.execute(httpPost, responseHandler);
            log.info("【HttpClient】应答 method=POST,uri={}", httpPost.getURI());
        } catch (IOException e) {
            log.error("【HttpClient】method=POST,异常", e);
            throw e;
        }
        return result;
    }

    private enum ContentType {
        XML,
        JSON,
        ENCODED;
    }
}

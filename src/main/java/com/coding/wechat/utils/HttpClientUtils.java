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

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
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
    private static final String DEFAULT_CHARSET = "UTF-8";
    private static PoolingHttpClientConnectionManager connManager;
    private static final RequestConfig defaultRequestConfig;
    private static final int MAX_CONNECT_TIMEOUT = 2000;
    private static final int MAX_SOCKET_TIMEOUT = 5000;
    private static final int MAX_CONNECT_REQUEST_TIMEOUT = 3000;

    static {
        SSLContext sslContext = SSLContexts.createSystemDefault();
        Registry<ConnectionSocketFactory> socketFactoryRegistry =
                RegistryBuilder.<ConnectionSocketFactory>create()
                        .register("https", new SSLConnectionSocketFactory(sslContext))
                        .register("http", PlainConnectionSocketFactory.INSTANCE)
                        .build();

        // 设置连接池
        connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        // 设置连接池大小
        connManager.setMaxTotal(500);
        // 每个路由最大并发数
        connManager.setDefaultMaxPerRoute(50);
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
    }

    public static CloseableHttpClient getHttpClient() {
        // Create an HttpClient with the given custom dependencies and configuration.
        CloseableHttpClient httpClient =
                HttpClients.custom()
                        .setConnectionManager(connManager)
                        .setDefaultRequestConfig(defaultRequestConfig)
                        .build();
        return httpClient;
    }

    public static CloseableHttpClient getHttpClient(int timeout) {
        RequestConfig requestConfig =
                RequestConfig.copy(defaultRequestConfig).setSocketTimeout(timeout).build();

        // Create an HttpClient with the given custom dependencies and configuration.
        CloseableHttpClient httpClient =
                HttpClients.custom()
                        .setConnectionManager(connManager)
                        .setDefaultRequestConfig(requestConfig)
                        .build();
        return httpClient;
    }

    public static String doGet(String url, Map<String, Object> params) {
        List<BasicNameValuePair> pairList = new ArrayList<>();
        if (params != null) {
            for (String key : params.keySet()) {
                pairList.add(new BasicNameValuePair(key, params.get(key).toString()));
            }
            String param = URLEncodedUtils.format(pairList, DEFAULT_CHARSET);
            url = url + "?" + param;
        }

        HttpGet httpGet = new HttpGet(url);

        String result = null;
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        try {
            CloseableHttpClient httpClient = HttpClientUtils.getHttpClient(100000);
            log.info("【HttpClient】请求 method=GET,uri={}", httpGet.getURI());
            response = httpClient.execute(httpGet);

            int statusCode = response.getStatusLine().getStatusCode();
            log.info(
                    "【HttpClient】应答 method=GET,uri={},statusCode={}", httpGet.getURI(), statusCode);
            if (HttpStatus.SC_OK == statusCode) {
                entity = response.getEntity();
                result = EntityUtils.toString(entity, DEFAULT_CHARSET);
            }
        } catch (IOException e) {
            log.error("【HttpClient】异常", e);
        } finally {
            if (entity != null) {
                try {
                    EntityUtils.consume(entity);
                } catch (IOException e) {
                }
            }
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                }
            }
        }
        return result;
    }

    public static String doPost(String apiUrl, Map<String, Object> params, int timeout) {
        HttpPost httpPost = new HttpPost(apiUrl);
        List<BasicNameValuePair> pairList = new ArrayList<>();
        if (params != null) {
            for (String key : params.keySet()) {
                pairList.add(new BasicNameValuePair(key, params.get(key).toString()));
            }
        }
        httpPost.setEntity(new UrlEncodedFormEntity(pairList, Charset.forName(DEFAULT_CHARSET)));

        String result = null;
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        try {
            CloseableHttpClient httpClient = HttpClientUtils.getHttpClient(timeout);
            log.info("【HttpClient】请求 method=POST,uri={}", httpPost.getURI());
            response = httpClient.execute(httpPost);

            int statusCode = response.getStatusLine().getStatusCode();
            log.info(
                    "【HttpClient】应答 method=POST,uri={},statusCode={}",
                    httpPost.getURI(),
                    statusCode);
            if (HttpStatus.SC_OK == statusCode) {
                entity = response.getEntity();
                result = EntityUtils.toString(entity, DEFAULT_CHARSET);
            }
        } catch (IOException e) {
            log.error("【HttpClient】method=POST,异常", e);
        } finally {
            if (entity != null) {
                try {
                    EntityUtils.consume(entity);
                } catch (IOException e) {
                }
            }
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                }
            }
        }
        return result;
    }

    public static void main(String[] args) throws IOException {
        String url = "http://localhost:8080/wechat/website/introduction";
        Map<String, Object> params = Maps.newHashMap();
        params.put("name", "LiMing");
        params.put("age", 25);
        String result = doGet(url, params);
        log.info(result);
    }
}

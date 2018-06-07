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

import com.coding.wechat.utils.http.HttpException;
import com.coding.wechat.utils.http.generator.HttpSyncClientGenerator;
import com.coding.wechat.utils.http.support.HttpMethod;
import com.coding.wechat.utils.http.support.HttpSupport;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.AbstractResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.springframework.util.Assert;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
public abstract class HttpSyncClient {

    private static final CloseableHttpClient httpSyncClient;

    private static final CloseableHttpClient httpSyncRetryClient;

    static {
        httpSyncClient = HttpSyncClientGenerator.custom().pool().timeout().build();
        httpSyncRetryClient = HttpSyncClientGenerator.custom().pool().timeout().retry().build();
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
            OutputStream outputStream)
            throws IOException {
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

    protected static <T> T send(
            HttpClient client,
            HttpMethod httpMethod,
            String url,
            Object params,
            Header[] headers,
            Charset charset,
            ResponseHandler<T> responseHandler)
            throws IOException, HttpException {
        Assert.notNull(url, "url must be not null");
        Assert.notNull(params, "params must be not null");

        HttpRequestBase httpRequest = HttpMethod.getHttpRequest(url, httpMethod);
        httpRequest.setHeaders(headers);

        String urlHost;
        String urlParam;
        // 实现了接口HttpEntityEnclosingRequest的类，可以支持设置Entity
        if (HttpEntityEnclosingRequest.class.isAssignableFrom(httpRequest.getClass())) {
            if (Map.class.isInstance(params)) {
                Map<String, String> paramMap = (HashMap<String, String>) params;

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
            } else if (String.class.isInstance(params)) {
                String paramString = (String) params;
                // 设置参数
                ((HttpEntityEnclosingRequestBase) httpRequest)
                        .setEntity(new StringEntity(paramString, charset));
                urlHost = url;
                urlParam = paramString;
            } else {
                throw new HttpException("【Http】无效的请求参数");
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

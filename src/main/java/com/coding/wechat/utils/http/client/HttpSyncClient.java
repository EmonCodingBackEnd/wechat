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

import com.coding.wechat.utils.http.HttpConfig;
import com.coding.wechat.utils.http.HttpConsts;
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
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.AbstractResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.util.Assert;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
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

    public static final CloseableHttpClient httpSyncClient;

    public static final CloseableHttpClient httpSyncRetryClient;

    static {
        httpSyncClient = HttpSyncClientGenerator.custom().pool().timeout().build();
        httpSyncRetryClient = HttpSyncClientGenerator.custom().pool().timeout().retry().build();
    }

    /**
     * 该方法返回字符串格式的应答，因此会占用内存，不适用于应答内容超过1MB的请求.
     *
     * <p>特殊说明：paramMap与paramString二选一
     *
     * @param client - 自定义的同步HttpClient
     * @param httpMethod - 请求方法，参见{@linkplain HttpMethod}
     * @param url - 请求地址
     * @param paramMap - <key,value>格式的请求参数，仅限于POST、PUT、PATCH、DELETE方法
     * @param paramString - 字符串格式的请求参数，仅限于POST、PUT、PATCH、DELETE方法，适用于json、xml等请求参数
     * @param timeout - 超时时间，-1表示永不超时，小于-1会被重置为默认值，单位：毫秒
     * @param headers - 请求头
     * @param charset - 请求体编码
     * @throws IOException -
     */
    public static String execute(
            HttpClient client,
            HttpMethod httpMethod,
            String url,
            Map<String, String> paramMap,
            String paramString,
            int timeout,
            Header[] headers,
            Charset charset)
            throws IOException {
        return execute(
                client,
                httpMethod,
                url,
                paramMap,
                paramString,
                timeout,
                headers,
                charset,
                new AbstractResponseHandler<String>() {
                    @Override
                    public String handleEntity(HttpEntity entity) throws IOException {
                        return EntityUtils.toString(entity, charset);
                    }
                });
    }

    /**
     * 无返回结果调用，应答数据会输出到给定的输出流，该方法不会自动关闭输出流.
     *
     * <p>特殊说明：paramMap与paramString二选一
     *
     * @param client - 自定义的同步HttpClient
     * @param httpMethod - 请求方法，参见{@linkplain HttpMethod}
     * @param url - 请求地址
     * @param paramMap - <key,value>格式的请求参数，仅限于POST、PUT、PATCH、DELETE方法
     * @param paramString - 字符串格式的请求参数，仅限于POST、PUT、PATCH、DELETE方法，适用于json、xml等请求参数
     * @param timeout - 超时时间，-1表示永不超时，小于-1会被重置为默认值，单位：毫秒
     * @param headers - 请求头
     * @param charset - 请求体编码
     * @param outputStream - 存放应答的输出流
     * @throws IOException -
     */
    public static void execute(
            HttpClient client,
            HttpMethod httpMethod,
            String url,
            Map<String, String> paramMap,
            String paramString,
            int timeout,
            Header[] headers,
            Charset charset,
            OutputStream outputStream)
            throws IOException {
        execute(
                client,
                httpMethod,
                url,
                paramMap,
                paramString,
                timeout,
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

    /**
     * 基本请求方法，内部使用.
     *
     * <p>特殊说明：paramMap与paramString二选一
     *
     * @param client - 自定义的同步HttpClient
     * @param httpMethod - 请求方法，参见{@linkplain HttpMethod}
     * @param url - 请求地址
     * @param paramMap - <key,value>格式的请求参数，仅限于POST、PUT、PATCH、DELETE方法
     * @param paramString - 字符串格式的请求参数，仅限于POST、PUT、PATCH、DELETE方法，适用于json、xml等请求参数
     * @param timeout - 超时时间，-1表示永不超时，小于-1会被重置为默认值，单位：毫秒
     * @param headers - 请求头
     * @param charset - 请求体编码
     * @param responseHandler - 应答处理器，用来处理应答数据
     * @param <T> - 处理后的应答数据返回类型
     * @return - 应答结果
     * @throws IOException -
     */
    public static <T> T execute(
            HttpClient client,
            HttpMethod httpMethod,
            String url,
            Map<String, String> paramMap,
            String paramString,
            int timeout,
            Header[] headers,
            Charset charset,
            ResponseHandler<T> responseHandler)
            throws IOException {
        Assert.notNull(url, "url must be not null");

        HttpRequestBase httpRequest = HttpMethod.getHttpRequest(url, httpMethod);
        httpRequest.setHeaders(headers);

        String urlHost = HttpConsts.EMPTY;
        String urlParam = HttpConsts.EMPTY;
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
            } else if (paramString != null) {
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
        if (timeout != Integer.MIN_VALUE) {
            if (timeout < -1) {
                timeout = HttpConfig.DEFAULT_SOCKET_TIMEOUT;
            }
            RequestConfig requestConfig =
                    RequestConfig.custom()
                            .setConnectTimeout(timeout)
                            .setSocketTimeout(timeout)
                            .setConnectionRequestTimeout(timeout)
                            .build();
            httpRequest.setConfig(requestConfig);
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

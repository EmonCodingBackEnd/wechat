/*
 * 文件名称：WechatUtil.java
 * 系统名称：[系统名称]
 * 模块名称：[模块名称]
 * 软件版权：Copyright (c) 2011-2018, liming20110711@163.com All Rights Reserved.
 * 功能说明：[请在此处输入功能说明]
 * 开发人员：Rushing0711
 * 创建日期：20180416 23:21
 * 修改记录：
 * <Version>        <DateSerial>        <Author>        <Description>
 * 1.0.0            20180416-01         Rushing0711     M201804162321 新建文件
 ********************************************************************************/
package com.coding.wechat.utils;

import com.sun.xml.internal.ws.Closeable;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * [请在此输入功能简述].
 *
 * <p>创建时间: <font style="color:#00FFFF">20180416 23:21</font><br>
 * [请在此输入功能详述]
 *
 * @author Rushing0711
 * @version 1.0.0
 * @since 1.0.0
 */
public class WechatUtil {

    private static CloseableHttpClient httpClient;

    static {
        RequestConfig config =
                RequestConfig.custom().setConnectTimeout(60000).setSocketTimeout(15000).build();
        httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
    }

    /** 
     * get请求.
     * 
     * <p>创建时间: <font style="color:#00FFFF">20180420 00:14</font><br>
     * [请在此输入功能详述]
     * 
     * @param url -
     * @return net.sf.json.JSONObject
     * @author Rushing0711
     * @since 1.0.0
     */
    public static JSONObject doGetStr(String url) {
        HttpGet httpGet = new HttpGet(url);
        JSONObject jsonObject = null;
        try {
            HttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result = EntityUtils.toString(entity);
                jsonObject = JSONObject.fromObject(result);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
        return jsonObject;
    }

    /** 
     * post请求.
     * 
     * <p>创建时间: <font style="color:#00FFFF">20180420 00:15</font><br>
     * [请在此输入功能详述]
     * 
     * @param url -
     * @param outStr -
     * @return net.sf.json.JSONObject
     * @author Rushing0711
     * @since 1.0.0
     */
    public static JSONObject doPostStr(String url, String outStr) {
        HttpPost httpPost = new HttpPost(url);
        JSONObject jsonObject = null;
        try {
            httpPost.setEntity(new StringEntity(outStr, "UTF-8"));
            HttpResponse response = httpClient.execute(httpPost);
            String result = EntityUtils.toString(response.getEntity(), "UTF-8");
            jsonObject = JSONObject.fromObject(result);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
        return jsonObject;
    }
}

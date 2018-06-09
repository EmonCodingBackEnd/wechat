/*
 * 文件名称：HttpMethod.java
 * 系统名称：[系统名称]
 * 模块名称：Http支持的方法定义
 * 软件版权：Copyright (c) 2011-2018, liming20110711@163.com All Rights Reserved.
 * 功能说明：[请在此处输入功能说明]
 * 开发人员：Rushing0711
 * 创建日期：20180607 14:27
 * 修改记录：
 * <Version>        <DateSerial>        <Author>        <Description>
 * 1.0.0            20180607-01         Rushing0711     M201806071427 新建文件
 ********************************************************************************/
package com.coding.wechat.component.http.support;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;

/**
 * Http支持的方法定义.
 *
 * <p>创建时间: <font style="color:#00FFFF">20180607 14:27</font><br>
 * [请在此输入功能详述]
 *
 * @author Rushing0711
 * @version 1.0.0
 * @since 1.0.0
 */
public enum HttpMethod {
    GET,
    HEAD,
    POST,
    PUT,
    PATCH,
    DELETE,
    OPTIONS,
    TRACE;

    public static HttpRequestBase getHttpRequest(String url, HttpMethod httpMethod) {
        HttpRequestBase httpRequest = null;

        switch (httpMethod) {
            case GET:
                httpRequest = new HttpGet(url);
                break;
            case HEAD:
                httpRequest = new HttpGet(url);
                break;
            case POST:
                httpRequest = new HttpGet(url);
                break;
            case PUT:
                httpRequest = new HttpGet(url);
                break;
            case PATCH:
                httpRequest = new HttpGet(url);
                break;
            case DELETE:
                httpRequest = new HttpGet(url);
                break;
            case OPTIONS:
                httpRequest = new HttpGet(url);
                break;
            case TRACE:
                httpRequest = new HttpGet(url);
                break;
            default:
                httpRequest = new HttpPost(url);
        }
        return httpRequest;
    }
}

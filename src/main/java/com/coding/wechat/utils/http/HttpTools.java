/*
 * 文件名称：HttpTools.java
 * 系统名称：[系统名称]
 * 模块名称：Http工具
 * 软件版权：Copyright (c) 2011-2018, liming20110711@163.com All Rights Reserved.
 * 功能说明：[请在此处输入功能说明]
 * 开发人员：Rushing0711
 * 创建日期：20180607 12:06
 * 修改记录：
 * <Version>        <DateSerial>        <Author>        <Description>
 * 1.0.0            20180607-01         Rushing0711     M201806071206 新建文件
 ********************************************************************************/
package com.coding.wechat.utils.http;

import com.coding.wechat.utils.http.client.HttpAsyncClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.concurrent.FutureCallback;

import java.util.concurrent.Semaphore;

/**
 * Http工具.
 *
 * <p>创建时间: <font style="color:#00FFFF">20180607 12:07</font><br>
 * [请在此输入功能详述]
 *
 * @author Rushing0711
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
public abstract class HttpTools {


    private static final Semaphore concurrency = new Semaphore(1024);

    public static void main(String[] args) {
        concurrency.acquireUninterruptibly();
        // step2 设置HttpUrlRequest

        HttpGet httpGet = new HttpGet("http://www.baidu.com");

        // step3 执行异步调用
        HttpAsyncClient.getInstance()
                .execute(
                        httpGet,
                        new FutureCallback<HttpResponse>() {
                            @Override
                            public void completed(HttpResponse httpResponse) {
                                // 处理Http响应
                                log.info("【Http异步】completed");
                            }

                            @Override
                            public void failed(Exception e) {
                                // 根据情况进行重试
                                log.error("【Http异步】异常", e);
                            }

                            @Override
                            public void cancelled() {
                                // 记录失败日志
                                log.info("【Http异步】canceller");
                            }
                        });
        concurrency.release();
    }
}

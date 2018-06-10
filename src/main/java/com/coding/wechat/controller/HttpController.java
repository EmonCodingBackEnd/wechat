/*
 * 文件名称：HttpController.java
 * 系统名称：[系统名称]
 * 模块名称：Http工具类测试接口
 * 软件版权：Copyright (c) 2011-2018, liming20110711@163.com All Rights Reserved.
 * 功能说明：[请在此处输入功能说明]
 * 开发人员：Rushing0711
 * 创建日期：20180606 12:35
 * 修改记录：
 * <Version>        <DateSerial>        <Author>        <Description>
 * 1.0.0            20180606-01         Rushing0711     M201806061235 新建文件
 ********************************************************************************/
package com.coding.wechat.controller;

import com.coding.wechat.component.http.HttpException;
import com.coding.wechat.config.WechatConfig;
import com.coding.wechat.component.http.HttpTools;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.Charsets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Http工具类测试接口.
 *
 * <p>创建时间: <font style="color:#00FFFF">20180606 12:29</font><br>
 * [请在此输入功能详述]
 *
 * @author Rushing0711
 * @version 1.0.0
 * @since 1.0.0
 */
@RestController
@RequestMapping("/http")
@Slf4j
public class HttpController {

    @Autowired WechatConfig wechatConfig;

    @RequestMapping(value = "test")
    public String test(
            HttpServletRequest request,
            @RequestParam(value = "timeout", defaultValue = "0") int timeout,
            String key)
            throws IOException {
        String result;
        if (timeout != 0) {
            try {
                TimeUnit.MILLISECONDS.sleep(timeout);
            } catch (InterruptedException e) {
                throw new HttpException("【test】睡眠异常");
            }
        }
        if (key != null) {
            result = key;
            log.info("【test】你请求的参数是param", key);
        } else {
            result = "no param";
            log.info("【test】收到请求");
        }
        return result;
    }

    @GetMapping(value = "readByUrl")
    public void readByUrl(HttpServletResponse response, String urlStr) throws IOException {
        String decodeUrl = URLDecoder.decode(urlStr, Charsets.UTF_8.name());
        response.setContentType("text/html;charset=UTF-8");
        /* OutputStream out = response.getOutputStream();
        HttpClientUtils.doGet(decodeUrl, out);*/
        String result = HttpTools.doGet(decodeUrl);
        result =
                result.replace(
                                "http://www.baidu.com",
                                "https://exp.mynatapp.cc/wechat/http/readByUrl?urlStr=http://www.baidu.com")
                        .replace(
                                "https%3A%2F%2Fmp.weixin.qq.com",
                                "https://exp.mynatapp.cc/wechat/http/readByUrl?urlStr=https%3A%2F%2Fmp.weixin.qq.com");
        Writer writer = response.getWriter();
        writer.write(result);
        writer.flush();
        writer.close();
    }

    @GetMapping(value = "readByUrlBatch")
    public String readByUrlBatch(
            HttpServletResponse response, int concurrencyCount, int threadCount, String[] urlStr)
            throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        log.info("【readByUrlBatchTest】start");

        final Semaphore semaphore = new Semaphore(concurrencyCount);
        final CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        ExecutorService executorService = Executors.newCachedThreadPool();
        final AtomicInteger[] statistic = {new AtomicInteger(0), new AtomicInteger(0)};
        for (int i = 0; i < threadCount; i++) {
            semaphore.acquireUninterruptibly();
            executorService.execute(
                    () -> {
                        try {
                            for (String url : urlStr) {
                                HttpTools.doGet(url);
                            }
                            statistic[0].incrementAndGet();
                        } catch (IOException e) {
                            log.error("【readByUrlBatchTest】exception", e);
                            statistic[1].incrementAndGet();
                        } finally {
                            countDownLatch.countDown();
                            semaphore.release();
                        }
                    });
        }
        countDownLatch.await();
        executorService.shutdown();
        long end = System.currentTimeMillis();
        String result =
                String.format(
                        "总用时=%sms,success=%s,failure=%s",
                        (end - start), statistic[0], statistic[1]);
        log.info("【readByUrlBatchTest】finish,{}", result);
        return result;
    }

    @GetMapping(value = "encodeUrl")
    public String encodeUrl(String urlStr) throws UnsupportedEncodingException {
        return URLEncoder.encode(urlStr, Charsets.UTF_8.name());
    }
}

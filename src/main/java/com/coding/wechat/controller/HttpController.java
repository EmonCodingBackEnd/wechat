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

import com.coding.wechat.component.constants.Consts;
import com.coding.wechat.component.http.HttpException;
import com.coding.wechat.component.http.HttpTools;
import com.coding.wechat.component.regex.RegexDefine;
import com.coding.wechat.config.WechatConfig;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.impl.client.AbstractResponseHandler;
import org.apache.http.util.CharArrayBuffer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.net.URLDecoder.decode;

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

    @GetMapping(value = "/readByUrl")
    public void readByUrl(HttpServletResponse response, String urlStr) throws IOException {
        String decodeUrl = decode(urlStr, StandardCharsets.UTF_8.name());
        /*response.setContentType("audio/mpeg");
        OutputStream out = response.getOutputStream();
        HttpTools.doGet(decodeUrl, out);
        out.flush();
        out.close();*/
        Map<String, String> replaceMap = Maps.newHashMap();
        replaceMap.put(
                "https://mp.weixin.qq.com",
                "https://exp.mynatapp.cc/wechat/http/readByUrl?urlStr=");
        replaceMap.put(
                "https://mmbiz.qpic.cn",
                "https://exp.mynatapp.cc/wechat/http/readByUrlImg?urlStr=");

        response.setContentType("text/html;charset=UTF-8");
        Pattern wxUrlPattern = RegexDefine.PROXY_GOOD_URL_REGEX_PATTERN;
        /*String re = HttpTools.doGet(decodeUrl, 10000);
        Matcher wxUrlMatcher = wxUrlPattern.matcher(re);
        while (wxUrlMatcher.find()) {
            System.out.println("来了");
        }*/
        HttpTools.doGet(
                decodeUrl,
                10000,
                new AbstractResponseHandler<Long>() {
                    @Override
                    public Long handleEntity(HttpEntity entity) throws IOException {
                        log.info(
                                "【Http】isStreaming={},isChunked={},isRepeatable={}",
                                entity.isStreaming(),
                                entity.isChunked(),
                                entity.isRepeatable());

                        long contentLength = 0;
                        final InputStream instream = entity.getContent();
                        if (instream == null) {
                            return null;
                        }
                        try {
                            int capacity = Consts.C_COMMON.DEFAULT_BUFFER_SIZE;
                            final Reader reader =
                                    new InputStreamReader(instream, StandardCharsets.UTF_8);
                            final CharArrayBuffer buffer = new CharArrayBuffer(capacity);
                            final char[] tmp = new char[1024];
                            int l;
                            while ((l = reader.read(tmp)) != -1) {
                                buffer.append(tmp, 0, l);
//                                buffer.
                            }
                        } finally {
                            instream.close();
                        }

                        OutputStream outputStream = response.getOutputStream();

                        final Writer writer = response.getWriter();
                        BufferedReader bufferedReader =
                                new BufferedReader(
                                        new InputStreamReader(
                                                entity.getContent(), StandardCharsets.UTF_8));

                        String result;
                        long len = 0;
                        while ((result = bufferedReader.readLine()) != null) {
                            len++;
                            contentLength += result.getBytes(StandardCharsets.UTF_8).length;
                            Matcher wxUrlMatcher = wxUrlPattern.matcher(result);
                            while (wxUrlMatcher.find()) {
                                String wxUrl = null;
                                for (int i = 1; i <= wxUrlMatcher.groupCount(); i++) {
                                    if (wxUrlMatcher.group(i) != null) {
                                        wxUrl = wxUrlMatcher.group(i);
                                        break;
                                    }
                                }
                                String wxUrlParsed;
                                if (!StringUtils.isEmpty(wxUrl)) {
                                    for (Map.Entry<String, String> entry : replaceMap.entrySet()) {
                                        if (wxUrl.startsWith(entry.getKey())) {
                                            wxUrlParsed =
                                                    entry.getValue()
                                                            + URLEncoder.encode(
                                                                    wxUrl,
                                                                    StandardCharsets.UTF_8.name());
                                            result = result.replace(wxUrl, wxUrlParsed);
                                            log.info(
                                                    "在索引区间[{}, {}]找到匹配组[{}]<==>[{}]",
                                                    wxUrlMatcher.start(),
                                                    wxUrlMatcher.end(),
                                                    wxUrl,
                                                    wxUrlParsed);
                                            break;
                                        }
                                    }
                                }
                            }
                            writer.write(result);
                            writer.flush();
                        }
                        writer.close();
                        log.info("【Http】len={}", len);
                        return contentLength;
                    }
                });
    }

    @GetMapping(value = "/readByUrlImg")
    public void readByUrlImg(HttpServletResponse response, String urlStr) throws IOException {
        String decodeUrl = decode(urlStr, StandardCharsets.UTF_8.name());
        /*response.setContentType("audio/mpeg");
        OutputStream out = response.getOutputStream();
        HttpTools.doGet(decodeUrl, out);
        out.flush();
        out.close();*/
        response.setContentType("image/jpeg");
        //        response.setContentType("application/octet-stream");
        OutputStream out = response.getOutputStream();
        HttpTools.doGet(decodeUrl, 10000, out);
        out.flush();
        out.close();
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
        return URLEncoder.encode(urlStr, StandardCharsets.UTF_8.name());
    }
}

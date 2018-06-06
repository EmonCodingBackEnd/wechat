package com.coding.wechat.utils;

import com.coding.wechat.utils.http.HttpClientUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class HttpClientUtilsTest {

    @Test
    public void doGet() throws Exception {
        int threadCount = 200;
        ExecutorService executorService = Executors.newCachedThreadPool();
        final CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            final int threadNum = i;
            executorService.execute(
                    () -> {
                        try {
                            HttpClientUtils.doGet("http://www.baidu.com");
                        } catch (IOException e) {
                            log.error("exception", e);
                        } finally {
                            countDownLatch.countDown();
                        }
                    });
        }
        countDownLatch.await();
        executorService.shutdown();
        log.info("finish");
    }

    @Test
    public void doGetTimeout() throws Exception {
        String url = "http://localhost:8080/wechat/website/introduction";
        HttpClientUtils.doGetTimeout(url, 100);
    }
}

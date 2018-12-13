/*
 * 文件名称：RecordNoMgrController.java
 * 系统名称：[系统名称]
 * 模块名称：[模块名称]
 * 软件版权：Copyright (c) 2011-2018, liming20110711@163.com All Rights Reserved.
 * 功能说明：[请在此处输入功能说明]
 * 开发人员：Rushing0711
 * 创建日期：20181107 12:16
 * 修改记录：
 * <Version>        <DateSerial>        <Author>        <Description>
 * 1.0.0            20181107-01         Rushing0711     M201811071216 新建文件
 ********************************************************************************/
package com.coding.wechat.controller;

import com.coding.component.recordno.service.RecordNoMgrService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/recordNo")
@Slf4j
public class RecordNoMgrController {
    @Autowired RecordNoMgrService recordNoMgrService;

    @GetMapping("/batch")
    public String getRecordNoBatch(
            @RequestParam(value = "concurrencyCount", defaultValue = "10") int concurrencyCount,
            @RequestParam(value = "threadCount", defaultValue = "100") int threadCount)
            throws InterruptedException {
        long start = System.currentTimeMillis();
        log.info("【getRecordNoBatchTest】start");

        final Semaphore semaphore = new Semaphore(concurrencyCount);
        final CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        ExecutorService executorService = Executors.newCachedThreadPool();
        final AtomicInteger[] statistic = {new AtomicInteger(0), new AtomicInteger(0)};
        for (int i = 0; i < threadCount; i++) {
            semaphore.acquireUninterruptibly();
            executorService.execute(
                    () -> {
                        try {
                            recordNoMgrService.getTenantRecordNo();
                            recordNoMgrService.getBatchTenantRecordNo(9);
                            recordNoMgrService.getShopRecordNo("shop");
                            recordNoMgrService.getBatchShopRecordNo("shop", 9);
                            recordNoMgrService.getGoodsRecordNo("goods");
                            recordNoMgrService.getBatchGoodsRecordNo("goods", 9);
                            recordNoMgrService.getUserinfoRecordNo("userinfo");
                            recordNoMgrService.getBatchUserinfoRecordNo("userinfo", 9);
                            recordNoMgrService.getCustomerRecordNo();
                            recordNoMgrService.getBatchCustomerRecordNo(9);
                            recordNoMgrService.getOrderRecordNo();
                            recordNoMgrService.getBatchOrderRecordNo(9);

                            statistic[0].incrementAndGet();
                        } catch (Exception e) {
                            log.error("【getRecordNoBatchTest】exception", e);
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
        log.info("【getRecordNoBatchTest】finish,{}", result);
        return result;
    }
}

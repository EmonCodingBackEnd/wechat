/*
 * 文件名称：AsyncTask.java
 * 系统名称：[系统名称]
 * 模块名称：异步任务
 * 软件版权：Copyright (c) 2011-2018, liming20110711@163.com All Rights Reserved.
 * 功能说明：[请在此处输入功能说明]
 * 开发人员：Rushing0711
 * 创建日期：20180613 14:30
 * 修改记录：
 * <Version>        <DateSerial>        <Author>        <Description>
 * 1.0.0            20180613-01         Rushing0711     M201806131430 新建文件
 ********************************************************************************/
package com.coding.component.timer.schedule.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * 异步任务.
 *
 * <p>创建时间: <font style="color:#00FFFF">20180613 14:31</font><br>
 * [请在此输入功能详述]
 *
 * @author Rushing0711
 * @version 1.0.0
 * @since 1.0.0
 */
@Async
@EnableAsync
@Component
@Slf4j
public class AsyncTask {

    // 获取异步结果
    public Future<Long> asyncTask11(int startNumber, int endNumber) throws InterruptedException {
        long beg = System.currentTimeMillis();
        long sum = 0;
        for (int i = startNumber; i <= endNumber; i++) {
            sum += i;
        }
        TimeUnit.SECONDS.sleep(2);
        long end = System.currentTimeMillis();
        log.info("asyncTask11执行耗时={}ms", end - beg);
        return new AsyncResult<>(sum);
    }

    // 获取异步结果
    public Future<Long> asyncTask12(int startNumber, int endNumber) throws InterruptedException {
        long beg = System.currentTimeMillis();
        long sum = 0;
        for (int i = startNumber; i <= endNumber; i++) {
            sum += i;
        }
        TimeUnit.SECONDS.sleep(3);
        long end = System.currentTimeMillis();
        log.info("asyncTask12执行耗时={}ms", end - beg);
        return new AsyncResult<>(sum);
    }
}

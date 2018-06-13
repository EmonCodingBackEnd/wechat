/*
 * 文件名称：TimerController.java
 * 系统名称：[系统名称]
 * 模块名称：延时任务、定时任务、异步任务测试接口
 * 软件版权：Copyright (c) 2011-2018, liming20110711@163.com All Rights Reserved.
 * 功能说明：[请在此处输入功能说明]
 * 开发人员：Rushing0711
 * 创建日期：20180613 15:07
 * 修改记录：
 * <Version>        <DateSerial>        <Author>        <Description>
 * 1.0.0            20180613-01         Rushing0711     M201806131507 新建文件
 ********************************************************************************/
package com.coding.wechat.controller;

import com.coding.wechat.component.timer.schedule.task.AsyncTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * 延时任务、定时任务、异步任务测试接口.
 *
 * <p>创建时间: <font style="color:#00FFFF">20180613 15:07</font><br>
 * [请在此输入功能详述]
 *
 * @author Rushing0711
 * @version 1.0.0
 * @since 1.0.0
 */
@RestController
@Slf4j
@RequestMapping("timer")
public class TimerController {

    @Autowired private AsyncTask asyncTask;

    @GetMapping("asyncTask")
    public Long asyncTask(@RequestParam(value = "number", defaultValue = "100") int number)
            throws InterruptedException, ExecutionException {
        long beg = System.currentTimeMillis();

        Future<Long> asyncTask11 = asyncTask.asyncTask11(1, number / 2);
        Future<Long> asyncTask12 = asyncTask.asyncTask12(number / 2 + 1, number);

        // 如果都执行往就可以跳出循环,isDone方法如果此任务完成，true
        for (; ; ) {
            if (asyncTask11.isDone() && asyncTask12.isDone()) {
                break;
            }
        }

        long end = System.currentTimeMillis();
        log.info("asyncTask执行耗时={}ms", end - beg);
        return asyncTask11.get() + asyncTask12.get();
    }
}

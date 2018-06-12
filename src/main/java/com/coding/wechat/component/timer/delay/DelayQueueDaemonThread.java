/*
 * 文件名称：DelayQueueDaemonThread.java
 * 系统名称：[系统名称]
 * 模块名称：延迟队列守护线程
 * 软件版权：Copyright (c) 2011-2018, liming20110711@163.com All Rights Reserved.
 * 功能说明：[请在此处输入功能说明]
 * 开发人员：Rushing0711
 * 创建日期：20180515 13:36
 * 修改记录：
 * <Version>        <DateSerial>        <Author>        <Description>
 * 1.0.0            20180515-01         Rushing0711     M201805151336 新建文件
 ********************************************************************************/
package com.coding.wechat.component.timer.delay;

import com.coding.wechat.component.constants.Consts;
import com.coding.wechat.component.timer.TimerPoolConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 延迟队列守护线程.
 *
 * <p>创建时间: <font style="color:#00FFFF">20180515 14:11</font><br>
 * [请在此输入功能详述]
 *
 * @author Rushing0711
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
@Slf4j
public class DelayQueueDaemonThread {

    @Autowired TimerPoolConfig timerPoolConfig;

    private ThreadPoolTaskExecutor delayQueueExecutor;

    /** 初始化守护线程 */
    @PostConstruct
    private void init() {
        log.info(
                "【延迟队列线程池配置】corePoolSize={},maxPoolSize={},queueCapacity={}",
                timerPoolConfig.getCorePoolSize(),
                timerPoolConfig.getMaxPoolSize(),
                timerPoolConfig.getQueueCapacity());
        delayQueueExecutor = new ThreadPoolTaskExecutor();
        delayQueueExecutor.setCorePoolSize(timerPoolConfig.getCorePoolSize());
        delayQueueExecutor.setMaxPoolSize(timerPoolConfig.getMaxPoolSize());
        delayQueueExecutor.setQueueCapacity(timerPoolConfig.getQueueCapacity());
        delayQueueExecutor.setThreadNamePrefix(Consts.C_COMMON.TIMER_DELAY_THREAD_PREFIX);
        delayQueueExecutor.initialize();

        Thread daemonThread = new Thread(() -> execute());
        daemonThread.setDaemon(true);
        daemonThread.setName("Delay Task Queue Daemon Thread");
        daemonThread.start();
    }

    private void execute() {
        log.info("【延迟任务队列守护线程】开启,thread={}", Thread.currentThread().getId());
        while (true) {
            // 阻塞式获取
            DelayedItem item;
            try {
                item = DelayQueueSupport.getDelayedItems().take();
                if (item != null) {
                    DelayTask task = item.getTask();
                    if (task == null) {
                        continue;
                    }
                    log.info("【延迟任务队列】任务已提取并加入线程池,taskId={}", task.getTaskId());
                    delayQueueExecutor.execute(task);
                }
            } catch (InterruptedException e) {
                log.error("【延迟任务队列守护线程】异常", e);
                break;
            }
        }
        log.info("【延迟任务队列守护线程】关闭,thread={}", Thread.currentThread().getId());
    }
}

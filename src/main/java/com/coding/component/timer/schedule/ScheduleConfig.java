/*
 * 文件名称：ScheduleConfig.java
 * 系统名称：[系统名称]
 * 模块名称：并行任务配置
 * 软件版权：Copyright (c) 2011-2018, liming20110711@163.com All Rights Reserved.
 * 功能说明：[请在此处输入功能说明]
 * 开发人员：Rushing0711
 * 创建日期：20180613 07:54
 * 修改记录：
 * <Version>        <DateSerial>        <Author>        <Description>
 * 1.0.0            20180613-01         Rushing0711     M201806130754 新建文件
 ********************************************************************************/
package com.coding.component.timer.schedule;

import com.coding.component.timer.TimerPoolConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executor;

/**
 * 并行任务配置.
 *
 * <p>创建时间: <font style="color:#00FFFF">20180613 07:54</font><br>
 * [请在此输入功能详述]
 *
 * @author Rushing0711
 * @version 1.0.0
 * @since 1.0.0
 */
@Configuration
@Slf4j
public class ScheduleConfig implements SchedulingConfigurer {

    @Autowired TimerPoolConfig timerPoolConfig;

    /**
     * 并行任务。
     *
     * @param taskRegistrar -
     */
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(taskExecutor());
    }

    /**
     * 并行任务使用策略，多线程处理。
     *
     * @return -
     */
    @Bean
    public Executor taskExecutor() {
        log.info(
                "【定时器线程池配置】threadNamePrefix={},poolSize={},awaitTerminationSeconds={}",
                timerPoolConfig.getSchedule().getThreadNamePrefix(),
                timerPoolConfig.getSchedule().getPoolSize(),
                timerPoolConfig.getSchedule().getAwaitTerminationSeconds());
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setThreadNamePrefix(timerPoolConfig.getSchedule().getThreadNamePrefix());
        scheduler.setPoolSize(timerPoolConfig.getSchedule().getPoolSize());
        scheduler.setAwaitTerminationSeconds(
                timerPoolConfig.getSchedule().getAwaitTerminationSeconds());
        scheduler.setWaitForTasksToCompleteOnShutdown(true);
        return scheduler;
    }
}

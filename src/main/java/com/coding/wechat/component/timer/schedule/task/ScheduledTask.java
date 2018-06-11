/*
 * 文件名称：ScheduledService.java
 * 系统名称：[系统名称]
 * 模块名称：定时器任务类
 * 软件版权：Copyright (c) 2011-2018, liming20110711@163.com All Rights Reserved.
 * 功能说明：[请在此处输入功能说明]
 * 开发人员：Rushing0711
 * 创建日期：20180515 10:38
 * 修改记录：
 * <Version>        <DateSerial>        <Author>        <Description>
 * 1.0.0            20180515-01         Rushing0711     M201805151038 新建文件
 ********************************************************************************/
package com.coding.wechat.component.timer.schedule.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

/**
 * 定时器任务类.
 *
 * <p>创建时间: <font style="color:#00FFFF">20180512 08:58</font><br>
 * [请在此输入功能详述]
 *
 * @author Rushing0711
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
@EnableScheduling
@Async
@Slf4j
public class ScheduledTask {

    /*
     * 定时配置有三种：
     * 1、cron 2、fixedRate 3、fixedDelay
     * 这里推荐使用cron，同时定义在该方法中的定时任务，都是异步执行的，
     * 两次任务执行之间只有共享资源的影响（比如：线程池资源、数据库资源）。
     * 所以，定时间隔要有考虑，保证任务执行时间间隔大于任务执行花费时间。
     * 针对corn的配置说明：
     * 元素               允许的配置字符
     * - 秒(0-59)         , - * /
     * - 分(0-59)         , - * /
     * - 时(0-23)         , - * /
     * - 天(0-31)         , - * /
     * - 月(0-11)         , - * / ?
     * - 星期(1-7 1=SUN，或者SUN,MON,TUE,WED,THU,FRI,AST)    , - * / ? L C #
     * - 年(1970-2099)    , - * /                        【Spring不支持年位定制】
     * 其中： , 表示单值，比如： 0 0 10,14,16 * * ?    含义：每天上午10点，下午2点和4点执行
     *       - 表示区间，比如： 0 0 10-16 * * ?       含义：每天上午10点到下午4点的每个小时
     *       / 表示每隔，比如： 0/5 * * * * *         含义：每隔5秒
     *       ? 只能用于月与星期，月与星期的天数，可能冲突，所以可以用?指定其中某一个指不需要设置
     * 其他的使用方式，请自行搜索cron表达式。
     * 这里有一个cron表达式生成器： http://cron.qqe2.com/
     */

    /*@Scheduled(cron = "0 0/1 * * * *")
    public void scheduled() throws InterruptedException {
        log.info("=====>>>>>使用cron1  {}", System.currentTimeMillis() / 1000);
        TimeUnit.SECONDS.sleep(10);
        log.info(Thread.currentThread().getId() + "今夕何夕1");
    }

    @Scheduled(cron = "0/30 * * * * *")
    public void scheduled2() throws InterruptedException {
        log.info("=====>>>>>使用cron2  {}", System.currentTimeMillis() / 1000);
        TimeUnit.SECONDS.sleep(10);
        log.info(Thread.currentThread().getId() + "今夕何夕2");
    }*/
}

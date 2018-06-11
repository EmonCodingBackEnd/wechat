/*
 * 文件名称：OrderDelayService.java
 * 系统名称：[系统名称]
 * 模块名称：订单延迟业务
 * 软件版权：Copyright (c) 2011-2018, liming20110711@163.com All Rights Reserved.
 * 功能说明：[请在此处输入功能说明]
 * 开发人员：Rushing0711
 * 创建日期：20180515 14:30
 * 修改记录：
 * <Version>        <DateSerial>        <Author>        <Description>
 * 1.0.0            20180515-01         Rushing0711     M201805151430 新建文件
 ********************************************************************************/
package com.coding.wechat.component.timer.delay.order;

import com.coding.wechat.component.config.CommonConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 系统启动，加入未完成订单.
 *
 * <p>创建时间: <font style="color:#00FFFF">20180517 12:02</font><br>
 * [请在此输入功能详述]
 *
 * @author Rushing0711
 * @version 1.0.0
 * @since 1.0.0
 */
@Service
@Slf4j
public class OrderDelayService implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired private CommonConfig commonConfig;

    public void init() {}

    // 计算订单创建时间，还有多久超时，单位:毫秒
    private long taskTimeout(OrderDelayTask orderDelayTask, String createTime) {
        long time = 0;
        // 如果任务已超时，在随机时间之后执行
        if (time <= 0) {
            Random random = new Random();
            // 获取一个100秒之内的数字
            Integer randomMillis = random.nextInt(90000) + 10000;
            log.info(
                    "【延迟任务】taskId={},任务已过期,随机延迟={}s",
                    orderDelayTask.getTaskId(),
                    TimeUnit.MILLISECONDS.toSeconds(randomMillis));
            time = randomMillis;
        }
        return time;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (contextRefreshedEvent.getApplicationContext().getParent() == null) {
            init();
        }
    }
}

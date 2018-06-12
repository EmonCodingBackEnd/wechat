package com.coding.wechat.component.constants;

import java.nio.charset.Charset;

/**
 * 常量类集合.
 *
 * <p>
 *
 * <p>创建时间: <font style="color:#00FFFF">20180423 17:51</font><br>
 * [请在此输入功能详述]
 *
 * @author Rushing0711
 * @version 1.0.0
 * @since 1.0.0
 */
public interface Consts {

    /** 通用常量定义 */
    interface C_COMMON {
        /** 系统名称 */
        String SYSTEM_NAME = "SAAS_SSP";

        /** 系统中使用到的默认编码 */
        Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

        /** 并行定时器线程名称前缀 * */
        String TIMER_SYNC_THREAD_PREFIX = "TIMER_SYNC-";
        
        /** 异步定时器线程名称前缀 * */
        String TIMER_ASYNC_THREAD_PREFIX = "TIMER_ASYNC-";

        /** 延迟队列线程名称前缀 */
        String TIMER_DELAY_THREAD_PREFIX = "TIMER_DELAY-";
    }

    /*
     * 要求：
     * 常量类以 C 开头，表示Constants，示例： C_XXX
     * 常量必须大写单词，下划线 _ 分隔多个单词
     */

    /** 用户相关常量类定义 */
    interface C_USER {
        /** 登录session key */
        String SESSION_KEY = "manage_login";
    }
}

/*
 * 文件名称：WechatConfig.java
 * 系统名称：[系统名称]
 * 模块名称：[模块名称]
 * 软件版权：Copyright (c) 2011-2018, liming20110711@163.com All Rights Reserved.
 * 功能说明：[请在此处输入功能说明]
 * 开发人员：Rushing0711
 * 创建日期：20180416 23:34
 * 修改记录：
 * <Version>        <DateSerial>        <Author>        <Description>
 * 1.0.0            20180416-01         Rushing0711     M201804162334 新建文件
 ********************************************************************************/
package com.coding.wechat.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 微信基本配置.
 *
 * <p>创建时间: <font style="color:#00FFFF">20180416 23:34</font><br>
 * [请在此输入功能详述]
 *
 * @author Rushing0711
 * @version 1.0.0
 * @since 1.0.0
 */
@Data
@Component
@ConfigurationProperties(prefix = "wechat")
public class WechatConfig {

    /** 第三方用户唯一凭证 */
    private String appId;

    /** 第三方用户唯一凭证密钥，即appsecret */
    private String appSecret;

    /** 令牌(Token) */
    private String token;

    /** https获取access_token的请求方式： GET */
    private String accessTokenUrl;

    /** https新增临时素材的请求方式： POST/FORM. */
    private String uploadUrl;
}

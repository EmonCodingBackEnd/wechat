/*
 * 文件名称：AccessToken.java
 * 系统名称：[系统名称]
 * 模块名称：[模块名称]
 * 软件版权：Copyright (c) 2011-2018, liming20110711@163.com All Rights Reserved.
 * 功能说明：[请在此处输入功能说明]
 * 开发人员：Rushing0711
 * 创建日期：20180419 22:34
 * 修改记录：
 * <Version>        <DateSerial>        <Author>        <Description>
 * 1.0.0            20180419-01         Rushing0711     M201804192234 新建文件
 ********************************************************************************/
package com.coding.wechat.DO;

import com.coding.wechat.utils.serializer.Date2LongSerializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

/**
 * [请在此输入功能简述].
 *
 * <p>创建时间: <font style="color:#00FFFF">20180419 22:35</font><br>
 * [请在此输入功能详述]
 *
 * @since 1.0.0
 */
@Data
public class AccessToken {

    /** 获取到的凭证 . */
    @JsonProperty("access_token")
    private String accessToken;

    /** 凭证有效时间，单位：秒 */
    @JsonProperty("expires_in")
    private Integer expiresIn;
}

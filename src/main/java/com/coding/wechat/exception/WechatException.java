/*
 * 文件名称：EdenException.java
 * 系统名称：[系统名称]
 * 模块名称：[模块名称]
 * 软件版权：Copyright (c) 2011-2018, liming20110711@163.com All Rights Reserved.
 * 功能说明：[请在此处输入功能说明]
 * 开发人员：Rushing0711
 * 创建日期：20180806 09:57
 * 修改记录：
 * <Version>        <DateSerial>        <Author>        <Description>
 * 1.0.0            20180806-01         Rushing0711     M201808060957 新建文件
 ********************************************************************************/
package com.coding.wechat.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class WechatException extends RuntimeException {

    private Integer code;

    public WechatException(WechatStatus resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    public WechatException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}

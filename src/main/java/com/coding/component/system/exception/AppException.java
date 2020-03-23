/*
 * 文件名称：AuthException.java
 * 系统名称：[系统名称]
 * 模块名称：[模块名称]
 * 软件版权：Copyright (c) 2011-2018, liming20110711@163.com All Rights Reserved.
 * 功能说明：[请在此处输入功能说明]
 * 开发人员：Rushing0711
 * 创建日期：20181111 08:25
 * 修改记录：
 * <Version>        <DateSerial>        <Author>        <Description>
 * 1.0.0            20181111-01         Rushing0711     M201811110825 新建文件
 ********************************************************************************/
package com.coding.component.system.exception;

public class AppException extends RuntimeException {
    private Integer code;

    public AppException(AppStatus appStatus) {
        super(appStatus.getMessage());
        this.code = appStatus.getCode();
    }

    public AppException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}

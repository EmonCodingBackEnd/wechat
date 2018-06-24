/*
 * 文件名称：ResultSupport.java
 * 系统名称：[系统名称]
 * 模块名称：Result结果
 * 软件版权：Copyright (c) 2011-2018, liming20110711@163.com All Rights Reserved.
 * 功能说明：[请在此处输入功能说明]
 * 开发人员：Rushing0711
 * 创建日期：20180621 00:58
 * 修改记录：
 * <Version>        <DateSerial>        <Author>        <Description>
 * 1.0.0            20180621-01         Rushing0711     M201806210058 新建文件
 ********************************************************************************/
package com.coding.wechat.component.ftp.support;

import java.util.Map;

/**
 * Result结果.
 *
 * <p>创建时间: <font style="color:#00FFFF">20180621 00:59</font><br>
 * [请在此输入功能详述]
 *
 * @author Rushing0711
 * @version 1.0.0
 * @since 1.0.0
 */
public class ResultSupport {

    private static final String SUCCESS_MSG = "上传成功";
    public static final String FAILURE_MSG = "上传失败";

    public static FTPResult success(Map<String, String> urlMap) {
        FTPResult result = new FTPResult();
        result.setSuccess(true);
        result.setMsg(SUCCESS_MSG);
        result.setUrlMap(urlMap);
        return result;
    }

    public static FTPResult error(String msg) {
        FTPResult result = new FTPResult();
        result.setSuccess(false);
        result.setMsg(msg);
        return result;
    }
}

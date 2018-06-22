/*
 * 文件名称：Result.java
 * 系统名称：[系统名称]
 * 模块名称：上传结果
 * 软件版权：Copyright (c) 2011-2018, liming20110711@163.com All Rights Reserved.
 * 功能说明：[请在此处输入功能说明]
 * 开发人员：Rushing0711
 * 创建日期：20180621 00:14
 * 修改记录：
 * <Version>        <DateSerial>        <Author>        <Description>
 * 1.0.0            20180621-01         Rushing0711     M201806210014 新建文件
 ********************************************************************************/
package com.coding.wechat.component.ftp.bean;

import lombok.Data;

import java.util.Map;

/**
 * 上传结果.
 *
 * <p>创建时间: <font style="color:#00FFFF">20180621 00:14</font><br>
 * [请在此输入功能详述]
 *
 * @author Rushing0711
 * @version 1.0.0
 * @since 1.0.0
 */
@Data
public class FTPResult {

    private boolean success;

    private String msg;

    private Map<String, String> urlMap;
}

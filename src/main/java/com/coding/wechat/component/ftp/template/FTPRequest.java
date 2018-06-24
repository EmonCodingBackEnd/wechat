/*
 * 文件名称：FTPAccessor.java
 * 系统名称：[系统名称]
 * 模块名称：FTP文件请求
 * 软件版权：Copyright (c) 2011-2018, liming20110711@163.com All Rights Reserved.
 * 功能说明：[请在此处输入功能说明]
 * 开发人员：Rushing0711
 * 创建日期：20180622 22:31
 * 修改记录：
 * <Version>        <DateSerial>        <Author>        <Description>
 * 1.0.0            20180622-01         Rushing0711     M201806222231 新建文件
 ********************************************************************************/
package com.coding.wechat.component.ftp.template;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * FTP文件请求.
 *
 * <p>创建时间: <font style="color:#00FFFF">20180622 22:32</font><br>
 * [请在此输入功能详述]
 *
 * @author Rushing0711
 * @version 1.0.0
 * @since 1.0.0
 */
@Data
public class FTPRequest {

    private static final String DEFAULT_ACCESS_URL_PREFIX = "";

    private boolean upload;
    private int retryTimes;
    private boolean rename;
    private String remoteDirectory;
    private String remoteFileName;
    private String localDirectory;
    private String localFleName;
    private String localFilePath;
    private Map<String, String> fileNameMap;
    // 访问上传的文件时，url前缀，比如 http://file.emon.vip/ 或者 http://192.168.1.116:80/
    private String accessUrlPrefixes = DEFAULT_ACCESS_URL_PREFIX;
}

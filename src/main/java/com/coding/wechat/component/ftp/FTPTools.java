/*
 * 文件名称：FTPTools.java
 * 系统名称：[系统名称]
 * 模块名称：FTP工具类
 * 软件版权：Copyright (c) 2011-2018, liming20110711@163.com All Rights Reserved.
 * 功能说明：[请在此处输入功能说明]
 * 开发人员：Rushing0711
 * 创建日期：20180621 00:44
 * 修改记录：
 * <Version>        <DateSerial>        <Author>        <Description>
 * 1.0.0            20180621-01         Rushing0711     M201806210044 新建文件
 ********************************************************************************/
package com.coding.wechat.component.ftp;

import com.coding.wechat.component.ftp.result.UploadResult;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * FTPTools.
 *
 * <p>创建时间: <font style="color:#00FFFF">20180621 00:44</font><br>
 * [请在此输入功能详述]
 *
 * @author Rushing0711
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
public class FTPTools {

    public static UploadResult uploadFile(List<File> fileList) throws IOException {
        //        FTPClientSupport ftpSupport =
        // FTPClientSupport.getInstance(FTPConfig.getServer("default"));
        //        log.info("【Ftp】开始连接服务器");
        //        FTPResult result = ftpSupport.uploadFile(fileList, "test", false, false);
        //        log.info("【Ftp】开始连接FTP服务器，结束上传，上传结果:{}", result);
        //        return result;
        return null;
    }
}

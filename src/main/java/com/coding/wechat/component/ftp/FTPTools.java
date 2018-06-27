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

import com.coding.wechat.component.ftp.config.FTPConfig;
import com.coding.wechat.component.ftp.param.UploadParam;
import com.coding.wechat.component.ftp.param.UploadParamBuilder;
import com.coding.wechat.component.ftp.result.UploadResult;
import com.coding.wechat.component.ftp.template.FTPTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

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
@Component
public class FTPTools {
    private static FTPConfig ftpConfig;
    private static FTPTemplate ftpTemplate;

    @Autowired
    public FTPTools(FTPConfig ftpConfig, FTPTemplate ftpTemplate) {
        this.ftpConfig = ftpConfig;
        this.ftpTemplate = ftpTemplate;
    }

    public static UploadResult uploadFileAutoDetectDirectory(MultipartFile multipartFile) {
        UploadParam uploadParam =
                UploadParamBuilder.custom().autoDetect(true).multipartFile(multipartFile).build();
        UploadResult uploadResult =
                ftpTemplate.uploadFile(ftpConfig.getDefaultServer(), uploadParam);
        return uploadResult;
    }

    public static UploadResult uploadFileAutoDetectDirectory(File file) {
        UploadParam uploadParam = UploadParamBuilder.custom().autoDetect(true).file(file).build();
        UploadResult uploadResult =
                ftpTemplate.uploadFile(ftpConfig.getDefaultServer(), uploadParam);
        return uploadResult;
    }

    public static UploadResult uploadFileAutoDetectDirectory(String key, String content) {
        UploadParam uploadParam =
                UploadParamBuilder.custom().autoDetect(true).content(key, content).build();
        UploadResult uploadResult =
                ftpTemplate.uploadFile(ftpConfig.getDefaultServer(), uploadParam);
        return uploadResult;
    }
}

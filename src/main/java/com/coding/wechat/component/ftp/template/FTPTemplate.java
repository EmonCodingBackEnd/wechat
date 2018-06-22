/*
 * 文件名称：FTPTemplate.java
 * 系统名称：[系统名称]
 * 模块名称：FTP模板
 * 软件版权：Copyright (c) 2011-2018, liming20110711@163.com All Rights Reserved.
 * 功能说明：[请在此处输入功能说明]
 * 开发人员：Rushing0711
 * 创建日期：20180622 20:17
 * 修改记录：
 * <Version>        <DateSerial>        <Author>        <Description>
 * 1.0.0            20180622-01         Rushing0711     M201806222017 新建文件
 ********************************************************************************/
package com.coding.wechat.component.ftp.template;

import com.coding.wechat.component.ftp.FTPConfig;
import com.coding.wechat.component.ftp.bean.FTPResult;
import com.coding.wechat.component.ftp.exception.FTPException;
import org.apache.commons.net.ftp.FTPClient;

import java.util.List;

/**
 * FTP模板.
 *
 * <p>创建时间: <font style="color:#00FFFF">20180622 20:17</font><br>
 * [请在此输入功能详述]
 *
 * @author Rushing0711
 * @version 1.0.0
 * @since 1.0.0
 */
public class FTPTemplate implements FTPOperations<FTPConfig> {
    @Override
    public List<String> listFiles(FTPConfig ftpConfig, int limit) throws FTPException {
        return null;
    }

    @Override
    public FTPResult getFile(FTPConfig ftpConfig, String fileName) throws FTPException {
        return null;
    }

    @Override
    public void getFileCallback(FTPConfig ftpConfig, String fileName, FTPCallback<FTPResult> callback) throws FTPException {

    }

    @Override
    public boolean putFile(FTPConfig ftpConfig, String content, String fileName) throws FTPException {
        return false;
    }

    @Override
    public boolean deleteFile(FTPConfig ftpConfig, String fileName) throws FTPException {
        return false;
    }
}

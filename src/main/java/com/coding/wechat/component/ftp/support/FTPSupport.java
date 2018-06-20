/*
 * 文件名称：FTPSupport.java
 * 系统名称：[系统名称]
 * 模块名称：FTP辅助类
 * 软件版权：Copyright (c) 2011-2018, liming20110711@163.com All Rights Reserved.
 * 功能说明：[请在此处输入功能说明]
 * 开发人员：Rushing0711
 * 创建日期：20180621 00:44
 * 修改记录：
 * <Version>        <DateSerial>        <Author>        <Description>
 * 1.0.0            20180621-01         Rushing0711     M201806210044 新建文件
 ********************************************************************************/
package com.coding.wechat.component.ftp.support;

import com.coding.wechat.component.ftp.FtpConfig;
import com.coding.wechat.component.ftp.bean.Result;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * FTP辅助类.
 *
 * <p>创建时间: <font style="color:#00FFFF">20180621 00:44</font><br>
 * [请在此输入功能详述]
 *
 * @author Rushing0711
 * @version 1.0.0
 * @since 1.0.0
 */
@Data
@Slf4j
public class FTPSupport {

    public FTPSupport(FtpConfig.FtpServer ftpServer) {
        this.ftpServer = ftpServer;
    }

    public Result uploadFile(
            List<File> fileList, String remotePath, boolean autoMkdir, boolean uniqueFile)
            throws IOException {
        FileInputStream fis = null;
        // 连接FTP服务器
        if (connectServer()) {
            try {
                ftpClient.changeWorkingDirectory(remotePath);
                ftpClient.setBufferSize(1024);
                ftpClient.setControlEncoding(StandardCharsets.UTF_8.name());
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
                ftpClient.enterLocalPassiveMode();
                for (File fileItem : fileList) {
                    fis = new FileInputStream(fileItem);
                    ftpClient.storeFile(fileItem.getName(), fis);
                }
                return ResultSupport.success(null);
            } catch (IOException e) {
                log.error("【Ftp】上传文件异常", e);
                return ResultSupport.error(e.getMessage());
            } finally {
                if (fis != null) {
                    fis.close();
                }
                if (ftpClient != null) {
                    ftpClient.disconnect();
                }
            }
        } else {
            return ResultSupport.error(
                    String.format("%s:%s", ResultSupport.FAILURE_MSG, "连接服务器失败"));
        }
    }

    private boolean connectServer() {
        boolean isSuccess = false;
        ftpClient = new FTPClient();
        try {
            ftpClient.connect(ftpServer.getHost(), ftpServer.getPort());
            isSuccess = ftpClient.login(ftpServer.getUsername(), ftpServer.getPassword());
        } catch (IOException e) {
            log.error("【Ftp】连接服务器异常", e);
        }
        return isSuccess;
    }

    private FTPClient ftpClient;

    private FtpConfig.FtpServer ftpServer;
}

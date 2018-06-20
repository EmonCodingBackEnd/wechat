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

    public static Result uploadFile(List<File> fileList) throws IOException {
        FTPSupport ftpSupport = new FTPSupport(FtpConfig.getServer());
        log.info("【Ftp】开始连接服务器");
        Result result = ftpSupport.uploadFile(fileList, "audio", false, false);
        log.info("【Ftp】开始连接FTP服务器，结束上传，上传结果:{}", result);
        return result;
    }

    public Result uploadFile(
            List<File> fileList, String remotePath, boolean autoMkdir, boolean uniqueFile)
            throws IOException {
        FileInputStream fis = null;
        // 连接FTP服务器
        if (connectServer(
                this.ftpServer.getHost(),
                this.ftpServer.getPort(),
                this.ftpServer.getUsername(),
                this.ftpServer.getPassword())) {
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

            } catch (IOException e) {
                log.error("【Ftp】上传文件异常", e);
                ResultSupport.error(e.getMessage());
            } finally {
                if (fis != null) {
                    fis.close();
                }
                if (ftpClient != null) {
                    ftpClient.disconnect();
                }
            }
        }
        return ResultSupport.error(String.format("%s:%s", ResultSupport.FAILURE_MSG, "连接服务器失败"));
    }

    public boolean connectServer(String ip, int port, String user, String pwd) {
        boolean isSuccess = false;
        ftpClient = new FTPClient();
        try {
            ftpClient.connect(ip, port);
            isSuccess = ftpClient.login(user, pwd);
        } catch (IOException e) {
            log.error("【Ftp】连接服务器异常", e);
        }
        return isSuccess;
    }

    private FTPClient ftpClient;

    private FtpConfig.FtpServer ftpServer;
}

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

import com.coding.wechat.component.ftp.config.ServerConfig;
import com.coding.wechat.component.ftp.exception.FTPException;
import com.coding.wechat.component.ftp.param.FTPParam;
import com.coding.wechat.component.ftp.param.ParamType;
import com.coding.wechat.component.ftp.pool.GenericKeyedFTPClientPool;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.StringTokenizer;

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
@Slf4j
@Component
public class FTPTemplate implements FTPOperations {

    private final GenericKeyedFTPClientPool ftpClientPool;

    @Autowired
    public FTPTemplate(GenericKeyedFTPClientPool ftpClientPool) {
        this.ftpClientPool = ftpClientPool;
    }

    private FTPClient getFTPClient(ServerConfig serverConfig) {
        FTPClient ftpClient;
        try {
            ftpClient = ftpClientPool.borrowObject(serverConfig);
        } catch (Exception e) {
            log.error("【FTP】获取FTPClient实例异常", e);
            throw new FTPException(e);
        }
        return ftpClient;
    }

    private String getInfo(ServerConfig serverConfig, FTPParam ftpParam) {
        StringBuffer sb = new StringBuffer();
        sb.append(serverConfig.getAlias()).append(ftpParam.getRemoteDirectory());
        return sb.toString();
    }

    private boolean changeWorkingDirectory(FTPClient ftpClient, FTPParam ftpParam) {
        boolean success;
        try {
            if (StringUtils.isEmpty(ftpParam.getRemoteDirectory())) {
                success = ftpClient.changeWorkingDirectory(ftpParam.getRemoteDirectory());
                if (!success) {
                    StringTokenizer token =
                            new StringTokenizer(ftpParam.getRemoteDirectory(), "\\//");
                    while (token.hasMoreElements()) {
                        String directory = token.nextToken();
                        boolean mkdirSuccess = ftpClient.makeDirectory(directory);
                        boolean changeSuccess = ftpClient.changeWorkingDirectory(directory);
                        if (!mkdirSuccess || !changeSuccess) {
                            success = false;
                            break;
                        }
                    }
                }
            } else {
                success = ftpClient.changeToParentDirectory();
            }
            return success;
        } catch (IOException e) {
            log.error("【FTP】切换远程目录异常", e);
            throw new FTPException(e);
        }
    }

    private InputStream getInputStream(FTPParam ftpParam) {
        if (ftpParam.getInputStream() != null) {
            return ftpParam.getInputStream();
        }
        if (!StringUtils.isEmpty(ftpParam.getLocalFilePath())) {
            InputStream inputStream = new FileInputStream(ftpParam.getLocalFilePath());
        }
    }

    @Override
    public FTPResponse putFile(ServerConfig serverConfig, FTPParam ftpParam) throws FTPException {
        if (ftpParam.getParamType().compareTo(ParamType.Upload) != 0) {
            throw new FTPException(
                    String.format("【FTP】方法 putFile 不支持请求类型 %s", ftpParam.getParamType()));
        }
        if (log.isDebugEnabled()) {
            log.debug("【FTP】开始上传文件到 {}", getInfo(serverConfig, ftpParam));
        }
        FTPClient ftpClient = getFTPClient(serverConfig);
        boolean success = changeWorkingDirectory(ftpClient, ftpParam);
        if (success) {

        }
        return null;
    }

    @Override
    public List<String> listFiles(ServerConfig serverConfig, FTPParam ftpParam)
            throws FTPException {
        return null;
    }

    @Override
    public <T> T getFile(ServerConfig serverConfig, FTPParam ftpParam) throws FTPException {
        return null;
    }

    @Override
    public <T> T getFile(ServerConfig serverConfig, FTPCallback<T> callback) throws FTPException {
        return null;
    }

    @Override
    public void deleteFile(ServerConfig serverConfig, FTPParam ftpParam) throws FTPException {}

    @Override
    public boolean mkdir(ServerConfig serverConfig, String remoteDirectory) throws FTPException {
        return false;
    }
}

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
import com.coding.wechat.component.ftp.param.*;
import com.coding.wechat.component.ftp.pool.GenericKeyedFTPClientPool;
import com.coding.wechat.component.ftp.result.ResultItem;
import com.coding.wechat.component.ftp.result.UploadResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.xml.transform.Result;
import java.io.*;
import java.util.*;

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

    private boolean changeWorkingDirectory(FTPClient ftpClient, BaseParam ftpParam) {
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

    /*private void putFile(FTPClient ftpClient, ResultItem ftpResult, String key, File fileItem) {
        FileInputStream fis = null;
        String originalFileName;
        String virtualFileName;
        UploadResult uploadResult;
        try {
            fis = new FileInputStream(fileItem);
            originalFileName = fileItem.getName();
            virtualFileName =
                    UUID.randomUUID().toString().replace("-", "")
                            + originalFileName.substring(originalFileName.indexOf("."));
            boolean storeSucess = ftpClient.storeFile(virtualFileName, fis);
            if (storeSucess) {
                uploadResult = UploadResult.newSuccess();
                uploadResult.setOriginalFileName(originalFileName);
                uploadResult.setVirtualFileName(virtualFileName);
                ftpResult.getSuccessResultMap().put(key, uploadResult);
                log.info("【FTP】文件[{}]上传成功", originalFileName);
            } else {
                uploadResult = UploadResult.newFailure();
                ftpResult.getFailureResultMap().put(key, uploadResult);
                log.error(String.format("【FTP】文件[%s]上传失败", originalFileName));
            }
        } catch (IOException e) {
            uploadResult = UploadResult.newFailure();
            uploadResult.setErrorMessage(e.getMessage());
            ftpResult.getFailureResultMap().put(key, uploadResult);
            log.error("【FTP】文件上传失败", e);
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    log.error("【FTP】文件流关闭失败", e);
                }
            }
        }
    }*/

    /*@Override
    public FTPResult putFile(ServerConfig serverConfig, UploadParam uploadParam)
            throws FTPException {
        FTPResult ftpResult = new FTPResult();
        ftpResult.setServerConfig(serverConfig);
        ftpResult.setUploadParam(uploadParam);
        String info = getInfo(serverConfig, uploadParam);
        if (log.isDebugEnabled()) {
            log.debug("【FTP】开始上传文件到 {}", info);
        }
        FTPClient ftpClient = getFTPClient(serverConfig);

        boolean success = changeWorkingDirectory(ftpClient, uploadParam);
        if (success) {
            for (Map.Entry<String, File> entry : uploadParam.getFileMap().entrySet()) {
                putFile(ftpClient, ftpResult, entry.getKey(), entry.getValue());
            }
            for (Map.Entry<String, String> entry : uploadParam.getContentMap().entrySet()) {
                putContent(ftpClient, ftpResult, entry.getKey(), entry.getValue());
            }
            for (Map.Entry<String, MultipartFile> entry :
                    uploadParam.getMultipartFileMap().entrySet()) {
                putMultipartFile(ftpClient, ftpResult, entry.getKey(), entry.getValue());
            }
        } else {
            ftpResult.setHasFailure(true);
        }
        ftpClientPool.returnObject(serverConfig, ftpClient);
        return ftpResult;
    }*/

    @Override
    public UploadResult uploadFile(ServerConfig serverConfig, UploadParam uploadParam)
            throws FTPException {
        return null;
    }

    @Override
    public List<String> listFiles(ServerConfig serverConfig, ListParam listParam)
            throws FTPException {
        return null;
    }

    @Override
    public void downloadFile(ServerConfig serverConfig, DownloadParam downloadParam)
            throws FTPException {}

    @Override
    public <T> T downloadFile(ServerConfig serverConfig, FTPCallback<T> callback)
            throws FTPException {
        return null;
    }

    @Override
    public void deleteFile(ServerConfig serverConfig, DeleteParam deleteParam)
            throws FTPException {}
}

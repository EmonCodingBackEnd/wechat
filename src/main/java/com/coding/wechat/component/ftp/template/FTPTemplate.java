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
import com.coding.wechat.component.ftp.param.DeleteParam;
import com.coding.wechat.component.ftp.param.DownloadParam;
import com.coding.wechat.component.ftp.param.ListParam;
import com.coding.wechat.component.ftp.param.UploadParam;
import com.coding.wechat.component.ftp.pool.GenericKeyedFTPClientPool;
import com.coding.wechat.component.ftp.result.ResultItem;
import com.coding.wechat.component.ftp.result.UploadResult;
import com.coding.wechat.component.regex.RegexSupport;
import com.coding.wechat.component.regex.result.FilenameResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.UUID;

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

    private boolean changeWorkingDirectory(FTPClient ftpClient, String remoteDirectory) {
        boolean success;
        try {
            if (StringUtils.isEmpty(remoteDirectory)) {
                success = ftpClient.changeWorkingDirectory(remoteDirectory);
                if (!success) {
                    StringTokenizer token = new StringTokenizer(remoteDirectory, "\\//");
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

    private String getVirtualFilename(String originalFilename) {
        FilenameResult regexResult = RegexSupport.matchStrictFilename(originalFilename);
        if (!regexResult.isMatched()) {
            log.error("【FTP】源文件名 {} 不合法", originalFilename);
            throw new FTPException("【FTP】源文件名不合法");
        }
        StringBuilder buffer = new StringBuilder();
        buffer.append(UUID.randomUUID().toString().replace("-", ""));
        if (regexResult.isHasSuffix()) {
            buffer.append(".").append(regexResult.getSuffix());
        }
        return buffer.toString();
    }

    private ResultItem uploadFile(
            FTPClient ftpClient,
            ServerConfig serverConfig,
            String remoteDirectory,
            String originalFilename,
            InputStream inputStream) {
        Assert.notNull(inputStream, "inputStream must be not null");
        ResultItem resultItem;
        try {
            if (changeWorkingDirectory(ftpClient, remoteDirectory)) {
                String virtualFilename = getVirtualFilename(originalFilename);
                boolean success = ftpClient.storeFile(virtualFilename, inputStream);
                if (success) {
                    resultItem = ResultItem.newSuccess();
                    resultItem.setOriginalFilename(originalFilename);
                    resultItem.setVirtualFilename(virtualFilename);
                    String url =
                            serverConfig.getAccessUrlPrefixes() + File.separator + virtualFilename;
                    resultItem.setUrl(url);
                } else {
                    resultItem = ResultItem.newFailure();
                }
            } else {
                resultItem = ResultItem.newFailure();
            }
        } catch (IOException e) {
            log.error("【FTP】上传文件失败", e);
            resultItem = ResultItem.newFailure();
            resultItem.setErrorMessage(e.getMessage());
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                log.error("【FTP】文件流关闭失败", e);
            }
        }
        return resultItem;
    }

    public static final String IMAGE_DIRECTORY = "images";
    public static final String AUDIO_DIRECTORY = "audios";
    public static final String VEDIO_DIRECTORY = "vedios";

    private String getRemoteDirectory(String originalFilename, UploadParam uploadParam) {
        String remoteDirectory;
        if (uploadParam.isAutoDetect()) {
            if (RegexSupport.matchImage(originalFilename).isMatched()) {
                remoteDirectory = IMAGE_DIRECTORY;
            } else if (RegexSupport.matchImage(originalFilename).isMatched()) {
                remoteDirectory = AUDIO_DIRECTORY;

            } else if (RegexSupport.matchImage(originalFilename).isMatched()) {
                remoteDirectory = VEDIO_DIRECTORY;
            } else {
                remoteDirectory = null;
            }
        } else {
            remoteDirectory = uploadParam.getRemoteDirectory();
        }
        return remoteDirectory;
    }

    @Override
    public UploadResult uploadFile(ServerConfig serverConfig, UploadParam uploadParam)
            throws FTPException {
        UploadResult uploadResult = new UploadResult();
        uploadResult.setServerConfig(serverConfig);
        uploadResult.setUploadParam(uploadParam);
        if (log.isDebugEnabled()) {
            log.debug("【FTP】开始上传文件到 {}", serverConfig.getHost());
        }
        FTPClient ftpClient = getFTPClient(serverConfig);

        for (Map.Entry<String, InputStream> entry : uploadParam.getInputStreamMap().entrySet()) {
            String remoteDirectory = getRemoteDirectory(entry.getKey(), uploadParam);
            ResultItem resultItem =
                    uploadFile(
                            ftpClient,
                            serverConfig,
                            remoteDirectory,
                            entry.getKey(),
                            entry.getValue());
            uploadResult.addResultItem(resultItem);
        }
        for (Map.Entry<String, String> entry : uploadParam.getContentMap().entrySet()) {
            String remoteDirectory = getRemoteDirectory(entry.getKey(), uploadParam);
            InputStream inputStream =
                    new BufferedInputStream(
                            new ByteArrayInputStream(
                                    entry.getValue().getBytes(ftpClient.getCharset())));
            ResultItem resultItem =
                    uploadFile(
                            ftpClient, serverConfig, remoteDirectory, entry.getKey(), inputStream);
            uploadResult.addResultItem(resultItem);
        }
        for (MultipartFile multipartFile : uploadParam.getMultipartFileList()) {
            String remoteDirectory =
                    getRemoteDirectory(multipartFile.getOriginalFilename(), uploadParam);
            ResultItem resultItem;
            try {
                resultItem =
                        uploadFile(
                                ftpClient,
                                serverConfig,
                                remoteDirectory,
                                multipartFile.getOriginalFilename(),
                                multipartFile.getInputStream());
            } catch (IOException e) {
                resultItem = ResultItem.newFailure();
                log.error("【FTP】multipartFile文件上传获取输入流失败", e);
            }
            uploadResult.addResultItem(resultItem);
        }
        for (File fileItem : uploadParam.getFileList()) {
            String remoteDirectory = getRemoteDirectory(fileItem.getName(), uploadParam);
            ResultItem resultItem;
            try {
                FileInputStream inputStream = new FileInputStream(fileItem);
                resultItem =
                        uploadFile(
                                ftpClient,
                                serverConfig,
                                remoteDirectory,
                                fileItem.getName(),
                                inputStream);
            } catch (FileNotFoundException e) {
                resultItem = ResultItem.newFailure();
                log.error("【FTP】fileItem文件上传获取输入流失败", e);
            }
            uploadResult.addResultItem(resultItem);
        }
        return uploadResult;
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

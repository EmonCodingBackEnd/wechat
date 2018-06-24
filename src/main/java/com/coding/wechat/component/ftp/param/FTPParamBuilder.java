package com.coding.wechat.component.ftp.param;

import org.springframework.util.Assert;

import java.io.InputStream;
import java.util.Map;

public class FTPParamBuilder {

    private FTPParam ftpParam;

    private FTPParamBuilder() {
        ftpParam = new FTPParam();
    }

    public static FTPParamBuilder custom() {
        return new FTPParamBuilder();
    }

    public FTPParamBuilder remoteDirectory(String remoteDirectory) {
        Assert.notNull(remoteDirectory, "remoteDirectory must be not null");
        this.ftpParam.setRemoteDirectory(remoteDirectory);
        return this;
    }

    public FTPParamBuilder remoteFileName(String remoteFileName) {
        Assert.notNull(remoteFileName, "remoteFileName must be not null");
        this.ftpParam.setRemoteFileName(remoteFileName);
        return this;
    }

    public FTPParamBuilder localDirectory(String localDirectory) {
        Assert.notNull(localDirectory, "localDirectory must be not null");
        this.ftpParam.setLocalDirectory(localDirectory);
        return this;
    }

    public FTPParamBuilder localFleName(String localFleName) {
        Assert.notNull(localFleName, "localFleName must be not null");
        this.ftpParam.setLocalFleName(localFleName);
        return this;
    }

    public FTPParamBuilder localFilePath(String localFilePath) {
        Assert.notNull(localFilePath, "localFilePath must be not null");
        this.ftpParam.setLocalFilePath(localFilePath);
        return this;
    }

    public FTPParamBuilder fileNameMap(Map<String, String> fileNameMap) {
        Assert.notNull(fileNameMap, "fileNameMap must be not null");
        this.ftpParam.setFileNameMap(fileNameMap);
        return this;
    }

    public FTPParamBuilder accessUrlPrefixes(String accessUrlPrefixes) {
        Assert.notNull(accessUrlPrefixes, "accessUrlPrefixes must be not null");
        this.ftpParam.setAccessUrlPrefixes(accessUrlPrefixes);
        return this;
    }

    public FTPParamBuilder limit(int limit) {
        this.ftpParam.setLimit(limit);
        return this;
    }

    public FTPParamBuilder inputStream(InputStream inputStream) {
        Assert.notNull(inputStream, "inputStream must be not null");
        this.ftpParam.setInputStream(inputStream);
        return this;
    }

    public FTPParam buildUp() {
        ftpParam.setParamType(ParamType.Upload);
        return ftpParam;
    }

    public FTPParam buildDown() {
        ftpParam.setParamType(ParamType.Download);
        return ftpParam;
    }

    public FTPParam buildDelete() {
        ftpParam.setParamType(ParamType.DeleteFile);
        return ftpParam;
    }

    public FTPParam buildMkdir() {
        ftpParam.setParamType(ParamType.MkDir);
        return ftpParam;
    }
}

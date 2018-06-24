package com.coding.wechat.component.ftp.param;

import lombok.Data;

import java.io.InputStream;
import java.util.Map;

@Data
public class FTPParam {

    private static final String DEFAULT_ACCESS_URL_PREFIX = "";
    private static final String DEFAULT_REMOTE_DIRECTORY = "/";

    private ParamType paramType = null;
    private boolean upload;
    private int retryTimes;
    private boolean autoRename = true;
    private String remoteDirectory = DEFAULT_REMOTE_DIRECTORY;
    private String remoteFileName;
    private String localDirectory;
    private String localFleName;
    private String localFilePath;
    private int limit;
    private InputStream inputStream;
    private Map<String, String> fileNameMap;
    // 访问上传的文件时，url前缀，比如 http://file.emon.vip/ 或者 http://192.168.1.116:80/
    private String accessUrlPrefixes = DEFAULT_ACCESS_URL_PREFIX;
}

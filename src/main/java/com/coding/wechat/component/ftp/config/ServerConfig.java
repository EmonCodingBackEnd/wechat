package com.coding.wechat.component.ftp.config;

import lombok.Data;
import org.apache.commons.net.ftp.FTPClient;

import java.nio.charset.StandardCharsets;

@Data
public class ServerConfig {

    private static final int DEFAULT_POST = 21;

    // 如果不配置，默认采用host作为别名
    private String alias;

    private String host;

    private int port = DEFAULT_POST;

    private String username;

    private String password;

    // FTP服务器被动模式
    private String passiveMode;

    private String encoding = StandardCharsets.UTF_8.name();

    private int connectTimeout = 5000;

    private int dataTimeout = 3000;

    private int transferFileType = FTPClient.BINARY_FILE_TYPE;

}

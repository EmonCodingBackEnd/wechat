/*
 * 文件名称：FtpConfig.java
 * 系统名称：[系统名称]
 * 模块名称：FTP配置文件
 * 软件版权：Copyright (c) 2011-2018, liming20110711@163.com All Rights Reserved.
 * 功能说明：[请在此处输入功能说明]
 * 开发人员：Rushing0711
 * 创建日期：20180620 22:35
 * 修改记录：
 * <Version>        <DateSerial>        <Author>        <Description>
 * 1.0.0            20180620-01         Rushing0711     M201806202235 新建文件
 ********************************************************************************/
package com.coding.wechat.component.ftp.config;

import com.coding.wechat.component.ftp.pool.GenericKeyedFTPClientPool;
import com.coding.wechat.component.ftp.pool.PooledFTPClientFactory;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.pool2.impl.GenericKeyedObjectPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * FTP配置文件.
 *
 * <p>创建时间: <font style="color:#00FFFF">20180620 22:35</font><br>
 * [请在此输入功能详述]
 *
 * @author Rushing0711
 * @version 1.0.0
 * @since 1.0.0
 */
@Data
@Configuration
@ConditionalOnClass({GenericKeyedObjectPool.class, FTPClient.class})
@ConditionalOnProperty(value = "ftp.enabled", havingValue = "true")
@ConfigurationProperties(prefix = "ftp")
@Slf4j
public class FTPConfig {

    private final FTPPoolConfig ftpPoolConfig;

    // 可以配置多个FTP服务器
    private List<ServerConfig> servers;

    @Autowired
    public FTPConfig(FTPPoolConfig ftpPoolConfig) {
        this.ftpPoolConfig = ftpPoolConfig;
    }

    /*@Data
    public static class ServerConfig {

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
    }*/

    @PostConstruct
    public void init() {
        log.info("【FTP连接参数配置】{}", servers);
        for (ServerConfig server : servers) {
            if (StringUtils.isEmpty(server.getAlias())) {
                server.setAlias(server.getHost());
            }
        }
    }

    /**
     * 根据alias或者host查找对应FTP服务器连接配置，如果查不到，返回null.
     *
     * <p>创建时间: <font style="color:#00FFFF">20180621 11:28</font><br>
     * [请在此输入功能详述]
     *
     * @param aliasOrHost - 配置的alias或者host（如果没有配置alias）
     * @return com.coding.wechat.component.ftp.config.FTPConfig.ServerConfig
     * @author Rushing0711
     * @since 1.0.0
     */
    public ServerConfig getServer(String aliasOrHost) {
        Assert.notNull(aliasOrHost, "aliasOrHost不能为空");
        for (ServerConfig server : servers) {
            if (aliasOrHost.equals(server.getAlias())) {
                return server;
            }
        }
        return null;
    }

    @Bean
    public PooledFTPClientFactory ftpClientFactory() {
        return new PooledFTPClientFactory();
    }

    @Bean
    public GenericKeyedFTPClientPool ftpClientPool() {
        return new GenericKeyedFTPClientPool(ftpClientFactory(), ftpPoolConfig);
    }
}

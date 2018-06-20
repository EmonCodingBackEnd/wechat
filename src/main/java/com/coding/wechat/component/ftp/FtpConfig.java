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
package com.coding.wechat.component.ftp;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
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
@Component
@ConfigurationProperties(prefix = "ftp")
public class FtpConfig {

    private static final String DEFAULT_ALIAS = "default";

    // 可以配置多个FTP服务器
    private static List<FtpServer> servers;

    @Data
    public static class FtpServer {
        private String alias;

        private String host;

        private int port = 21;

        private String username;

        private String password;

        // 访问上传的文件时，url前缀，比如 http://file.emon.vip/ 或者 http://192.168.1.116:80/
        private String accessUrlPrefixes;
    }

    @PostConstruct
    public void init() {
        for (FtpServer server : servers) {
            if (StringUtils.isEmpty(server.getAlias())) {
                server.setAlias(server.getHost());
            }
            if (StringUtils.isEmpty(server.getAccessUrlPrefixes())) {
                server.setAccessUrlPrefixes("");
            }
        }
    }

    public static FtpServer getServer() {
        return getServer(DEFAULT_ALIAS);
    }

    public static FtpServer getServer(String alias) {
        Assert.notNull(alias, "alias不能为空");

        FtpServer result = null;
        for (FtpServer server : servers) {
            if (alias.equals(server.getAlias())) {
                result = server;
                break;
            }
        }
        if (result == null) {
            result = servers.get(0);
        }
        return result;
    }
}

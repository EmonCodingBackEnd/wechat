/*
 * 文件名称：JwtTokenUtil.java
 * 系统名称：[系统名称]
 * 模块名称：[模块名称]
 * 软件版权：Copyright (c) 2011-2018, liming20110711@163.com All Rights Reserved.
 * 功能说明：[请在此处输入功能说明]
 * 开发人员：Rushing0711
 * 创建日期：20181104 08:39
 * 修改记录：
 * <Version>        <DateSerial>        <Author>        <Description>
 * 1.0.0            20181104-01         Rushing0711     M201811040839 新建文件
 ********************************************************************************/
package com.coding.component.security.jwt;

import com.coding.component.security.CustomUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class JwtTokenUtil implements Serializable {
    private static final long serialVersionUID = -1171596647511353722L;

    private static InputStream inputStream =
            Thread.currentThread()
                    .getContextClassLoader()
                    .getResourceAsStream("static/jwt/jwt.jks");
    private static PrivateKey privateKey;
    private static PublicKey publicKey;

    static {
        try {
            KeyStore keyStore = KeyStore.getInstance("JKS");
            keyStore.load(inputStream, "ss541018".toCharArray());
            privateKey = (PrivateKey) keyStore.getKey("jwt", "ss541018".toCharArray());
            publicKey = keyStore.getCertificate("jwt").getPublicKey();
        } catch (KeyStoreException
                | IOException
                | NoSuchAlgorithmException
                | CertificateException
                | UnrecoverableKeyException e) {
            log.error("【JWT】加载密钥库失败", e);
        }
    }

    /** Token在HTTP请求头中存在的具体属性. */
    public static final String TOKEN_HEADER = "Authorization";

    /** Token值前缀. */
    public static final String TOKEN_PREFIX = "Bearer ";

    private static final String CLAIM_KEY_USERNAME = "sub";
    private static final String CLAIM_KEY_CREATED = "created";

    /** 默认2小时过期. */
    public static Long expiration;

    @Value("${jwt.expiration:7200}")
    public void setExpiration(Long expiration) {
        JwtTokenUtil.expiration = expiration;
    }

    public String getUsernameFromToken(String token) {
        String username;
        try {
            final Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    public Date getCreatedDateFromToken(String token) {
        Date created;
        try {
            final Claims claims = getClaimsFromToken(token);
            created = new Date((Long) claims.get(CLAIM_KEY_CREATED));
        } catch (Exception e) {
            created = null;
        }
        return created;
    }

    public Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            final Claims claims = getClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }

    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }

    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.RS256, privateKey)
                .compact();
    }

    public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
        final Date created = getCreatedDateFromToken(token);
        return !isCreatedBeforeLastPasswordReset(created, lastPasswordReset)
                && !isTokenExpired(token);
    }

    public String refreshToken(String token) {
        String refreshedToken;
        try {
            final Claims claims = getClaimsFromToken(token);
            claims.put(CLAIM_KEY_CREATED, new Date());
            refreshedToken = generateToken(claims);
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        CustomUserDetails user = (CustomUserDetails) userDetails;
        final String username = getUsernameFromToken(token);
        final Date created = getCreatedDateFromToken(token);
        return (username.equals(user.getUsername())
                && !isTokenExpired(token)
                && !isCreatedBeforeLastPasswordReset(created, user.getLastPasswordResetDate()));
    }
}

/*
使用JDK自带的keytool工具生成非对称加密的token。
keytool -genkey -alias jwt -keyalg  RSA -keysize 1024 -validity 3650 -keystore jwt.jks
【命令解释】：
jwt     秘钥别名
RSA     算法
3650    有效期
jwt.jks 文件名
【命令执行概述】：
输入密钥库口令：				    ss541018
再次输入新口令：				    ss541018
您的名字与姓氏是什么？				[忽略]
您的组织单位名称是什么？			    [忽略]
您的组织名称是什么？				[忽略]
您所在的城市或区域名称是什么？		[忽略]
您所在的省/市自治区名称是什么？		[忽略]
该单位的双字母国家/地区代码是什么？	[忽略]
CN=Unknown，OU=Unknown，O=Unknown，L=Unknown，ST=Unknown，C=Unknown是否正确？
[否]：y
输入<jwt>的密钥口令
    （如果和秘钥库口令相同，按回车）：[回车]
 */

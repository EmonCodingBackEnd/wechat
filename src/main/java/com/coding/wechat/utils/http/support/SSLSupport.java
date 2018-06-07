/*
 * 文件名称：SSLSupport.java
 * 系统名称：[系统名称]
 * 模块名称：SSL生成器
 * 软件版权：Copyright (c) 2011-2018, liming20110711@163.com All Rights Reserved.
 * 功能说明：[请在此处输入功能说明]
 * 开发人员：Rushing0711
 * 创建日期：20180607 19:38
 * 修改记录：
 * <Version>        <DateSerial>        <Author>        <Description>
 * 1.0.0            20180607-01         Rushing0711     M201806071938 新建文件
 ********************************************************************************/
package com.coding.wechat.utils.http.support;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.ssl.SSLContexts;

import javax.net.ssl.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * SSL生成器.
 *
 * <p>创建时间: <font style="color:#00FFFF">20180607 19:39</font><br>
 * [请在此输入功能详述]
 *
 * @author Rushing0711
 * @version 1.0.0
 * @since 1.0.0
 */
public class SSLSupport {
    private static final SSLHandler simpleVerifier = new SSLHandler();

    private static SSLConnectionSocketFactory sslConnectionSocketFactory;

    private static SSLSupport ssl = new SSLSupport();

    private SSLContext sslContext;

    private SSLSupport() {}

    public static SSLSupport getInstance() {
        return ssl;
    }

    public static SSLSupport custom() {
        return new SSLSupport();
    }

    private static class SSLHandler implements X509TrustManager, HostnameVerifier {
        @Override
        public boolean verify(String s, SSLSession sslSession) {
            return true;
        }

        @Override
        public void checkClientTrusted(X509Certificate[] x509Certificates, String s)
                throws CertificateException {}

        @Override
        public void checkServerTrusted(X509Certificate[] x509Certificates, String s)
                throws CertificateException {}

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    }

    public static HostnameVerifier getVerifier() {
        return simpleVerifier;
    }

    public synchronized SSLConnectionSocketFactory getSslConnectionSocketFactory()
            throws KeyManagementException {
        if (sslConnectionSocketFactory != null) {
            return sslConnectionSocketFactory;
        }
        SSLContext sslContext = getSslContext();
        sslContext.init(null, new TrustManager[] {simpleVerifier}, null);
        sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext, simpleVerifier);
        return sslConnectionSocketFactory;
    }

    public SSLSupport customSSL(String keyStorePath, String keyStorepass)
            throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException,
                    KeyManagementException {
        FileInputStream fileInputStream = null;
        KeyStore keyStore = null;

        keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        fileInputStream = new FileInputStream(new File(keyStorePath));
        keyStore.load(fileInputStream, keyStorepass.toCharArray());
        sslContext =
                SSLContexts.custom()
                        .loadTrustMaterial(keyStore, new TrustSelfSignedStrategy())
                        .build();

        fileInputStream.close();
        return this;
    }

    public SSLContext getSslContext() {
        if (sslContext == null) {
            sslContext = SSLContexts.createSystemDefault();
        }
        return sslContext;
    }
}

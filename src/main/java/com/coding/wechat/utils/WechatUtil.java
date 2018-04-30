/*
 * 文件名称：WechatUtil.java
 * 系统名称：[系统名称]
 * 模块名称：[模块名称]
 * 软件版权：Copyright (c) 2011-2018, liming20110711@163.com All Rights Reserved.
 * 功能说明：[请在此处输入功能说明]
 * 开发人员：Rushing0711
 * 创建日期：20180416 23:21
 * 修改记录：
 * <Version>        <DateSerial>        <Author>        <Description>
 * 1.0.0            20180416-01         Rushing0711     M201804162321 新建文件
 ********************************************************************************/
package com.coding.wechat.utils;

import com.coding.wechat.constants.WechatConsts;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * [请在此输入功能简述].
 *
 * <p>创建时间: <font style="color:#00FFFF">20180416 23:21</font><br>
 * [请在此输入功能详述]
 *
 * @author Rushing0711
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
public class WechatUtil {

    private static CloseableHttpClient httpClient;

    static {
        RequestConfig config =
                RequestConfig.custom().setConnectTimeout(60000).setSocketTimeout(15000).build();
        httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
    }

    /**
     * get请求.
     *
     * <p>创建时间: <font style="color:#00FFFF">20180420 00:14</font><br>
     * [请在此输入功能详述]
     *
     * @param url -
     * @return net.sf.json.JSONObject
     * @author Rushing0711
     * @since 1.0.0
     */
    public static JSONObject doGetStr(String url) {
        HttpGet httpGet = new HttpGet(url);
        JSONObject jsonObject = null;
        try {
            HttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result = EntityUtils.toString(entity);
                jsonObject = JSONObject.fromObject(result);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
        return jsonObject;
    }

    /**
     * post请求.
     *
     * <p>创建时间: <font style="color:#00FFFF">20180420 00:15</font><br>
     * [请在此输入功能详述]
     *
     * @param url -
     * @param outStr -
     * @return net.sf.json.JSONObject
     * @author Rushing0711
     * @since 1.0.0
     */
    public static JSONObject doPostStr(String url, String outStr) {
        HttpPost httpPost = new HttpPost(url);
        JSONObject jsonObject = null;
        try {
            httpPost.setEntity(new StringEntity(outStr, "UTF-8"));
            HttpResponse response = httpClient.execute(httpPost);
            String result = EntityUtils.toString(response.getEntity(), "UTF-8");
            jsonObject = JSONObject.fromObject(result);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
        return jsonObject;
    }

    /**
     * 新增临时素材.
     *
     * <p>创建时间: <font style="color:#00FFFF">20180430 13:17</font><br>
     * 可以通过使用curl命令，用form表单方式模拟上传一个多媒体文件：<br>
     * <code>
     *     curl -F media=@test.jpg "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE"
     * </code> <br>
     * 备注：需要替换地址中的ACCESS_TOKEN和TYPE为具体的值。
     *
     * @param filePath -
     * @param uploadUrl -
     * @return java.lang.String
     * @author Rushing0711
     * @since 1.0.0
     */
    public static String upload(String filePath, String uploadUrl) throws IOException {
        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            throw new IOException("文件不存在");
        }

        URL url = new URL(uploadUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setUseCaches(false);

        // 设置请求头信息
        connection.setRequestProperty("Connection", "Keep-Alive");
        connection.setRequestProperty("Charset", "UTF-8");

        // 设置边界
        String BOUNDARY = "----------" + System.currentTimeMillis();
        connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + BOUNDARY);

        StringBuilder sb = new StringBuilder();
        sb.append("--");
        sb.append(BOUNDARY);
        sb.append("\r\n");
        sb.append(
                "Content-Disposition:form-data;name=\"file\";filename=\""
                        + file.getName()
                        + "\"\r\n");
        sb.append("Content-Type:application/octet-stream\r\n\r\n");

        byte[] head = sb.toString().getBytes("utf-8");

        // 获得输出流
        OutputStream out = new DataOutputStream(connection.getOutputStream());
        // 输出表头
        out.write(head);

        // 文件正文部分
        // 把文件以流文件的方式，推入到url中
        DataInputStream in = new DataInputStream(new FileInputStream(file));
        int bytes = 0;
        byte[] bufferOut = new byte[1024];
        while ((bytes = in.read(bufferOut)) != -1) {
            out.write(bufferOut, 0, bytes);
        }
        in.close();

        // 结尾部分
        // 定义最后数据分割线
        byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");

        out.write(foot);
        out.flush();
        out.close();

        StringBuilder buf = new StringBuilder();
        BufferedReader reader = null;
        String result = null;
        try {
            // 定义BufferedReader输入流来读取URL的响应
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                buf.append(line);
            }
            if (result == null) {
                result = buf.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
        }

        JSONObject jsonObject = JSONObject.fromObject(result);
        log.info("【新建临时素材】响应结果：{}", jsonObject);
        String mediaId = jsonObject.getString(WechatConsts.Media.MEDIA_ID);
        return mediaId;
    }
}

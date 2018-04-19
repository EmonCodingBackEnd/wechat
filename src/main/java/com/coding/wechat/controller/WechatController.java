/*
 * 文件名称：WeiXinController.java
 * 系统名称：[系统名称]
 * 模块名称：[模块名称]
 * 软件版权：Copyright (c) 2011-2018, liming20110711@163.com All Rights Reserved.
 * 功能说明：[请在此处输入功能说明]
 * 开发人员：Rushing0711
 * 创建日期：20180327 22:35
 * 修改记录：
 * <Version>        <DateSerial>        <Author>        <Description>
 * 1.0.0            20180327-01         Rushing0711     M201803272235 新建文件
 ********************************************************************************/
package com.coding.wechat.controller;

import com.coding.wechat.DO.AccessToken;
import com.coding.wechat.DO.BaseMessage;
import com.coding.wechat.DO.TextMessage;
import com.coding.wechat.common.WechatConstant;
import com.coding.wechat.config.WechatConfig;
import com.coding.wechat.utils.MessageUtil;
import com.coding.wechat.utils.WechatUtil;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

/**
 * [请在此输入功能简述].
 *
 * <p>创建时间: <font style="color:#00FFFF">20180327 22:36</font><br>
 * [请在此输入功能详述]
 *
 * @author Rushing0711
 * @version 1.0.0
 * @since 1.0.0
 */
@RestController
@Slf4j
public class WechatController {

    @Autowired WechatConfig wechatConfig;

    @GetMapping(value = "/message", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String checkSignature(
            @RequestParam(value = "signature") String signature,
            @RequestParam("timestamp") String timestamp,
            @RequestParam("nonce") String nonce,
            @RequestParam("echostr") String echostr) {
        log.info(
                "【微信公众平台接入】signature={},timestamp={},nonce={},echostr={}",
                signature,
                timestamp,
                nonce,
                echostr);

        String token = "emonnote";
        // 1、将token、timestamp、nonce三个参数进行字典序排序
        String[] array = {token, timestamp, nonce};
        Arrays.sort(array);
        // 2、将三个参数字符串拼接成一个字符串进行sha1加密
        String arrayString = StringUtils.arrayToDelimitedString(array, "");
        String arraySecret = DigestUtils.sha1Hex(arrayString);

        log.info("【微信公众平台接入】sha1加密结果={}", arraySecret);

        // 3、开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
        if (arraySecret.equals(signature)) {
            return echostr;
        } else {
            return "";
        }
    }

    @PostMapping(value = "/message", produces = MediaType.APPLICATION_XML_VALUE)
    public BaseMessage receiveMessage(HttpServletRequest request, HttpServletResponse response) {
        BaseMessage message = null;
        try {
            Map<String, String> map = MessageUtil.xmlToMap(request);
            String toUserName = map.get("ToUserName");
            String fromUserName = map.get("FromUserName");
            String msgType = map.get("MsgType");
            String content = map.get("Content");

            log.info(
                    "【WechatConfig】AppId={}, AppSecret={}, token={}",
                    wechatConfig.getAppId(),
                    wechatConfig.getAppSecret(),
                    wechatConfig.getToken());
            if (MessageUtil.MESSAGE_TEXT.equals(msgType)) {
                if ("1".equals(content)) {
                    message =
                            MessageUtil.initTextMessage(
                                    toUserName, fromUserName, MessageUtil.firstMenu());
                } else if ("2".equals(content)) {
                    message = MessageUtil.initNewsMessage(toUserName, fromUserName);
                } else if ("?".equals(content)) {
                    message =
                            MessageUtil.initTextMessage(
                                    toUserName, fromUserName, MessageUtil.menuText());
                } else {
                    message =
                            MessageUtil.initTextMessage(
                                    toUserName, fromUserName, String.format("你发送的消息是：%s", content));
                }
            } else if (MessageUtil.MESSAGE_EVENT.equals(msgType)) {
                String eventType = map.get("Event");
                log.info("【微信接收消息】消息类型={}", eventType);
                if (MessageUtil.MESSAGE_SUBSCRIBE.equals(eventType)) {
                    message =
                            MessageUtil.initTextMessage(
                                    toUserName, fromUserName, MessageUtil.menuText());
                }
            }
        } catch (Exception e) {
            log.error("【微信接收消息】异常", e);
        }
        return message;
    }

    @PostMapping(value = "/message2")
    public void receiveMessage2(HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        PrintWriter printWriter = null;
        try {
            Map<String, String> map = MessageUtil.xmlToMap(request);
            String toUserName = map.get("ToUserName");
            String fromUserName = map.get("FromUserName");
            String msgType = map.get("MsgType");
            String content = map.get("Content");
            String msgId = map.get("MsgId");

            String message = null;
            if (MessageUtil.MESSAGE_TEXT.equals(msgType)) {
                TextMessage textMessage = new TextMessage();
                textMessage.setFromUserName(toUserName);
                textMessage.setToUserName(fromUserName);
                textMessage.setMsgType(msgType);
                textMessage.setCreateTime(new Date().getTime());
                textMessage.setContent("您发送的消息是：" + content);
                message = MessageUtil.textMessageToXml(textMessage);
            } else if (MessageUtil.MESSAGE_EVENT.equals(msgType)) {
                String eventType = map.get("Event");
                if (MessageUtil.MESSAGE_SUBSCRIBE.equals(eventType)) {
                    message =
                            MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());
                }
            }
            printWriter = response.getWriter();
            printWriter.write(message);
        } catch (Exception e) {
            log.error("【微信接收消息】异常", e);
        } finally {
            printWriter.close();
        }
    }

    @GetMapping(value = "/accessToken", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public AccessToken accessToken() {
        AccessToken accessToken = new AccessToken();
        String accessTokenUrl =
                wechatConfig
                        .getAccessTokenUrl()
                        .replace(WechatConstant.BaseInfo.APP_ID, wechatConfig.getAppId())
                        .replace(WechatConstant.BaseInfo.APP_SECRET, wechatConfig.getAppSecret());
        log.info("【微信获取access_token】result={}", accessTokenUrl);
        JSONObject jsonObject = WechatUtil.doGetStr(accessTokenUrl);
        if (jsonObject != null) {
            accessToken.setAccessToken(jsonObject.getString("access_token"));
            accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
        }
        return accessToken;
    }
}

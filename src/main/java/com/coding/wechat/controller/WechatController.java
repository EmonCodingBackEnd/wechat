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
import com.coding.wechat.DO.message.BaseMessage;
import com.coding.wechat.config.WechatConfig;
import com.coding.wechat.constants.WechatConsts;
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
import java.util.Arrays;
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

    /**
     * 微信公众号、小程序消息推送验证方法.
     *
     * <p>创建时间: <font style="color:#00FFFF">20180506 17:29</font><br>
     * [请在此输入功能详述]
     *
     * @param signature - 微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
     * @param timestamp - 时间戳
     * @param nonce - 随机字符串
     * @param echostr - 随机字符串
     * @return java.lang.String
     * @author Rushing0711
     * @since 1.0.0
     */
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

    /**
     * 被动回复用户消息.
     *
     * <p>创建时间: <font style="color:#00FFFF">20180430 10:58</font><br>
     *
     * <ul>
     *   <li>回复文本消息
     *   <li>回复图片消息
     *   <li>回复语音消息
     *   <li>回复视频消息
     *   <li>回复音乐消息
     *   <li>回复图文消息
     * </ul>
     *
     * @param request -
     * @param response -
     * @return com.coding.wechat.DO.message.BaseMessage
     * @author Rushing0711
     * @since 1.0.0
     */
    @PostMapping(value = "/message", produces = MediaType.APPLICATION_XML_VALUE)
    public BaseMessage receiveMessage(HttpServletRequest request, HttpServletResponse response) {
        BaseMessage message = null;
        try {
            Map<String, String> map = MessageUtil.xmlToMap(request);
            String toUserName = map.get("ToUserName");
            String fromUserName = map.get("FromUserName");
            String msgType = map.get("MsgType");
            String content = map.get("Content");

            if (WechatConsts.Message.TEXT.equals(msgType)) {
                if ("1".equals(content)) {
                    message =
                            MessageUtil.initTextMessage(
                                    toUserName, fromUserName, MessageUtil.firstMenu());
                } else if ("2".equals(content)) {
                    message = MessageUtil.initNewsMessage(toUserName, fromUserName);
                } else if ("3".equals(content)) {
                    message = MessageUtil.initImageMessage(toUserName, fromUserName);
                } else if ("4".equals(content)) {
                    message = MessageUtil.initMusicMessage(toUserName, fromUserName);
                } else if ("?".equals(content)) {
                    message =
                            MessageUtil.initTextMessage(
                                    toUserName, fromUserName, MessageUtil.menuText());
                } else {
                    message =
                            MessageUtil.initTextMessage(
                                    toUserName, fromUserName, String.format("你发送的消息是：%s", content));
                }
            } else if (WechatConsts.Message.IMAGE.equals(msgType)) {
                message = MessageUtil.initImageMessage(toUserName, fromUserName);
            } else if (WechatConsts.Message.LOCATION.equals(msgType)) {
                String Label = map.get("Label");
                message = MessageUtil.initTextMessage(toUserName, fromUserName, Label);
            } else if (WechatConsts.Message_Event.EVENT.equals(msgType)) {
                String eventType = map.get("Event");
                log.info("【微信接收消息】消息类型={}", eventType);
                if (WechatConsts.Message_Event.SUBSCRIBE.equals(eventType)) {
                    message =
                            MessageUtil.initTextMessage(
                                    toUserName, fromUserName, MessageUtil.menuText());
                } else if (WechatConsts.Message_Event.CLICK.equals(eventType)) {
                    message =
                            MessageUtil.initTextMessage(
                                    toUserName, fromUserName, MessageUtil.menuText());
                } else if (WechatConsts.Message_Event.VIEW.equals(eventType)) {
                } else if (WechatConsts.Menu_Event.SCANCODE_PUSH.equals(eventType)) {
                } else if (WechatConsts.Menu_Event.LOCATION_SELECT.equals(eventType)) {
                }
            }
        } catch (Exception e) {
            log.error("【微信接收消息】异常", e);
        }
        return message;
    }

    @GetMapping(value = "/accessToken", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public AccessToken accessToken() {
        log.info(
                "【WechatConfig】AppId={}, AppSecret={}, token={}",
                wechatConfig.getAppId(),
                wechatConfig.getAppSecret(),
                wechatConfig.getToken());
        AccessToken accessToken = new AccessToken();
        String accessTokenUrl =
                wechatConfig
                        .getAccessTokenUrl()
                        .replace(WechatConsts.BaseInfo.APP_ID, wechatConfig.getAppId())
                        .replace(WechatConsts.BaseInfo.APP_SECRET, wechatConfig.getAppSecret());
        log.info("【微信获取access_token】accessTokenUrl={}", accessTokenUrl);
        JSONObject jsonObject = WechatUtil.doGetStr(accessTokenUrl);
        if (jsonObject != null) {
            accessToken.setAccessToken(jsonObject.getString("access_token"));
            accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
        }
        return accessToken;
    }

    @GetMapping(value = "/createMenu", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Integer createMenu(String accessToken, String menu) {
        Integer errcode = 0;
        String createMenuUrl =
                wechatConfig
                        .getCreateMenuUrl()
                        .replace(WechatConsts.BaseInfo.ACCESS_TOKEN, accessToken);
        log.info("【微信创建菜单】createMenuUrl={},menu={}", createMenuUrl, menu);
        JSONObject jsonObject = WechatUtil.doPostStr(createMenuUrl, menu);
        if (jsonObject != null) {
            errcode = jsonObject.getInt("errcode");
            String errmsg = jsonObject.getString("errmsg");
            log.info("【微信创建菜单】errcode={},errmsg={}", errcode, errmsg);
        }
        return errcode;
    }

    @GetMapping(value = "/queryMenu", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public JSONObject queryMenu(String accessToken) {
        String queryMenuUrl =
                wechatConfig
                        .getQueryMenuUrl()
                        .replace(WechatConsts.BaseInfo.ACCESS_TOKEN, accessToken);
        log.info("【微信查询菜单】queryMenuUrl={}", queryMenuUrl);
        JSONObject jsonObject = WechatUtil.doGetStr(queryMenuUrl);
        return jsonObject;
    }

    @GetMapping(value = "/deleteMenu", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Integer deleteMenunu(String accessToken) {
        Integer errcode = 0;
        String deleteMenuUrl =
                wechatConfig
                        .getDeleteMenuUrl()
                        .replace(WechatConsts.BaseInfo.ACCESS_TOKEN, accessToken);
        log.info("【微信删除菜单】deleteMenuUrl={}", deleteMenuUrl);
        JSONObject jsonObject = WechatUtil.doGetStr(deleteMenuUrl);
        if (jsonObject != null) {
            errcode = jsonObject.getInt("errcode");
            String errmsg = jsonObject.getString("errmsg");
            log.info("【微信删除菜单】errcode={},errmsg={}", errcode, errmsg);
        }
        return errcode;
    }
}

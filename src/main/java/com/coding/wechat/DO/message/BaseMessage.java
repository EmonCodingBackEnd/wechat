/*
 * 文件名称：BaseMessage.java
 * 系统名称：[系统名称]
 * 模块名称：[模块名称]
 * 软件版权：Copyright (c) 2011-2018, liming20110711@163.com All Rights Reserved.
 * 功能说明：[请在此处输入功能说明]
 * 开发人员：Rushing0711
 * 创建日期：20180416 19:18
 * 修改记录：
 * <Version>        <DateSerial>        <Author>        <Description>
 * 1.0.0            20180416-01         Rushing0711     M201804161918 新建文件
 ********************************************************************************/
package com.coding.wechat.DO.message;

import javax.xml.bind.annotation.XmlElement;

/**
 * [请在此输入功能简述].
 *
 * <p>创建时间: <font style="color:#00FFFF">20180416 19:19</font><br>
 * [请在此输入功能详述]
 *
 * @author Rushing0711
 * @version 1.0.0
 * @since 1.0.0
 */
public abstract class BaseMessage {

    /**
     * 对于请求消息：开发者微信号<br>
     * 对于应答消息：接收方帐号（收到的OpenID）<br>
     */
    private String ToUserName;

    /**
     * 对于请求消息：发送方帐号（一个OpenID）<br>
     * 对于应答消息：开发者微信号<br>
     */
    private String FromUserName;

    /** 消息创建时间 （整型）. */
    private Long CreateTime;

    /**
     * 被动回复消息类型.
     *
     * <p>创建时间: <font style="color:#00FFFF">20180430 11:05</font><br>
     *
     * <ul>
     *   <li>回复文本消息 - text
     *   <li>回复图片消息 - image
     *   <li>回复语音消息 - voice
     *   <li>回复视频消息 - video
     *   <li>回复音乐消息 - music
     *   <li>回复图文消息 - news
     * </ul>
     *
     * @since 1.0.0
     */
    private String MsgType;

    @XmlElement(name = "ToUserName")
    public String getToUserName() {
        return ToUserName;
    }

    public void setToUserName(String toUserName) {
        ToUserName = toUserName;
    }

    @XmlElement(name = "FromUserName")
    public String getFromUserName() {
        return FromUserName;
    }

    public void setFromUserName(String fromUserName) {
        FromUserName = fromUserName;
    }

    @XmlElement(name = "CreateTime")
    public Long getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(Long createTime) {
        CreateTime = createTime;
    }

    @XmlElement(name = "MsgType")
    public String getMsgType() {
        return MsgType;
    }

    public void setMsgType(String msgType) {
        MsgType = msgType;
    }
}

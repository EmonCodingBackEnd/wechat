/*
 * 文件名称：WechatConstant.java
 * 系统名称：[系统名称]
 * 模块名称：[模块名称]
 * 软件版权：Copyright (c) 2011-2018, liming20110711@163.com All Rights Reserved.
 * 功能说明：[请在此处输入功能说明]
 * 开发人员：Rushing0711
 * 创建日期：20180419 23:34
 * 修改记录：
 * <Version>        <DateSerial>        <Author>        <Description>
 * 1.0.0            20180419-01         Rushing0711     M201804192334 新建文件
 ********************************************************************************/
package com.coding.wechat.constants;

/**
 * [请在此输入功能简述].
 *
 * <p>创建时间: <font style="color:#00FFFF">20180419 23:34</font><br>
 * [请在此输入功能详述]
 *
 * @author Rushing0711
 * @version 1.0.0
 * @since 1.0.0
 */
public interface WechatConsts {

    interface BaseInfo {

        /** 第三方用户唯一凭证 */
        public static final String APP_ID = "APPID";

        /** 第三方用户唯一凭证密钥，即appsecret */
        public static final String APP_SECRET = "APPSECRET";

        /** 微信公众号全局唯一接口调用凭据 */
        public static final String ACCESS_TOKEN = "ACCESS_TOKEN";
    }

    interface Message {
        /** 文本消息. */
        public static final String TEXT = "text";

        /** 图文消息. */
        public static final String NEWS = "news";

        /** 图片消息. */
        public static final String IMAGE = "image";

        /** 语音消息. */
        public static final String VOICE = "voice";

        /** 视频消息. */
        public static final String VIDEO = "video";

        /** 小视频消息. */
        public static final String SHORTVIDEO = "shortvideo";

        /** 音乐消息. */
        public static final String MUSIC = "music";

        /** 地理位置消息. */
        public static final String LOCATION = "location";

        /** 链接消息. */
        public static final String LINK = "link";
    }

    interface Event {

        /** 事件消息. */
        public static final String EVENT = "event";

        /**
         * 关注事件.
         *
         * <p>创建时间: <font style="color:#00FFFF">20180430 21:38</font><br>
         *
         * <ul>
         *   <li>关注事件
         *   <li>扫描带参数二维码事件-用户未关注时，进行关注后的事件推送
         * </ul>
         *
         * @since 1.0.0
         */
        public static final String SUBSCRIBE = "subscribe";

        /** 取消关注事件. */
        public static final String UNSUBSCRIBE = "unsubscribe";

        /** 扫描带参数二维码事件-用用户已关注时的事件推送. */
        public static final String SCAN = "SCAN";

        /** 上报地理位置事件. */
        public static final String LOCATION = "LOCATION";

        /** 自定义菜单事件-点击菜单拉取消息时的事件推送 . */
        public static final String CLICK = "CLICK";

        /** 自定义菜单事件-点击菜单跳转链接时的事件推送 */
        public static final String VIEW = "VIEW";
    }

    interface Media {
        /** 媒体文件类型，分别有图片（image）、语音（voice）、视频（video）和缩略图（thumb）. */
        public static final String TYPE = "TYPE";

        /** 图片（image）: 2M，支持PNG\JPEG\JPG\GIF格式. */
        public static final String IMAGE = "image";

        /** 语音（voice）：2M，播放长度不超过60s，支持AMR\MP3格式 . */
        public static final String VOICE = "voice";

        /** 视频（video）：10MB，支持MP4格式 . */
        public static final String VIDEO = "video";

        /** 缩略图（thumb）：64KB，支持JPG格式 . */
        public static final String THUMB = "thumb";

        /** 媒体文件ID.临时素材media_id是可复用的 */
        public static final String MEDIA_ID = "media_id";
    }
}

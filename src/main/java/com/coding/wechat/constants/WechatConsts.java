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
        String APP_ID = "APPID";

        /** 第三方用户唯一凭证密钥，即appsecret */
        String APP_SECRET = "APPSECRET";

        /** 微信公众号全局唯一接口调用凭据 */
        String ACCESS_TOKEN = "ACCESS_TOKEN";
    }

    interface Message {
        /** 文本消息. */
        String TEXT = "text";

        /** 图文消息. */
        String NEWS = "news";

        /** 图片消息. */
        String IMAGE = "image";

        /** 语音消息. */
        String VOICE = "voice";

        /** 视频消息. */
        String VIDEO = "video";

        /** 小视频消息. */
        String SHORTVIDEO = "shortvideo";

        /** 音乐消息. */
        String MUSIC = "music";

        /** 地理位置消息. */
        String LOCATION = "location";

        /** 链接消息. */
        String LINK = "link";
    }

    interface Message_Event {

        /** 事件消息. */
        String EVENT = "event";

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
        String SUBSCRIBE = "subscribe";

        /** 取消关注事件. */
        String UNSUBSCRIBE = "unsubscribe";

        /** 扫描带参数二维码事件-用用户已关注时的事件推送. */
        String SCAN = "SCAN";

        /** 上报地理位置事件. */
        String LOCATION = "LOCATION";

        /** 自定义菜单事件-点击菜单拉取消息时的事件推送 . */
        String CLICK = "CLICK";

        /** 自定义菜单事件-点击菜单跳转链接时的事件推送 */
        String VIEW = "VIEW";
    }

    interface Media {
        /** 媒体文件类型，分别有图片（image）、语音（voice）、视频（video）和缩略图（thumb）. */
        String TYPE = "TYPE";

        /** 图片（image）: 2M，支持PNG\JPEG\JPG\GIF格式. */
        String IMAGE = "image";

        /** 语音（voice）：2M，播放长度不超过60s，支持AMR\MP3格式 . */
        String VOICE = "voice";

        /** 视频（video）：10MB，支持MP4格式 . */
        String VIDEO = "video";

        /** 缩略图（thumb）：64KB，支持JPG格式 . */
        String THUMB = "thumb";

        /** 媒体文件ID.临时素材media_id是可复用的 */
        String MEDIA_ID = "media_id";
    }

    interface Menu {
        /**
         * 1、点击推事件用户点击click类型按钮后，微信服务器会通过消息接口推送消息类型为event的结构给开发者（参考消息接口指南），<br>
         * 并且带上按钮中开发者填写的key值，开发者可以通过自定义的key值与用户进行交互；
         */
        String CLICK = "click";

        /**
         * 2、跳转URL用户点击view类型按钮后，微信客户端将会打开开发者在按钮中填写的网页URL，<br>
         * 可与网页授权获取用户基本信息接口结合，获得用户基本信息。
         */
        String VIEW = "view";

        /**
         * 3、扫码推事件用户点击按钮后，微信客户端将调起扫一扫工具，完成扫码操作后显示扫描结果（如果是URL，将进入URL），<br>
         * 且会将扫码的结果传给开发者，开发者可以下发消息。
         */
        String SCANCODE_PUSH = "scancode_push";

        /**
         * 4、扫码推事件且弹出“消息接收中”提示框用户点击按钮后，微信客户端将调起扫一扫工具，完成扫码操作后，将扫码的结果传给开发者，<br>
         * 同时收起扫一扫工具，然后弹出“消息接收中”提示框，随后可能会收到开发者下发的消息。
         */
        String SCANCODE_WAITMSG = "scancode_waitmsg";

        /**
         * 5、弹出系统拍照发图用户点击按钮后，微信客户端将调起系统相机，完成拍照操作后，会将拍摄的相片发送给开发者，<br>
         * 并推送事件给开发者，同时收起系统相机，随后可能会收到开发者下发的消息。
         */
        String PIC_SYSPHOTO = "pic_sysphoto";

        /** 6、弹出拍照或者相册发图用户点击按钮后，微信客户端将弹出选择器供用户选择“拍照”或者“从手机相册选择”。用户选择后即走其他两种流程。 */
        String PIC_PHOTO_OR_ALBUM = "pic_photo_or_album";

        /**
         * 7、弹出微信相册发图器用户点击按钮后，微信客户端将调起微信相册，完成选择操作后，将选择的相片发送给开发者的服务器，<br>
         * 并推送事件给开发者，同时收起相册，随后可能会收到开发者下发的消息。
         */
        String PIC_WEIXIN = "pic_weixin";

        /**
         * 8、弹出地理位置选择器用户点击按钮后，微信客户端将调起地理位置选择工具，完成选择操作后，将选择的地理位置发送给开发者的服务器，<br>
         * 同时收起位置选择工具，随后可能会收到开发者下发的消息。
         */
        String LOCATION_SELECT = "location_select";

        /**
         * 9、下发消息（除文本消息）用户点击media_id类型按钮后，微信服务器会将开发者填写的永久素材id对应的素材下发给用户，<br>
         * 永久素材类型可以是图片、音频、视频、图文消息。请注意：永久素材id必须是在“素材管理/新增永久素材”接口上传后获得的合法id。
         */
        String MEDIA_ID = "media_id";

        /**
         * 10、跳转图文消息URL用户点击view_limited类型按钮后，微信客户端将打开开发者在按钮中填写的永久素材id对应的图文消息URL，<br>
         * 永久素材类型只支持图文消息。请注意：永久素材id必须是在“素材管理/新增永久素材”接口上传后获得的合法id。 <br>
         * 请注意，3到8的所有事件，仅支持微信iPho
         */
        String VIEW_LIMITED = "view_limited";
    }

    interface Menu_Event {

        /** 事件消息. */
        String EVENT = "event";

        /** 自定义菜单事件-点击菜单拉取消息时的事件推送 . */
        String CLICK = "CLICK";

        /** 自定义菜单事件-点击菜单跳转链接时的事件推送 */
        String VIEW = "VIEW";

        /** 扫码推事件的事件推送. */
        String SCANCODE_PUSH = "scancode_push";

        /** 扫码推事件且弹出“消息接收中”提示框的事件推送. */
        String SCANCODE_WAITMSG = "scancode_waitmsg";

        /** 弹出系统拍照发图的事件推送. */
        String PIC_SYSPHOTO = "pic_sysphoto";

        /** 弹出拍照或者相册发图的事件推送. */
        String PIC_PHOTO_OR_ALBUM = "pic_photo_or_album";

        /** 弹出微信相册发图器的事件推送. */
        String PIC_WEIXIN = "pic_weixin";

        /** 弹出地理位置选择器的事件推送. */
        String LOCATION_SELECT = "location_select";
    }
}

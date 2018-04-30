/*
 * 文件名称：Music.java
 * 系统名称：[系统名称]
 * 模块名称：[模块名称]
 * 软件版权：Copyright (c) 2011-2018, liming20110711@163.com All Rights Reserved.
 * 功能说明：[请在此处输入功能说明]
 * 开发人员：Rushing0711
 * 创建日期：20180501 00:26
 * 修改记录：
 * <Version>        <DateSerial>        <Author>        <Description>
 * 1.0.0            20180501-01         Rushing0711     M201805010026 新建文件
 ********************************************************************************/
package com.coding.wechat.DO;

import javax.xml.bind.annotation.XmlElement;

/**
 * [请在此输入功能简述].
 *
 * <p>创建时间: <font style="color:#00FFFF">20180501 00:26</font><br>
 * [请在此输入功能详述]
 *
 * @author Rushing0711
 * @version 1.0.0
 * @since 1.0.0
 */
public class Music {

    /** 音乐标题. */
    private String Title;

    /** 音乐描述. */
    private String Description;

    /** 音乐链接. */
    private String MusicUrl;

    /** 高质量音乐链接，WIFI环境优先使用该链接播放音乐. */
    private String HQMusicUrl;

    /** 缩略图的媒体id，通过素材管理中的接口上传多媒体文件，得到的id. */
    private String ThumbMediaId;

    @XmlElement(name = "Title")
    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    @XmlElement(name = "Description")
    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    @XmlElement(name = "MusicUrl")
    public String getMusicUrl() {
        return MusicUrl;
    }

    public void setMusicUrl(String musicUrl) {
        MusicUrl = musicUrl;
    }

    @XmlElement(name = "HQMusicUrl")
    public String getHQMusicUrl() {
        return HQMusicUrl;
    }

    public void setHQMusicUrl(String HQMusicUrl) {
        this.HQMusicUrl = HQMusicUrl;
    }

    @XmlElement(name = "ThumbMediaId")
    public String getThumbMediaId() {
        return ThumbMediaId;
    }

    public void setThumbMediaId(String thumbMediaId) {
        ThumbMediaId = thumbMediaId;
    }
}

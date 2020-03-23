/*
 * 文件名称：News.java
 * 系统名称：[系统名称]
 * 模块名称：[模块名称]
 * 软件版权：Copyright (c) 2011-2018, liming20110711@163.com All Rights Reserved.
 * 功能说明：[请在此处输入功能说明]
 * 开发人员：Rushing0711
 * 创建日期：20180416 18:40
 * 修改记录：
 * <Version>        <DateSerial>        <Author>        <Description>
 * 1.0.0            20180416-01         Rushing0711     M201804161840 新建文件
 ********************************************************************************/
package com.coding.wechat.DO.message;

import javax.xml.bind.annotation.XmlElement;

/**
 * [请在此输入功能简述].
 *
 * <p>创建时间: <font style="color:#00FFFF">20180416 18:58</font><br>
 * [请在此输入功能详述]
 *
 * @author Rushing0711
 * @version 1.0.0
 * @since 1.0.0
 */
public class News {

    /** 图文消息标题 */
    private String Title;

    /** 图文消息描述 */
    private String Description;

    /** 图片链接，支持JPG、PNG格式，较好的效果为大图360*200，小图200*200 */
    private String PicUrl;

    /** 点击图文消息跳转链接 */
    private String Url;

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

    @XmlElement(name = "PicUrl")
    public String getPicUrl() {
        return PicUrl;
    }

    public void setPicUrl(String picUrl) {
        PicUrl = picUrl;
    }

    @XmlElement(name = "Url")
    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }
}

/*
 * 文件名称：ImageMessage.java
 * 系统名称：[系统名称]
 * 模块名称：[模块名称]
 * 软件版权：Copyright (c) 2011-2018, liming20110711@163.com All Rights Reserved.
 * 功能说明：[请在此处输入功能说明]
 * 开发人员：Rushing0711
 * 创建日期：20180430 18:36
 * 修改记录：
 * <Version>        <DateSerial>        <Author>        <Description>
 * 1.0.0            20180430-01         Rushing0711     M201804301836 新建文件
 ********************************************************************************/
package com.coding.wechat.DO.message;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 图片消息.
 *
 * <p>创建时间: <font style="color:#00FFFF">20180430 18:36</font><br>
 * [请在此输入功能详述]
 *
 * @author Rushing0711
 * @version 1.0.0
 * @since 1.0.0
 */
@XmlRootElement(name = "xml")
public class ImageMessage extends BaseMessage {

    private Image image;

    @XmlElement(name = "Image")
    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}

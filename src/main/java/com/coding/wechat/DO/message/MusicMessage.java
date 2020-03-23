/*
 * 文件名称：MusicMessage.java
 * 系统名称：[系统名称]
 * 模块名称：[模块名称]
 * 软件版权：Copyright (c) 2011-2018, liming20110711@163.com All Rights Reserved.
 * 功能说明：[请在此处输入功能说明]
 * 开发人员：Rushing0711
 * 创建日期：20180501 00:27
 * 修改记录：
 * <Version>        <DateSerial>        <Author>        <Description>
 * 1.0.0            20180501-01         Rushing0711     M201805010027 新建文件
 ********************************************************************************/
package com.coding.wechat.DO.message;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 音乐消息.
 *
 * <p>创建时间: <font style="color:#00FFFF">20180501 00:27</font><br>
 * [请在此输入功能详述]
 *
 * @author Rushing0711
 * @version 1.0.0
 * @since 1.0.0
 */
@XmlRootElement(name = "xml")
public class MusicMessage extends BaseMessage {

    private Music music;

    @XmlElement(name = "Music")
    public Music getMusic() {
        return music;
    }

    public void setMusic(Music music) {
        this.music = music;
    }
}

/*
 * 文件名称：NewsMessage.java
 * 系统名称：[系统名称]
 * 模块名称：[模块名称]
 * 软件版权：Copyright (c) 2011-2018, liming20110711@163.com All Rights Reserved.
 * 功能说明：[请在此处输入功能说明]
 * 开发人员：Rushing0711
 * 创建日期：20180416 18:47
 * 修改记录：
 * <Version>        <DateSerial>        <Author>        <Description>
 * 1.0.0            20180416-01         Rushing0711     M201804161847 新建文件
 ********************************************************************************/
package com.coding.wechat.DO;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * 图文消息.
 *
 * <p>创建时间: <font style="color:#00FFFF">20180416 18:48</font><br>
 * [请在此输入功能详述]
 *
 * @author Rushing0711
 * @version 1.0.0
 * @since 1.0.0
 */
@XmlRootElement(name = "xml")
public class NewsMessage extends BaseMessage {

    /** 图文消息个数，限制为8条以内 */
    private Integer ArticleCount;

    /** 多条图文消息信息，默认第一个item为大图,注意，如果图文数超过8，则将会无响应 */
    private List<News> newsList;

    @XmlElement(name = "ArticleCount")
    public Integer getArticleCount() {
        return ArticleCount;
    }

    public void setArticleCount(Integer articleCount) {
        ArticleCount = articleCount;
    }

    @XmlElementWrapper(name = "Articles")
    @XmlElement(name = "item")
    public List<News> getNewsList() {
        return newsList;
    }

    public void setNewsList(List<News> newsList) {
        this.newsList = newsList;
    }
}

/*
 * 文件名称：MessageUtil.java
 * 系统名称：[系统名称]
 * 模块名称：[模块名称]
 * 软件版权：Copyright (c) 2011-2018, liming20110711@163.com All Rights Reserved.
 * 功能说明：[请在此处输入功能说明]
 * 开发人员：Rushing0711
 * 创建日期：20180412 08:17
 * 修改记录：
 * <Version>        <DateSerial>        <Author>        <Description>
 * 1.0.0            20180412-01         Rushing0711     M201804120817 新建文件
 ********************************************************************************/
package com.coding.wechat.utils;

import com.coding.wechat.DO.*;
import com.coding.wechat.constants.WechatConsts;
import com.thoughtworks.xstream.XStream;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.*;

/**
 * 消息格式转换工具.
 *
 * <p>创建时间: <font style="color:#00FFFF">20180412 08:18</font><br>
 * [请在此输入功能详述]
 *
 * @author Rushing0711
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
public class MessageUtil {

    /**
     * xml转换为Map集合.
     *
     * <p>创建时间: <font style="color:#00FFFF">20180412 08:25</font><br>
     * [请在此输入功能详述]
     *
     * @param request -
     * @return java.util.Map<java.lang.String,java.lang.String>
     * @throws IOException -
     * @throws DocumentException -
     * @author Rushing0711
     * @since 1.0.0
     */
    public static Map<String, String> xmlToMap(HttpServletRequest request)
            throws IOException, DocumentException {
        Map<String, String> map = new HashMap<>();
        SAXReader reader = new SAXReader();

        InputStream ins = request.getInputStream();
        Document doc = reader.read(ins);

        Element root = doc.getRootElement();
        // 格式化输出
        OutputFormat formater = OutputFormat.createPrettyPrint();
        formater.setEncoding("UTF-8");
        StringWriter out = new StringWriter();
        XMLWriter writer = new XMLWriter(out, formater);
        writer.write(doc);
        writer.close();
        log.info("【微信接收消息】请求消息={}", out.toString());

        List<Element> list = root.elements();
        for (Element e : list) {
            map.put(e.getName(), e.getText());
        }
        ins.close();
        return map;
    }

    /**
     * 将文本消息对象转换为xml.
     *
     * <p>创建时间: <font style="color:#00FFFF">20180412 08:35</font><br>
     * [请在此输入功能详述]
     *
     * @param textMessage -
     * @return java.lang.String
     * @author Rushing0711
     * @since 1.0.0
     */
    public static String textMessageToXml(TextMessage textMessage) {
        XStream xStream = new XStream();
        xStream.alias("xml", textMessage.getClass());
        String result = xStream.toXML(textMessage);
        log.info("【微信接收消息】应答消息={}", result);
        return result;
    }

    /**
     * 组装文本消息.
     *
     * <p>创建时间: <font style="color:#00FFFF">20180430 10:55</font><br>
     * [请在此输入功能详述]
     *
     * @param toUserName -
     * @param fromUserName -
     * @param content -
     * @return com.coding.wechat.DO.TextMessage
     * @author Rushing0711
     * @since 1.0.0
     */
    public static TextMessage initTextMessage(
            String toUserName, String fromUserName, String content) {
        TextMessage textMessage = new TextMessage();
        textMessage.setFromUserName(toUserName);
        textMessage.setToUserName(fromUserName);
        textMessage.setMsgType(WechatConsts.Message.TEXT);
        textMessage.setCreateTime(new Date().getTime());
        textMessage.setContent(content);
        return textMessage;
    }

    /**
     * 菜单.
     *
     * <p>创建时间: <font style="color:#00FFFF">20180416 08:32</font><br>
     * [请在此输入功能详述]
     *
     * @return java.lang.String
     * @author Rushing0711
     * @since 1.0.0
     */
    public static String menuText() {
        StringBuffer sb = new StringBuffer();
        sb.append("欢迎您的关注，请按照菜单提示进行操作：\n\n");
        sb.append("1、课程介绍\n");
        sb.append("2、慕课网介绍\n");
        sb.append("3、得到图片\n\n");
        sb.append("回复 ? 调出此菜单。\n");
        return sb.toString();
    }

    public static String firstMenu() {
        StringBuffer sb = new StringBuffer();
        sb.append("本套课程介绍微信公众号开发，主要涉及公众号介绍会、编辑模式介绍、开发模式介绍等");
        return sb.toString();
    }

    public static String secondMenu() {
        StringBuffer sb = new StringBuffer();
        sb.append(
                "慕课网是垂直的互联网IT技能免费学习网站。以独家视频教程、在线编程工具、学习计划、问答社区为核心特色。在这里，你可以找到最好的互联网技术牛人，也可以通过免费的在线公开视频课程学习国内领先的互联网IT技术。\n"
                        + "慕课网课程涵盖前端开发、PHP、Html5、Android、iOS、Swift等IT前沿技术语言，包括基础课程、实用案例、高级分享三大类型，适合不同阶段的学习人群。以纯干货、短视频的形式为平台特点，为在校学生、职场白领提供了一个迅速提升技能、共同分享进步的学习平台。 [1] \n"
                        + "4月2日，国内首个IT技能学习类应用——慕课网3.1.0版本在应用宝首发。据了解，在此次上线的版本中，慕课网新增了课程历史记录、相关课程推荐等四大功能，为用户营造更加丰富的移动端IT学习体验。");
        return sb.toString();
    }

    /**
     * 组装图文消息.
     *
     * <p>创建时间: <font style="color:#00FFFF">20180416 19:14</font><br>
     * [请在此输入功能详述]
     *
     * @param toUserName -
     * @param fromUserName -
     * @return com.coding.wechat.DO.NewsMessage
     * @author Rushing0711
     * @since 1.0.0
     */
    public static NewsMessage initNewsMessage(String toUserName, String fromUserName) {
        List<News> newsList = new ArrayList<>();
        News news = new News();
        news.setTitle("慕课网介绍");
        news.setDescription(
                "慕课网是垂直的互联网IT技能免费学习网站。以独家视频教程、在线编程工具、学习计划、问答社区为核心特色。在这里，你可以找到最好的互联网技术牛人，也可以通过免费的在线公开视频课程学习国内领先的互联网IT技术。\n"
                        + "慕课网课程涵盖前端开发、PHP、Html5、Android、iOS、Swift等IT前沿技术语言，包括基础课程、实用案例、高级分享三大类型，适合不同阶段的学习人群。以纯干货、短视频的形式为平台特点，为在校学生、职场白领提供了一个迅速提升技能、共同分享进步的学习平台。 [1] \n"
                        + "4月2日，国内首个IT技能学习类应用——慕课网3.1.0版本在应用宝首发。据了解，在此次上线的版本中，慕课网新增了课程历史记录、相关课程推荐等四大功能，为用户营造更加丰富的移动端IT学习体验。");
        news.setPicUrl("http://exp.mynatapp.cc/wechat/images/stare.jpg");
        news.setUrl("www.imooc.com");
        newsList.add(news);
        newsList.add(news);

        NewsMessage newsMessage = new NewsMessage();
        newsMessage.setFromUserName(toUserName);
        newsMessage.setToUserName(fromUserName);
        newsMessage.setMsgType(WechatConsts.Message.NEWS);
        newsMessage.setCreateTime(new Date().getTime());
        newsMessage.setArticleCount(newsList.size());
        newsMessage.setNewsList(newsList);
        return newsMessage;
    }

    /**
     * 组装图片消息.
     *
     * <p>创建时间: <font style="color:#00FFFF">20180430 19:56</font><br>
     * [请在此输入功能详述]
     *
     * @param toUserName -
     * @param fromUserName -
     * @return com.coding.wechat.DO.NewsMessage
     * @author Rushing0711
     * @since 1.0.0
     */
    public static ImageMessage initImageMessage(String toUserName, String fromUserName) {
        Image image = new Image();
        image.setMediaId("Turg7a0ggBp3wWy06KL1CMZHDaFbMtVhD1FGtGMVRnDRg_0q3mqw7ycdgGlSJ_dX");

        ImageMessage imageMessage = new ImageMessage();
        imageMessage.setFromUserName(toUserName);
        imageMessage.setToUserName(fromUserName);
        imageMessage.setMsgType(WechatConsts.Message.IMAGE);
        imageMessage.setCreateTime(new Date().getTime());
        imageMessage.setImage(image);
        return imageMessage;
    }
}

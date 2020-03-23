/*
 * 文件名称：Button.java
 * 系统名称：[系统名称]
 * 模块名称：[模块名称]
 * 软件版权：Copyright (c) 2011-2018, liming20110711@163.com All Rights Reserved.
 * 功能说明：[请在此处输入功能说明]
 * 开发人员：Rushing0711
 * 创建日期：20180501 10:16
 * 修改记录：
 * <Version>        <DateSerial>        <Author>        <Description>
 * 1.0.0            20180501-01         Rushing0711     M201805011016 新建文件
 ********************************************************************************/
package com.coding.wechat.DO.menu;

import lombok.Data;

/**
 * [请在此输入功能简述].
 *
 * <p>创建时间: <font style="color:#00FFFF">20180501 10:16</font><br>
 * [请在此输入功能详述]
 *
 * @author Rushing0711
 * @version 1.0.0
 * @since 1.0.0
 */
@Data
public abstract class BaseButton {

    /** 菜单的响应动作类型，view表示网页类型，click表示点击类型，miniprogram表示小程序类型 */
    private String type;

    /** 菜单标题，不超过16个字节，子菜单不超过60个字节. */
    private String name;
}

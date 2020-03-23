/*
 * 文件名称：ViewButton.java
 * 系统名称：[系统名称]
 * 模块名称：[模块名称]
 * 软件版权：Copyright (c) 2011-2018, liming20110711@163.com All Rights Reserved.
 * 功能说明：[请在此处输入功能说明]
 * 开发人员：Rushing0711
 * 创建日期：20180501 10:22
 * 修改记录：
 * <Version>        <DateSerial>        <Author>        <Description>
 * 1.0.0            20180501-01         Rushing0711     M201805011022 新建文件
 ********************************************************************************/
package com.coding.wechat.DO.menu;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * [请在此输入功能简述].
 *
 * <p>创建时间: <font style="color:#00FFFF">20180501 10:22</font><br>
 * [请在此输入功能详述]
 *
 * @author Rushing0711
 * @version 1.0.0
 * @since 1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ViewButton extends BaseButton {

    /**
     * view、miniprogram类型必须：<br>
     * 网页 链接，用户点击菜单可打开链接，不超过1024字节。type为miniprogram时，不支持小程序的老版本客户端将打开本url。.
     */
    private String url;
}

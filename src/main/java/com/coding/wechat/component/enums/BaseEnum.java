/*
 * 文件名称：BaseEnum.java
 * 系统名称：[系统名称]
 * 模块名称：枚举基础接口
 * 软件版权：Copyright (c) 2011-2018, liming20110711@163.com All Rights Reserved.
 * 功能说明：[请在此处输入功能说明]
 * 开发人员：Rushing0711
 * 创建日期：20180616 12:21
 * 修改记录：
 * <Version>        <DateSerial>        <Author>        <Description>
 * 1.0.0            20180616-01         Rushing0711     M201806161221 新建文件
 ********************************************************************************/
package com.coding.wechat.component.enums;

/**
 * 枚举基础接口.
 *
 * <p>创建时间: <font style="color:#00FFFF">20180423 17:13</font><br>
 * [请在此输入功能详述]
 *
 * @author Rushing0711
 * @version 1.0.0
 * @since 1.0.0
 */
public interface BaseEnum<T> {
    T getCode();

    String getMsg();
}

/*
 * 文件名称：Button.java
 * 系统名称：[系统名称]
 * 模块名称：[模块名称]
 * 软件版权：Copyright (c) 2011-2018, liming20110711@163.com All Rights Reserved.
 * 功能说明：[请在此处输入功能说明]
 * 开发人员：Rushing0711
 * 创建日期：20180501 10:53
 * 修改记录：
 * <Version>        <DateSerial>        <Author>        <Description>
 * 1.0.0            20180501-01         Rushing0711     M201805011053 新建文件
 ********************************************************************************/
package com.coding.wechat.DO.menu;

import lombok.Data;

/**
 * [请在此输入功能简述].
 *
 * <p>创建时间: <font style="color:#00FFFF">20180501 10:53</font><br>
 * [请在此输入功能详述]
 *
 * @author Rushing0711
 * @version 1.0.0
 * @since 1.0.0
 */
@Data
public class Button extends BaseButton {

    /** 二级菜单数组，个数应为1~5个. */
    private BaseButton[] sub_button;
}

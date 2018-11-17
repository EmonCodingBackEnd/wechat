/*
 * 文件名称：ShopVO.java
 * 系统名称：[系统名称]
 * 模块名称：[模块名称]
 * 软件版权：Copyright (c) 2011-2018, liming20110711@163.com All Rights Reserved.
 * 功能说明：[请在此处输入功能说明]
 * 开发人员：Rushing0711
 * 创建日期：20181114 17:59
 * 修改记录：
 * <Version>        <DateSerial>        <Author>        <Description>
 * 1.0.0            20181114-01         Rushing0711     M201811141759 新建文件
 ********************************************************************************/
package com.coding.component.security.auth.VO;

import lombok.Data;

import java.io.Serializable;

@Data
public class ShopVO implements Serializable {

    private static final long serialVersionUID = 1835148772873517749L;

    private String id;

    private String name;
}

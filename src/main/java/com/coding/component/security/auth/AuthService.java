/*
 * 文件名称：AuthService.java
 * 系统名称：[系统名称]
 * 模块名称：[模块名称]
 * 软件版权：Copyright (c) 2011-2018, liming20110711@163.com All Rights Reserved.
 * 功能说明：[请在此处输入功能说明]
 * 开发人员：Rushing0711
 * 创建日期：20181107 16:36
 * 修改记录：
 * <Version>        <DateSerial>        <Author>        <Description>
 * 1.0.0            20181107-01         Rushing0711     M201811071636 新建文件
 ********************************************************************************/
package com.coding.component.security.auth;

public interface AuthService {

    LoginAuthority login(String authType, String username);

    LoginSession loginSession(Long userId);

    void switchSystem(Long userId, Integer targetSystem);

    void switchShop(Long userId, Long targetShop);

    SystemInfo systemInfo();
}

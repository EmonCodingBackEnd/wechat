/*
 * 文件名称：LoginAuthority.java
 * 系统名称：[系统名称]
 * 模块名称：[模块名称]
 * 软件版权：Copyright (c) 2011-2018, liming20110711@163.com All Rights Reserved.
 * 功能说明：[请在此处输入功能说明]
 * 开发人员：Rushing0711
 * 创建日期：20181109 18:42
 * 修改记录：
 * <Version>        <DateSerial>        <Author>        <Description>
 * 1.0.0            20181109-01         Rushing0711     M201811091842 新建文件
 ********************************************************************************/
package com.coding.component.security.auth;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class LoginAuthority implements Serializable {

    private static final long serialVersionUID = 2874984346071024286L;

    /** 用户编号. */
    private Long userId;

    /** 用户名称 */
    private String username;

    /** 密码. */
    private String password;

    /** 用户当前状态拥有的角色. */
    private List<String> roleIdList;
}

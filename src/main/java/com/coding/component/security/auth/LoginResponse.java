/*
 * 文件名称：LoginResponse.java
 * 系统名称：[系统名称]
 * 模块名称：[模块名称]
 * 软件版权：Copyright (c) 2011-2018, liming20110711@163.com All Rights Reserved.
 * 功能说明：[请在此处输入功能说明]
 * 开发人员：Rushing0711
 * 创建日期：20181110 13:29
 * 修改记录：
 * <Version>        <DateSerial>        <Author>        <Description>
 * 1.0.0            20181110-01         Rushing0711     M201811101329 新建文件
 ********************************************************************************/
package com.coding.component.security.auth;

import com.coding.component.system.api.AppResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginResponse extends AppResponse {
    private static final long serialVersionUID = -3099123363323227147L;

    /** 用户登录后获得的凭证，调用接口需要传递. */
    private String token;

    /** 用户编号. */
    private String userId;

    /** 用户的当前门店，用户切换前取自系统中的默认门店 */
    private String currentShopId;

    /** 当前系统用户切换前取自系统中的默认系统. */
    private String currentSystem;

    /** 用户名称 */
    private String username;

    /** 性别 */
    private Integer sex;

    /** 年龄 */
    private Integer age;

    /** 用户头像 */
    private String avatar;

    /** 是否是超级用户 */
    private Integer isAdmin;

    /** 用户手机号. */
    private String mobile;

    /** 用户登录名称. */
    private String loginname;

    /** 用户可访问的系统类型. */
    @JsonProperty("systemTypes")
    private List<String> systemTypeList;

    /** 用户管辖的门店信息. */
    @JsonProperty("shopIds")
    private List<String> shopIdList;

    /** 用户拥有的菜单权限. */
    @JsonProperty("menuIds")
    private List<String> menuIdList;
}

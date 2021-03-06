/*
 * 文件名称：UserLoginResponse.java
 * 系统名称：[系统名称]
 * 模块名称：[模块名称]
 * 软件版权：Copyright (c) 2011-2018, liming20110711@163.com All Rights Reserved.
 * 功能说明：[请在此处输入功能说明]
 * 开发人员：Rushing0711
 * 创建日期：20181101 10:00
 * 修改记录：
 * <Version>        <DateSerial>        <Author>        <Description>
 * 1.0.0            20181101-01         Rushing0711     M201811011000 新建文件
 ********************************************************************************/
package com.coding.component.security.auth;

import com.coding.component.security.auth.VO.ShopVO;
import com.coding.component.security.auth.VO.SystemVO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginSession implements Serializable {

    private static final long serialVersionUID = 52420565251745462L;

    /** 用户编号. */
    private Long userId;

    /** 用户所属租户. */
    private Long tenantId;

    /** 用户所属租户的总店. */
    private Long hqShopId;

    /** 用户的当前门店，用户切换前取自系统中的默认门店 */
    private Long currentShopId;

    /** 当前系统用户切换前取自系统中的默认系统. */
    private Integer currentSystem;

    /** 用户名称 */
    private String username;

    /** 密码. */
    private String password;

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
    private List<SystemVO> systemTypeList;

    /** 用户管辖的门店信息. */
    @JsonProperty("shopInfos")
    private List<ShopVO> shopInfoList;

    /** 用户拥有的菜单权限. */
    @JsonProperty("menuKeys")
    private List<String> menuKeyList;

    /** 用户拥有的角色权限. */
    @JsonProperty("authoritys")
    private LoginAuthority loginAuthority;

    public String toLog() {
        return "LoginSession{"
                + "userId="
                + userId
                + ", currentShopId="
                + currentShopId
                + ", username='"
                + username
                + '\''
                + '}';
    }
}

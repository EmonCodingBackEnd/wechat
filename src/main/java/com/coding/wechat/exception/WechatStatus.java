/*
 * 文件名称：EdenStatus.java
 * 系统名称：[系统名称]
 * 模块名称：[模块名称]
 * 软件版权：Copyright (c) 2011-2018, liming20110711@163.com All Rights Reserved.
 * 功能说明：[请在此处输入功能说明]
 * 开发人员：Rushing0711
 * 创建日期：20180806 09:58
 * 修改记录：
 * <Version>        <DateSerial>        <Author>        <Description>
 * 1.0.0            20180806-01         Rushing0711     M201808060958 新建文件
 ********************************************************************************/
package com.coding.wechat.exception;

import lombok.Getter;

@Getter
public enum WechatStatus {
    SUCCESS(9000, "成功"),
    SYSTEM_BUSY(9100, "系统繁忙，请稍后重试"),

    PARAM_ERROR(1100, "请求参数错误"),
    TENANT_EMPTY(1101, "租户ID为空"),
    DICT_TYPE_CODE_EMPTY(1102, "字典代码为空"),
    PARAM_CODE_EMPTY(1103, "参数代码为空"),
    CUSTOMER_SOURCE_LEVEL_EMPTY(1104, "客户来源级别为空"),
    ORGAN_ID_EMPTY(1105, "机构ID为空"),
    DEPT_ID_EMPTY(1106, "部门ID为空"),
    USER_ID_EMPTY(1107, "用户ID为空"),
    ROLE_NAME_EMPTY(1108, "角色名称为空"),
    ROLE_ID_EMPTY(1109, "角色ID为空"),
    MENU_TYPE_EMPTY(1110, "菜单类型为空"),
    MENU_NAME_EMPTY(1111, "菜单名称为空"),
    MENU_ROUTE_EMPTY(1112, "菜单路由地址为空"),
    MENU_ID_EMPTY(1113, "菜单ID为空"),
    MENU_ITEM_TYPE_EMPTY(1114, "菜单项类型为空"),
    MENU_ITEM_NAME_EMPTY(1115, "菜单项名称为空"),
    MENU_ITEM_ID_EMPTY(1116, "菜单项ID为空"),
    DICT_TYPE_TEXT_EMPTY(1117, "字典描述为空"),
    DICT_ITEM_VALUE_EMPTY(1118, "字典项的值为空"),
    DICT_ITEM_TEXT_EMPTY(1119, "字典项描述为空"),
    DICT_TYPE_ID_EMPTY(1120, "字典ID不允许为空"),
    DICT_ITEM_ID_EMPTY(1121, "字典项ID不允许为空"),
    SHARE_POOL_RULE_CODE_EMPTY(1122, "公海池规则代码不允许空"),
    SHARE_POOL_RULE_ID_EMPTY(1123, "公海池规则ID不允许空"),
    PARAM_ID_EMPTY(1124, "参数ID不允许空"),
    CUSTOMER_SOURCE_NAME_EMPTY(1125, "客户来源方式名称不允许为空"),
    CUSTOMER_SOURCE_ID_EMPTY(1126, "客户来源方式ID为空"),
    ORGAN_NAME_EMPTY(1127, "机构名称不允许为空"),
    ORGAN_BUSITYPE_EMPTY(1128, "机构营业类型不允许为空"),
    ORGAN_CONTACTS_EMPTY(1129, "机构联系人不允许为空"),
    ORGAN_TEL_EMPTY(1130, "机构联系方式不允许为空"),
    DEPT_NAME_EMPTY(1131, "部门名称不允许为空"),
    USER_ORGAN_ID_EMPYT(1132, "指定的用户管辖机构包含空值"),
    USER_DEPT_ID_EMPYT(1133, "为用户指定的部门包含空值"),
    USER_ROLE_ID_EMPYT(1134, "为用户指定的机构角色包含空值"),
    USERNAME_EMTPY(1135, "用户名不能为空"),
    PASSWORD_EMTPY(1136, "用户密码不能为空"),

    TENANT_ERROR(1301, "租户ID输入错误"),

    MOBILE_FORMAT_ERROR(1401, "手机号码格式不正确"),
    EMAIL_FORMAT_ERROR(1402, "邮箱格式不正确"),

    AUTH_UNAUTHENTICATION(2100, "未认证！"),
    AUTH_USERNAME_PASSWORD_ERROR(2200, "用户名或密码错误"),
    AUTH_TYPE_NOT_SUPPORT(2201, "不支持的登录方式"),
    AUTH_USERNAME_ERROR(2202, "用户不存在"),
    AUTH_PASSWORD_ERROR(2203, "密码错误"),
    AUTH_USERNAME_DISABLED(2301, "账号封停"),
    AUTH_USERNAME_LOCKED(2302, "账号锁定"),
    AUTH_USERNAME_EXPIRED(2303, "账号已过期"),
    AUTH_LOGIN_EXPIRED(2400, "登录信息超时"),
    AUTH_PERMISSION_FORBIDDEN(2500, "用户权限不足"),

    SHARE_POOL_RULE_NOT_EXIST(3101, "公海池规则不存在"),
    SHARE_POOL_RULE_VALUE_NOT_EXIST(3102, "公海池规则值不存在"),
    PARAM_NOT_EXIST(3103, "参数不存在"),
    PARAM_VALUE_NOT_EXIST(3104, "公海池规则值不存在"),
    ROLE_NOT_EXIST(3105, "角色不存在"),
    PARENT_ROLE_NOT_EXIST(3106, "父角色不存在"),
    MENU_NOT_EXIST(3107, "菜单不存在"),
    PARENT_MENU_NOT_EXIST(3108, "父菜单不存在"),
    MENU_ITEM_NOT_EXIST(3109, "菜单项不存在"),
    DICT_TYPE_NOT_EXIST(3110, "字典不存在"),
    DICT_ITEM_NOT_EXIST(3111, "字典项不存在"),
    CUSTOMER_SOURCE_NOT_EXIST(3112, "客户来源方式不存在"),
    PARENT_CUSTOMER_SOURCE_NOT_EXIST(3113, "父客户来源方式不存在"),
    PARENT_ORGAN_NOT_EXIST(3114, "父机构不存在"),
    ORGAN_NOT_EXIST(3115, "机构不存在"),
    PARENT_DEPT_NOT_EXIST(3116, "父部门不存在"),
    DEPT_NOT_EXIST(3117, "部门不存在"),
    USER_NOT_EXIST(3118, "用户不存在"),
    USER_ORGAN_NOT_EXIST(3119, "指定管辖机构在用户所属租户下不存在"),
    SHOP_NOT_EXIST(3120, "当前门店不存在"),
    SPEC_NOT_EXIST(3121, "当前商品不存在"),
    TENANT_NOT_EXIST(3122, "当前租户不存在"),
    CUSTOMER_NOT_EXIST(3123, "会员不存在"),
    PURCHASE_NOT_EXIST(3124, "当前订单不存在"),
    CONSUMER_CARD_NOT_EXIST(3125, "当前会员卡不存在"),
    CUSTOMERCARDVERIFY_NOT_EXIST(3126, "当前核销记录不存在"),
    USER_SHIFT_NOT_EXIST(3127, "当前交换班记录不存在"),

    FROM_JSON_ERRPR(3201, "JSON转换到对象错误"),

    MENU_EXIST(3301, "菜单编号已存在"),
    MENU_ITEM_EXIST(3302, "菜单项编号已存在"),
    DICT_TYPE_CODE_EXIST(3303, "字典代码已存在"),
    DICT_TYPE_NEW_CODE_EXIST(3304, "该字典指定的新代码已存在"),
    DICT_ITEM_VALUE_EXIST(3305, "字典项的值已存在"),
    DICT_ITEM_NEW_VALUE_EXIST(3306, "该字典项指定的新值已存在"),
    DICT_TYPE_EXIST(3307, "该字典已存在"),
    ROLE_NAME_EXIST(3308, "当前角色已存在"),
    USER_EXIST(3309, "当前用户已存在"),

    SHARE_POOL_RULE_VALUE_ERROR(3401, "公海池规则值格式不正确"),
    MENU_STATUS_INVALID(3402, "菜单处于不可用状态"),
    PARENT_MENU_STATUS_INVALID(3402, "父菜单处于不可用状态"),
    MENU_IDS_INVALID(3403, "请输入英文逗号分隔的菜单ID串"),
    MENU_ITEM_STATUS_INVALID(3404, "菜单项处于不可用状态"),
    MENU_ITEM_IDS_INVALID(3405, "请输入英文逗号分隔的菜单项ID串"),
    TENANT_NOT_MATCH_MENU_TENANT(3406, "请求的租户ID与菜单所属租户ID不相符"),
    MENU_ITEM_ACCESS_COUNT_OUT_OF_COUNT(3407, "菜单拥有的菜单项超过最大限制"),
    MENU_ITEM_ACCESS_VALUE_DUPLICATE(3408, "同一个菜单拥有重复的菜单项"),
    MENU_ITEM_ACCESS_VALUE_EXIST_INVALID(3409, "存在菜单项的权限不在正常范围值之内"),
    MENU_ITEM_NOT_BLONG_TO_MENU(3410, "请求中的菜单项中存在不属于菜单的情况"),
    ROLE_STATUS_INVALID(3411, "角色处于不可用状态"),
    PARENT_ROLE_STATUS_INVALID(3411, "父角色处于不可用状态"),
    DICT_TYPE_ACCESS_VALUE_INVALID(3412, "字典访问权限列表格式不正确"),
    DICT_TYPE_CODE_REGEX_ERROR(3413, "字典代码不符合组成规则"),
    DICT_TYPE_CODES_REGEX_ERROR(3414, "请输入英文逗号分隔的字典代码串"),
    DICT_TYPE_STATUS_INVALID(3415, "字典处于不可用状态"),
    DICT_TYPE_ACCESS_LIMIT(3416, "字典访问限制"),
    DICT_ITEM_CODE_REGEX_ERROR(3417, "字典项的值不符合组成规则"),
    DICT_ITEM_ACCESS_VALUE_INVALID(3418, "字典项访问权限列表格式不正确"),
    DICT_ITEM_ACCESS_LIMIT(3419, "字典项访问限制"),
    DICT_ITEM_STATUS_INVALID(3420, "字典项处于不可用状态"),
    DICT_TYPE_IDS_INVALID(3421, "请输入英文逗号分隔的字典ID串"),
    DICT_ITEM_IDS_INVALID(3422, "请输入英文逗号分隔的字典项ID串"),
    SHARE_POOL_RULE_NOT_SUPPORT(3423, "不支持的规则"),
    SHARE_POOL_RULE_STATUS_INVALID(3424, "公海池处于不可用状态"),
    PARAM_STATUS_INVALID(3424, "参数处于不可用状态"),
    PARAM_INEDITABLE(3425, "参数不可修改"),
    CUSTOMER_SOURCE_LEVEL_INVALID(3446, "客户来源方式级别无效"),
    CUSTOMER_SOURCE_STATUS_INVALID(3447, "客户来源方式处于不可用状态"),
    PARENT_CUSTOMER_SOURCE_STATUS_INVALID(3448, "父客户来源方式处于不可用状态"),
    CUSTOMER_SOURCE_IDS_INVALID(3449, "请输入英文逗号分隔的客户来源方式ID串"),
    PARENT_ORGAN_STATUS_INVALID(3450, "父机构处于不可用状态"),
    ORGAN_STATUS_INVALID(3451, "机构处于不可用状态"),
    ORGAN_IDS_INVALID(3452, "请输入英文逗号分隔的机构ID串"),
    PARENT_DEPT_STATUS_INVALID(3453, "父部门处于不可用状态"),
    DEPT_STATUS_INVALID(3454, "部门处于不可用状态"),
    DEPT_IDS_INVALID(3455, "请输入英文逗号分隔的部门ID串"),
    PARENT_CHILD_DISORDERLY_ERROR(3456, "指定的层级关系错乱"),
    ROLE_IDS_INVALID(3457, "请输入英文逗号分隔的角色ID串"),
    USER_STATUS_INVALID(3458, "用户处于不可用状态"),
    USER_DEPT_NOT_EXIST(3459, "为用户指定的部门不存在"),
    USER_DEPT_OUT_OF_TENANTID_ERROR(3460, "为用户指定的部门存在不属于该租户的情况"),
    USER_DEPT_OUT_OF_ORGAN_ERROR(34601, "为用户指定的部门存在不属于对应机构的情况"),
    USER_DEPT_STATUS_INVALID(3462, "为用户指定的部门存在处于不可用状态的情况"),
    USER_ORGAN_AND_DEPT_EMPTY_ERROR(3463, "为用户分配的部门与角色不能同时为空"),
    USER_ROLE_OUT_OF_ORGAN_ROLE_ERROR(3464, "为用户指定的角色超出了对应机构的权限范围"),
    PARAM_CODE_REGEX_ERROR(3465, "系统参数代码不符合组成规则"),
    PARAM_VALUE_REGEX_ERROR(3466, "系统参数值不符合组成规则"),
    USER_NOT_SHOPOWNER_ERROR(3467, "当前用户不是店长"),
    PASSWORD_ERROR(3468, "用户密码错误"),
    OLDPASSWORD_ERROR(3469, "旧密码输入错误,请重新输入"),
    NEWPASSWORD_CONPASSWORD_ERROR(3470, "两次输入的新密码不一致,请重新输入"),
    ORDER_STATUS_ERROR(3471, "订单状态异常"),
    CONSUMER_CARD_STATUS_ERROR(3472, "会员卡状态异常"),
    ORDER_CHANGE_AMOUNT_CHECK_ERROR(3473, "订单找零金额校验不通过"),
    CUSTOMERCARDVERIFY_STATUS_ERROR(3474, "核销记录状态异常"),
    SHIFT_CASH_AMOUNT_ERROR(3475, "现金总额异常"),
    USER_SHIFT_STATUS_ERROR(3476, "交接班状态异常"),

    SYSTEM_ERROR(5100, "服务端错误"),
    SYSTEM_TIMEOUT(5200, "服务连接超时"),
    SYSTEM_NOT_FIND(5300, "服务不存在"),
    ;

    private Integer code;

    private String message;

    WechatStatus(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}

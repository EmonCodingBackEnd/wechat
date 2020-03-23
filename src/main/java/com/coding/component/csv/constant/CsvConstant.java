/*
 * 文件名称：CsvConstant.java
 * 系统名称：[系统名称]
 * 模块名称：[模块名称]
 * 软件版权：Copyright (c) 2011-2018, liming20110711@163.com All Rights Reserved.
 * 功能说明：[请在此处输入功能说明]
 * 开发人员：Rushing0711
 * 创建日期：20181213 12:07
 * 修改记录：
 * <Version>        <DateSerial>        <Author>        <Description>
 * 1.0.0            20181213-01         Rushing0711     M201812131207 新建文件
 ********************************************************************************/
package com.coding.component.csv.constant;

public interface CsvConstant {

    /** 逗号. */
    String COMMA = ",";

    /** 冒号. */
    String COLON = ":";

    /** 连字符. */
    String HYPHEN = "-";

    /** 下划线. */
    String UNDERLINE = "_";

    /** 空字符串. */
    String EMPTY = "";

    /** 斜线. */
    String FORWARD_SLASH = "/";

    /** 反斜线. */
    String BACK_SLASH = "\\";

    /** 双斜线. */
    String DOUBLE_FORWARD_SLASH = "//";

    /** 双反斜线. */
    String DOUBLE_BACK_SLASH = "\\\\";

    /** 配置文件名<code>profile.csv</code> */
    String PROFILE_FILE_NAME = "profile.csv";

    /** 代表null字符串 */
    String NULL = "null";

    /** 数据行号（文件第一行为标题行，文件第二行为数据的第一行，注释的行也算数据行，以此类推） */
    String $_ROWNUM = "$_ROWNUM";
}

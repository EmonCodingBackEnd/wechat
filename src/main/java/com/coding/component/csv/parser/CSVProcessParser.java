/*
 * 文件名称：CSVProcessParser.java
 * 系统名称：[系统名称]
 * 模块名称：[模块名称]
 * 软件版权：Copyright (c) 2011-2018, liming20110711@163.com All Rights Reserved.
 * 功能说明：[请在此处输入功能说明]
 * 开发人员：Rushing0711
 * 创建日期：20181213 15:26
 * 修改记录：
 * <Version>        <DateSerial>        <Author>        <Description>
 * 1.0.0            20181213-01         Rushing0711     M201812131526 新建文件
 ********************************************************************************/
package com.coding.component.csv.parser;

import com.coding.component.csv.constant.CsvConstant;

import java.util.Map;

public interface CSVProcessParser {

    /**
     * 功能说明: 使用自定义解析器解析初步校验的文件数据
     *
     * @param rowNum - {@linkplain CsvConstant#$_ROWNUM 数据行号}
     * @param map - 行数据，含有{@linkplain CsvConstant#$_ROWNUM 数据行号}
     * @param csvPath - 文件路径
     * @throws Exception - 文件解析异常
     */
    void processParser(int rowNum, Map<String, String> map, CsvPath csvPath);
}

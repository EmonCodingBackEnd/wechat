/*
 * 文件名称：CSVSupport.java
 * 系统名称：[系统名称]
 * 模块名称：[模块名称]
 * 软件版权：Copyright (c) 2011-2018, liming20110711@163.com All Rights Reserved.
 * 功能说明：[请在此处输入功能说明]
 * 开发人员：Rushing0711
 * 创建日期：20181213 15:34
 * 修改记录：
 * <Version>        <DateSerial>        <Author>        <Description>
 * 1.0.0            20181213-01         Rushing0711     M201812131534 新建文件
 ********************************************************************************/
package com.coding.component.csv.parser;

import java.util.List;

public abstract class CSVSupport {

    private static AbstractCSVParser csvParser = new DefaultCSVParser();

    /**
     * 功能说明: 根据自定义processParser解析CSV文件
     *
     * @param processParser - 自定义解析器
     * @param path - 文件路径
     * @throws Exception
     */
    public static void readCSV(CSVProcessParser processParser, String path) throws Exception {
        csvParser.parse(processParser, path);
    }

    /**
     * 功能说明: 根据自定义processParser解析CSV文件
     *
     * @param processParser - 自定义解析器
     * @param path - 文件路径
     * @param headerTransfer -
     *     启用标题转换，如果启用，Header_Name/header_Name->headerName;header_name->headername
     * @throws Exception
     */
    public static void readCSV(CSVProcessParser processParser, String path, boolean headerTransfer)
            throws Exception {
        csvParser.parse(processParser, path, headerTransfer);
    }

    /**
     * 功能说明: 根据CSV文件解析出目标类的List形式的结果
     *
     * @param <T>
     * @param clazz - 目标类
     * @param path - 文件路径
     * @return 目标类集合
     * @throws Exception
     */
    public static <T> List<T> getListFromCSV(Class<T> clazz, String path) throws Exception {
        return csvParser.parse(clazz, path);
    }

    /**
     * 功能说明: 根据CSV文件解析出目标类的List形式的结果
     *
     * @param <T>
     * @param clazz - 目标类
     * @param path - 文件路径
     * @param headerTransfer -
     *     启用标题转换，如果启用，Header_Name/header_Name->headerName;header_name->headername
     * @return 目标类集合
     * @throws Exception
     */
    public static <T> List<T> getListFromCSV(Class<T> clazz, String path, boolean headerTransfer)
            throws Exception {
        return csvParser.parse(clazz, path, headerTransfer);
    }
}

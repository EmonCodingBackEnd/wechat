/*
 * 文件名称：AbstractCSVParser.java
 * 系统名称：[系统名称]
 * 模块名称：[模块名称]
 * 软件版权：Copyright (c) 2011-2018, liming20110711@163.com All Rights Reserved.
 * 功能说明：[请在此处输入功能说明]
 * 开发人员：Rushing0711
 * 创建日期：20181213 15:02
 * 修改记录：
 * <Version>        <DateSerial>        <Author>        <Description>
 * 1.0.0            20181213-01         Rushing0711     M201812131502 新建文件
 ********************************************************************************/
package com.coding.component.csv.parser;

import com.coding.component.csv.constant.CsvConstant;
import com.coding.component.csv.exception.CsvException;
import com.opencsv.CSVReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.FileReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public abstract class AbstractCSVParser {

    /**
     * 功能说明: 使用自定义解析器解析初步校验的文件数据
     *
     * @param processParser - 自定义解析器
     * @param path 原始文件路径
     * @throws Exception
     */
    public final void parse(CSVProcessParser processParser, String path) throws Exception {
        this.parse(processParser, path, false);
    }

    /**
     * 功能说明: 使用自定义解析器解析初步校验的文件数据
     *
     * @param processParser - 自定义解析器
     * @param path 原始文件路径
     * @param headerTransfer -
     *     原始解析配置，启用标题转换，如果启用，Header_Name/header_Name->headerName;header_name->headername
     * @throws Exception
     */
    public final void parse(CSVProcessParser processParser, String path, boolean headerTransfer)
            throws Exception {
        if (null == processParser) {
            throw new CsvException("processParser不允许为null");
        }
        ruleParser(null, null, path, headerTransfer, processParser);
    }

    /**
     * 功能说明: 解析文件到List
     *
     * @param <T>
     * @param clazz - List的元素类型
     * @param path 文件路径
     * @return 目标类集合
     * @throws Exception
     */
    public final <T> List<T> parse(Class<T> clazz, String path) throws Exception {
        return this.parse(clazz, path, false);
    }

    /**
     * 功能说明: 解析文件到List
     *
     * @param <T>
     * @param clazz - List的元素类型
     * @param path - 文件路径
     * @param headerTransfer -
     *     启用标题转换，如果启用，Header_Name/header_Name->headerName;header_name->headername
     * @return 目标类的集合
     * @throws Exception
     */
    public final <T> List<T> parse(Class<T> clazz, String path, boolean headerTransfer)
            throws Exception {
        List<T> beanList = new ArrayList<>();
        if (null == clazz) {
            throw new CsvException("目标类型clazz不能为空");
        }
        ruleParser(beanList, clazz, path, headerTransfer, null);
        return beanList;
    }

    private <T> void ruleParser(
            List<T> beanList,
            Class<T> clazz,
            String path,
            boolean headerTransfer,
            CSVProcessParser processParser) {
        CsvPath csvPath = new CsvPath(path);
        FileReader fileReader = null;
        CSVReader csvReader = null;
        Map<String, String> map;
        try {
            if (null == csvPath.getPath()) {
                throw new CsvException("文件路径path不能为空");
            }
            URL url = ClassLoader.getSystemResource(csvPath.getPath());
            if (url == null) {
                log.error("文件[{}]不存在", csvPath.getPath());
            } else {
                log.debug("读取文件[{}]开始...", csvPath.getPath());
                fileReader = new FileReader(url.getPath());
                csvReader = new CSVReader(fileReader);
                String[] headerLine = csvReader.readNext();
                if (null != headerLine && headerLine.length > 0) {
                    if (headerTransfer) {
                        for (int i = 0; i < headerLine.length; i++) {
                            headerLine[i] =
                                    StringUtils.uncapitalize(headerLine[i].replace("_", ""));
                        }
                    }

                    String[] columnLine;
                    int rowNum = 0; // 数据行号（文件第一行为标题行，文件第二行为数据的第一行，注释的行也算数据行，以此类推）
                    while ((columnLine = csvReader.readNext()) != null) {
                        rowNum++;
                        if (columnLine[0].startsWith("#")) { // 忽略掉以#开头的行
                            continue;
                        }
                        if (columnLine.length == 1 && columnLine[0].isEmpty()) { // 忽略掉空行
                            continue;
                        }
                        if (columnLine.length != headerLine.length) {
                            log.warn(
                                    "读取文件[{}]第[{}]行与header列数不等，忽略该行",
                                    csvPath.getPath(),
                                    (rowNum + 1));
                            continue;
                        }
                        map = new HashMap<>();
                        for (int i = 0; i < headerLine.length; i++) {
                            if (headerLine[i].startsWith("#")) { // 忽略掉以#开头的列
                                continue;
                            }
                            map.put(headerLine[i].trim(), columnLine[i]);
                        }
                        if (map.size() == 0) { // 全部是注释列，没有必要解析
                            break;
                        }
                        map.put(CsvConstant.$_ROWNUM, String.valueOf(rowNum)); // 存放数据行号

                        // 配置校验，当前行是否应该读取
                        if (isContained(map, csvPath.getRowList())) {
                            if (null != processParser) {
                                processParser.processParser(rowNum, map, csvPath);
                            } else {
                                T bean = parseToBean(clazz, map, csvPath, headerTransfer);
                                beanList.add(bean);
                            }
                        }
                    }
                }
                log.debug("读取文件[{}]完成", csvPath.getPath());
            }
        } catch (Exception e) {
            String errMsg = String.format("读取文件[%s]异常", csvPath.getPath());
            log.error(errMsg, e);
            throw new CsvException(errMsg);
        } finally {
            try {
                if (fileReader != null) {
                    fileReader.close();
                }
                if (csvReader != null) {
                    csvReader.close();
                }
            } catch (Exception e) {
                log.warn(String.format("关闭文件[%s]异常", csvPath.getPath()), e);
            }
        }
    }

    /**
     * 功能说明: 校验当前行是否满足读取条件<br>
     * 校验规则：如果Enabled列满足条件（未配置该列表示满足条件），并且数据行号包含于指定行，则该行允许读取。<br>
     * <em> 优先级Enabled>数据行号<br>
     * Enabled如果已配置，且值为1|true，表示满足条件<br>
     * 数据行号属于指定列(filePath@1-3:5形式的指定），表示满足条件 </em>
     *
     * @param map - 行数据，含有{@linkplain CsvConstant#$_ROWNUM 数据行号}
     * @param rows - 指定的列集合
     * @return 行是否读取：true-读取；false-忽略
     */
    protected boolean isContained(Map<String, String> map, List<Integer> rows) {
        boolean isContained = true;
        boolean needRowFilter = null != rows && rows.size() != 0;

        String enabled = map.get("Enabled");
        if (null != enabled && !enabled.trim().isEmpty()) {
            isContained = enabled.equals("1") || enabled.equals("true");
        }

        if (isContained && needRowFilter) {
            int rowNum = Integer.parseInt(map.get(CsvConstant.$_ROWNUM));
            isContained = rows.indexOf(rowNum) != -1;
        }
        return isContained;
    }

    /**
     * 功能说明: 转换行数据到目标类的实例
     *
     * @param <T>
     * @param clazz - 目标类型
     * @param map - 行数据，含有{@linkplain CsvConstant#$_ROWNUM 数据行号}
     * @param csvPath - 文件路径
     * @param hearderTransfer -
     *     启用标题转换，如果启用，Header_Name/header_Name->headerName;header_name->headername
     * @return 目标类的实例
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws ClassNotFoundException
     */
    protected abstract <T> T parseToBean(
            Class<T> clazz, Map<String, String> map, CsvPath csvPath, boolean hearderTransfer)
            throws InstantiationException, IllegalAccessException, ClassNotFoundException;
}

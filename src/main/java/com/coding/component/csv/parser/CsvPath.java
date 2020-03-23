/*
 * 文件名称：CsvPath.java
 * 系统名称：[系统名称]
 * 模块名称：[模块名称]
 * 软件版权：Copyright (c) 2011-2018, liming20110711@163.com All Rights Reserved.
 * 功能说明：[请在此处输入功能说明]
 * 开发人员：Rushing0711
 * 创建日期：20181213 11:05
 * 修改记录：
 * <Version>        <DateSerial>        <Author>        <Description>
 * 1.0.0            20181213-01         Rushing0711     M201812131105 新建文件
 ********************************************************************************/
package com.coding.component.csv.parser;

import com.coding.component.csv.constant.CsvConstant;
import com.coding.component.csv.exception.CsvException;
import com.coding.component.csv.util.ParserUtils;
import com.coding.component.regex.RegexDefine;
import com.coding.component.regex.RegexResult;
import com.coding.component.regex.RegexSupport;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

@Data
public class CsvPath {

    /**
     * 获取CSV路径中的文件路径信息.
     *
     * <p>创建时间: <font style="color:#00FFFF">20181213 11:36</font><br>
     * 文件路径-filePath
     *
     * @since 1.0.0
     */
    private String path;

    /**
     * 获取CSV路径中指定行列表信息.
     *
     * <p>创建时间: <font style="color:#00FFFF">20181213 11:35</font><br>
     * 指定行列表-[1,2,3,5,6,7,8]-去除了重复行，并排序
     *
     * @since 1.0.0
     */
    private List<Integer> rowList;

    /**
     * 构造说明: 传入形如filePath@1-3:5:5-8的CSVPath信息，解析出包含的文件路径信息和指定行列表信息
     *
     * @param path
     */
    public CsvPath(String path) {
        if (null == path || path.trim().isEmpty()) {
            return;
        }
        String cfgName = ParserUtils.getFileName(path);
        RegexResult result = RegexSupport.matchSingleCSVFile(cfgName);
        if (!result.isMatched()) {
            throw new CsvException(String.format("CSV文件路径[%s]格式不正确", path));
        }
        String[] paths = path.split("@");
        this.path = paths[0];
        if (paths.length == 1) {
            this.rowList = new ArrayList<>();
            return;
        }
        rowList = fetchDistinctAndOrderedRowNumListBySeparation(paths[1]);
    }

    /**
     * 根据指定的行列表的表达式，获取无重复有序的指定行信息.
     *
     * <p>创建时间: <font style="color:#00FFFF">20181213 11:59</font><br>
     * [请在此输入功能详述]
     *
     * @param rowExpression - 指定的行列表表达式，例如：1-3:5:5-8
     * @return 指定行列表-[1,2,3,5,6,7,8]-去除了重复行，并排序
     * @author Rushing0711
     * @since 1.0.0
     */
    private List<Integer> fetchDistinctAndOrderedRowNumListBySeparation(String rowExpression) {
        List<Integer> rowList = new ArrayList<>();
        if (StringUtils.isEmpty(rowExpression)) {
            return rowList;
        }
        rowExpression = StringUtils.trimAllWhitespace(rowExpression);

        Pattern pattern = RegexDefine.POSITIVE_INTEGER_REGEX_PATTERN;
        // 分割CSVPath
        String[] rows = rowExpression.split(CsvConstant.COLON);
        for (String row : rows) {
            if (row.trim().isEmpty()) {
                continue;
            }
            boolean isRange = false;
            String[] range = null;
            // 是否是区间？
            if (row.contains(CsvConstant.HYPHEN)) {
                range = row.split(CsvConstant.HYPHEN);
                if (range.length == 2) {
                    isRange = true;
                }
            }
            if (isRange) {
                // 如果区间的两端不是数字，则是无效区间，忽略！
                if (!pattern.matcher(range[0]).matches() || !pattern.matcher(range[1]).matches()) {
                    continue;
                }
                int start = Integer.parseInt(range[0]);
                int end = Integer.parseInt(range[1]);
                for (int i = start; i <= end; i++) {
                    // 不添加重复的行号
                    if (!rowList.contains(i)) {
                        rowList.add(i);
                    }
                }
            } else {
                if (!pattern.matcher(row).matches()) {
                    continue;
                }
                int rowNum = Integer.parseInt(row);
                if (!rowList.contains(rowNum)) {
                    rowList.add(rowNum);
                }
            }
        }
        Collections.sort(rowList);
        return rowList;
    }
}

/*
 * 文件名称：ParserUtils.java
 * 系统名称：[系统名称]
 * 模块名称：[模块名称]
 * 软件版权：Copyright (c) 2011-2018, liming20110711@163.com All Rights Reserved.
 * 功能说明：[请在此处输入功能说明]
 * 开发人员：Rushing0711
 * 创建日期：20181213 12:22
 * 修改记录：
 * <Version>        <DateSerial>        <Author>        <Description>
 * 1.0.0            20181213-01         Rushing0711     M201812131222 新建文件
 ********************************************************************************/
package com.coding.component.csv.util;

import com.coding.component.csv.constant.CsvConstant;
import com.coding.component.csv.bean.Profile;
import com.coding.component.csv.parser.CSVSupport;
import com.coding.component.csv.parser.CsvPath;
import com.coding.component.regex.RegexResult;
import com.coding.component.regex.RegexSupport;
import lombok.Data;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public abstract class ParserUtils {

    /**
     * 获取文件路径，去掉前端文件分隔符，保留后端文件分隔符.
     *
     * <p>创建时间: <font style="color:#00FFFF">20181213 12:31</font><br>
     * [请在此输入功能详述]
     *
     * @param path - 待转换的路径
     * @return 转换后的路径
     * @author Rushing0711
     * @since 1.0.0
     */
    public static String getFilePath(String path) {
        if (path == null) {
            return CsvConstant.EMPTY;
        }

        path = path.replace(CsvConstant.DOUBLE_BACK_SLASH, CsvConstant.FORWARD_SLASH);
        if (path.startsWith(CsvConstant.FORWARD_SLASH)) {
            path = path.substring(1);
        }
        if (!path.endsWith(CsvConstant.FORWARD_SLASH)) {
            path = path + CsvConstant.FORWARD_SLASH;
        }
        return path.trim();
    }

    /**
     * 获取CSV文件名，去除两端的文件分隔符，然后获取最后一个文件分隔符（如果存在）后面的内容，或者全部内容.
     *
     * <p>创建时间: <font style="color:#00FFFF">20181213 12:23</font><br>
     * [请在此输入功能详述]
     *
     * @param fileName - 待转换的文件名
     * @return 转换后的文件名
     * @author Rushing0711
     * @since 1.0.0
     */
    public static String getFileName(String fileName) {
        if (fileName == null) return CsvConstant.EMPTY;

        fileName = fileName.replace(CsvConstant.DOUBLE_BACK_SLASH, CsvConstant.FORWARD_SLASH);
        while (fileName.contains(CsvConstant.DOUBLE_FORWARD_SLASH)) {
            fileName =
                    fileName.replace(CsvConstant.DOUBLE_FORWARD_SLASH, CsvConstant.FORWARD_SLASH);
        }
        if (fileName.endsWith(CsvConstant.FORWARD_SLASH)) {
            fileName = fileName.substring(0, fileName.length() - 1);
        }
        int index = fileName.lastIndexOf(CsvConstant.FORWARD_SLASH);
        if (index != -1) {
            fileName = fileName.substring(index + 1);
        }
        return fileName.trim();
    }

    /**
     * 功能说明: 获取CSV文件名中的纯文件名作为表名<br>
     * <code>例如：filePath@1-3:5->filePath</code>
     *
     * @param fileName - 文件路径，可能包含其他信息
     * @return 纯文件名，作为表名
     */
    public static String getTableFromFileName(String fileName) {
        fileName = getFileName(fileName);
        return fileName.substring(0, fileName.lastIndexOf(".")).trim();
    }

    /**
     * 功能说明: 解析profile.csv配置，返回形如： Map&lt;Integer,List&lt;String&gt;&gt;格式的数据。<br>
     * 其中Integer表示profile.csv的行，List&lt;String&gt;表示CSV文件列表。
     *
     * @param filePath - 指定一个包含profile.csv文件的目录
     * @return 解析profile.csv得到的CSV配置文件信息。如果解析失败，返回不包含任何元素的Map。
     */
    public static Map<Integer, List<String>> getValidCSVFiles(String filePath) {
        return getValidCSVFiles(filePath, "profile.csv");
    }

    /**
     * 功能说明: 解析profile.csv配置，返回形如： Map&lt;Integer,List&lt;String&gt;&gt;格式的数据。<br>
     * 其中Integer表示profile.csv的行，List&lt;String&gt;表示CSV文件列表。
     *
     * @param filePath - 指定一个包含profile.csv文件的目录
     * @param profileCfg - 如果只提取profile.csv文件的部分数据，可以通过该参数指定，形如： profile.csv@1:5-8
     * @return - 解析profile.csv得到的CSV配置文件信息。如果解析失败，返回不包含任何元素的Map。
     */
    public static Map<Integer, List<String>> getValidCSVFiles(String filePath, String profileCfg) {
        Map<Integer, List<String>> indexCSVFileMap = new HashMap<Integer, List<String>>();
        try {
            String profileFileName = CsvConstant.PROFILE_FILE_NAME;

            CsvPath csvPath = new CsvPath(filePath + profileCfg);
            if (null == csvPath.getPath()) {
                String errMsg = String.format("文件路径[%s]配置不正确", profileCfg);
                throw new Exception(errMsg);
            }

            String cfgName = ParserUtils.getFileName(csvPath.getPath());
            if (!profileFileName.equals(cfgName)) {
                String errMsg = String.format("配置[%s]必须基于[%s]", profileCfg, profileFileName);
                throw new Exception(errMsg);
            }

            // 校验是否包含profile.csv文件
            String[] profiles = PubMethods.filterFileByPrefix(filePath, profileFileName);
            if (null == profiles || profiles.length == 0) {
                String errMsg = String.format("目录[%s]下缺少[%s]文件，请检查", filePath, profileFileName);
                throw new Exception(errMsg);
            }

            // 获取profile.csv之外的CSV文件
            String[] notProfiles =
                    PubMethods.filterFile(
                            filePath,
                            new FilenameFilter() {

                                @Override
                                public boolean accept(File dir, String name) {
                                    return name.endsWith(".csv") && !PubMethods.isProfiles(name);
                                }
                            });
            List<String> notProfileList = Arrays.asList(notProfiles);

            // 获取profile.csv中配置的文件列表
            List<Profile> profileList =
                    CSVSupport.getListFromCSV(Profile.class, filePath + profileCfg);
            for (Profile prof : profileList) {
                String profile = prof.getProfile();
                if (null == profile || profile.trim().isEmpty()) {
                    continue;
                }
                RegexResult multiResult = RegexSupport.matchMultiCSVFile(profile);
                if (!multiResult.isMatched()) {
                    String errMsg =
                            String.format(
                                    "目录[%s]下profile.csv文件第[%s]行%s配置格式不正确",
                                    filePath, prof.get$RowNum(), prof);
                    throw new Exception(errMsg);
                }
                String[] files = profile.trim().split("\\|");
                for (String file : files) {
                    RegexResult result = RegexSupport.matchSingleCSVFile(file);
                    String fileName = result.getResult("fileName");
                    if (notProfileList.indexOf(fileName) == -1) {
                        String errMsg =
                                String.format(
                                        "目录[%s]下profile.csv文件第[%s]行%s配置的CSV文件不存在",
                                        filePath, prof.get$RowNum(), prof);
                        throw new Exception(errMsg);
                    }
                }
                indexCSVFileMap.put(prof.get$RowNum(), Arrays.asList(files));
            }
        } catch (Exception e) {
            //            LogSupport.getCaseLog().error(e, "解析目录[{}]下CSV文件失败", filePath);
            indexCSVFileMap = new HashMap<>();
        }
        return indexCSVFileMap;
    }
}

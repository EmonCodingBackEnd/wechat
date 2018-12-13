/*
 * 文件名称：PubMethods.java
 * 系统名称：[系统名称]
 * 模块名称：[模块名称]
 * 软件版权：Copyright (c) 2011-2018, liming20110711@163.com All Rights Reserved.
 * 功能说明：[请在此处输入功能说明]
 * 开发人员：Rushing0711
 * 创建日期：20181213 14:31
 * 修改记录：
 * <Version>        <DateSerial>        <Author>        <Description>
 * 1.0.0            20181213-01         Rushing0711     M201812131431 新建文件
 ********************************************************************************/
package com.coding.component.csv.util;

import com.coding.component.csv.constant.CsvConstant;
import com.coding.component.regex.RegexResult;
import com.coding.component.regex.RegexSupport;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FilenameFilter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public abstract class PubMethods {

    /**
     * 字段说明: 如果文件中配置了某列，但是无需用例实际使用，配置到这里可以在使用时根据{@link #is$Header(String...)}返回结果进行过滤<br>
     * 系统内部标题列：
     *
     * <ul>
     *   <li>Enabled
     *   <li>{@linkplain CsvConstant#$_ROWNUM $_ROWNUM}
     * </ul>
     */
    private static List<String> $_HEADER_LIST = null;

    static {
        $_HEADER_LIST = new ArrayList<>();
        $_HEADER_LIST.add("Enabled");
        $_HEADER_LIST.add(CsvConstant.$_ROWNUM);
    }

    /**
     * 功能说明: 判断给定header是否是{@linkplain #$_HEADER_LIST 系统内部标题列}
     *
     * @param headers - 给定标题数组
     * @return 判断结果：true-全部是系统内部标题列；false-非全部是系统内部标题列
     */
    public static boolean is$Header(String... headers) {
        boolean is$Header = true;
        for (String header : headers) {
            if (!$_HEADER_LIST.contains(header)) {
                is$Header = false;
                break;
            }
        }
        return is$Header;
    }

    /**
     * 功能说明: 是否是CSV配置描述文件<code>profile.csv</code><br>
     * 如果给定的CSV配置文件不合法，或者合法，但并不是CSV配置描述文件，则返回false。<br>
     * 该方法并不区分是因为CSV配置文件不合法导致的结果false，还是由于不是CSV配置描述文件导致的结果false。<br>
     * 示例：<br>
     *
     * <ul>
     *   <li><font color="red">abc.csv->false</font>
     *   <li><font color="red">abc.csv@1->false</font>
     *   <li><font color="red">abc.txt->false</font>
     *   <li><font color="green">profile.csv->true</font>
     *   <li><font color="green">profile.csv@1->true</font>
     * </ul>
     *
     * @param profiles - 给定一组CSV配置文件，形如：<code>fileName.csv@1:3-8</code>
     * @return 判断结果：true-全部是CSV配置描述文件；false-非全部是CSV配置描述文件
     */
    public static boolean isProfiles(String... profiles) {
        boolean isProfile = true;
        for (String profile : profiles) {
            RegexResult result = RegexSupport.matchSingleCSVFile(profile);
            if (!result.isMatched()) {
                isProfile = false;
                break;
            }
            String fileName = result.getResult("fileName");
            if (!"profile.csv".equals(fileName)) {
                isProfile = false;
                break;
            }
        }
        return isProfile;
    }

    /**
     * 功能说明: 过滤指定目录下，指定后缀的文件列表。
     *
     * @param filePath - 文件目录
     * @param suffix - 文件后缀
     * @return 指定目录下过滤到的文件列表，不存在则返回空数组，过滤异常则返回null。
     */
    public static String[] filterFileBySuffix(String filePath, final String suffix) {
        FilenameFilter filenameFilter = (dir, name) -> name.endsWith(suffix);
        return filterFile(filePath, filenameFilter);
    }

    /**
     * 功能说明: 过滤指定目录下，指定前缀的文件列表。
     *
     * @param filePath - 文件目录
     * @param prefix - 文件前缀
     * @return 指定目录下过滤到的文件列表，不存在则返回空数组，过滤异常则返回null。
     */
    public static String[] filterFileByPrefix(String filePath, final String prefix) {
        FilenameFilter filenameFilter = (dir, name) -> name.startsWith(prefix);
        return filterFile(filePath, filenameFilter);
    }

    /**
     * 功能说明: 过滤指定目录下，指定后缀的文件列表。
     *
     * @param filePath 文件目录
     * @param filenameFilter 文件名过滤器
     * @return 指定目录下过滤到的文件列表，不存在则返回空数组，过滤异常则返回null。
     */
    public static String[] filterFile(String filePath, FilenameFilter filenameFilter) {
        String[] files;
        try {
            URL url = ClassLoader.getSystemResource(filePath);
            File fileDir = new File(url.getFile());
            files = fileDir.list(filenameFilter);
        } catch (Exception e) {
            log.error(String.format("过滤目录[%s]下满足条件的文件列表失败！", filePath), e);
            files = null;
        }
        return files;
    }
}

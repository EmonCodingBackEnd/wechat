/*
 * 文件名称：RegexSupport.java
 * 系统名称：[系统名称]
 * 模块名称：正则表达式匹配解析类
 * 软件版权：Copyright (c) 2011-2018, liming20110711@163.com All Rights Reserved.
 * 功能说明：[请在此处输入功能说明]
 * 开发人员：Rushing0711
 * 创建日期：20180611 00:46
 * 修改记录：
 * <Version>        <DateSerial>        <Author>        <Description>
 * 1.0.0            20180611-01         Rushing0711     M201806110046 新建文件
 ********************************************************************************/
package com.coding.wechat.component.regex;

import com.coding.wechat.component.regex.result.MobileRegexResult;
import com.coding.wechat.component.regex.result.UriRegexResult;
import com.coding.wechat.component.regex.result.UrlParamRegexResult;
import com.coding.wechat.component.regex.result.UrlRegexResult;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

/**
 * 正则表达式匹配解析类.
 *
 * <p>创建时间: <font style="color:#00FFFF">20180611 00:46</font><br>
 * [请在此输入功能详述]
 *
 * @author Rushing0711
 * @version 1.0.0
 * @since 1.0.0
 */
public abstract class RegexSupport {

    /**
     * 功能说明: 解析交易请求报文的引用变量
     *
     * @param value - 引用变量
     * @return 解析结果
     */
    public static RegexResult match$R(String value) {
        RegexResult result = RegexResult.instance();
        Matcher matcher = RegexDefine.$R_REGEX_PATTERN.matcher(value);
        if (matcher.matches()) {
            result.setMatched(true);
            result.setRegex(RegexDefine.$R_REGEX);
            result.setPattern(RegexDefine.$R_REGEX_PATTERN);
            result.setMatcher(matcher);
            result.setRawValue(matcher.group(0));
            result.setResult("refKey", matcher.group(2));
        }
        return result;
    }

    /**
     * 功能说明: 解析主机请求报文的引用变量
     *
     * @param value - 引用变量
     * @return 解析结果
     */
    public static RegexResult match$H(String value) {
        RegexResult result = RegexResult.instance();
        Matcher matcher = RegexDefine.$H_REGEX_PATTERN.matcher(value);
        if (matcher.matches()) {
            result.setMatched(true);
            result.setRegex(RegexDefine.$H_REGEX);
            result.setPattern(RegexDefine.$H_REGEX_PATTERN);
            result.setMatcher(matcher);
            result.setRawValue(matcher.group(0));
            result.setResult("refKey", matcher.group(2));
        }
        return result;
    }

    /**
     * 功能说明: 解析交易应答报文的引用变量
     *
     * @param value - 引用变量
     * @return 解析结果
     */
    public static RegexResult match$S(String value) {
        RegexResult result = RegexResult.instance();
        Matcher matcher = RegexDefine.$S_REGEX_PATTERN.matcher(value);
        if (matcher.matches()) {
            result.setMatched(true);
            result.setRegex(RegexDefine.$S_REGEX);
            result.setPattern(RegexDefine.$S_REGEX_PATTERN);
            result.setMatcher(matcher);
            result.setRawValue(matcher.group(0));
            result.setResult("resultKey", matcher.group(2));
            result.setResult("refKey", matcher.group(3));
        }
        return result;
    }

    /**
     * 功能说明: 解析缓存中的数据库引用变量
     *
     * @param value - 引用变量
     * @return 解析结果
     */
    public static RegexResult match$TCache(String value) {
        RegexResult result = RegexResult.instance();
        Matcher matcher = RegexDefine.$T_CACHE_REGEX_PATTERN.matcher(value);
        if (matcher.matches()) {
            result.setMatched(true);
            result.setRegex(RegexDefine.$T_CACHE_REGEX);
            result.setPattern(RegexDefine.$T_CACHE_REGEX_PATTERN);
            result.setMatcher(matcher);
            result.setRawValue(matcher.group(0));
            result.setResult("SeqNum", Integer.parseInt(matcher.group(2)));
            String RowNum = matcher.group(4);
            result.setResult("RowNum", null == RowNum ? null : Integer.parseInt(matcher.group(4)));
            result.setResult("refCol", matcher.group(5));
        }
        return result;
    }

    /**
     * 功能说明: 解析数据库引用变量
     *
     * @param value - 引用变量
     * @return 解析结果
     */
    public static RegexResult match$T(String value) {
        RegexResult result = RegexResult.instance();
        Matcher matcher = RegexDefine.$T_REGEX_PATTERN.matcher(value);
        if (matcher.matches()) {
            result.setMatched(true);
            result.setRegex(RegexDefine.$T_REGEX);
            result.setPattern(RegexDefine.$T_REGEX_PATTERN);
            result.setMatcher(matcher);
            result.setRawValue(matcher.group(0));
            result.setResult("tableName", matcher.group(2));
            result.setResult("keyCol", matcher.group(3));
            result.setResult("refCol", matcher.group(13));
        }
        return result;
    }

    /**
     * 功能说明: 解析混合引用变量
     *
     * @param value - 混合类型的引用变量
     * @return 解析结果
     */
    public static RegexResult matchFETCH_$RHST(String value) {
        RegexResult result = RegexResult.instance();
        Matcher matcher = RegexDefine.FETCH_$RHST_REGEX_PATTERN.matcher(value);
        List<String> fetchList = new ArrayList<String>();
        boolean found = false;
        RegexResult $TCache;
        List<RegexResult> $TCacheList = new ArrayList<RegexResult>();
        while (matcher.find()) {
            fetchList.add(matcher.group());
            $TCache = match$TCache(matcher.group(3));
            if ($TCache.isMatched()) {
                $TCacheList.add($TCache);
            }
            found = true;
        }
        if (found) {
            result.setMatched(true);
            result.setRegex(RegexDefine.FETCH_$RHST_REGEX);
            result.setPattern(RegexDefine.FETCH_$RHST_REGEX_PATTERN);
            matcher.reset();
            result.setMatcher(matcher);
            result.setRawValue(value);
            result.setResult("fetchList", fetchList);
            result.setResult("$TCacheList", $TCacheList);
        }
        return result;
    }

    /**
     * 功能说明: 解析格式化的日志
     *
     * @param value - 格式化的日志
     * @return 解析结果
     */
    public static RegexResult matchLogFormater(String value) {
        RegexResult result = RegexResult.instance();
        Matcher matcher = RegexDefine.LOG_FORMATER_REGEX_PATTERN.matcher(value);
        boolean found = false;
        int index = 0;
        String formattedValue = null;
        while (matcher.find()) {
            formattedValue = matcher.replaceFirst(String.format("{%d}", index++));
            matcher.reset(formattedValue);
            found = true;
        }
        if (found) {
            result.setMatched(true);
            result.setRegex(RegexDefine.LOG_FORMATER_REGEX);
            result.setPattern(RegexDefine.LOG_FORMATER_REGEX_PATTERN);
            matcher.reset(value);
            result.setMatcher(matcher);
            result.setRawValue(value);
            result.setResult("formattedValue", formattedValue);
        }
        return result;
    }

    public static MobileRegexResult matchMobile(String value) {
        MobileRegexResult result = MobileRegexResult.instance();
        Matcher matcher = RegexDefine.MOBILE_REGEX_PATTERN.matcher(value);
        if (matcher.matches()) {
            result.setMatched(true);
            result.setRegex(
                    "^((?:13[0-9])|(?:14[5|7])|(?:15(?:[0-3]|[5-9]))|(?:17[013678])|(?:18[0,5-9]))\\d{4}(\\d{4})$");
            result.setPattern(RegexDefine.MOBILE_REGEX_PATTERN);
            result.setMatcher(matcher);
            result.setMobile(matcher.group(0));
            result.setMobileHead(matcher.group(1));
            result.setMobileTail(matcher.group(2));
        }
        return result;
    }

    public static UrlRegexResult matchUrl(String value) {
        UrlRegexResult result = UrlRegexResult.instance();
        Matcher matcher = RegexDefine.URL_REGEX_PATTERN.matcher(value);
        if (matcher.matches()) {
            result.setMatched(true);
            result.setRegex(RegexDefine.URL_REGEX);
            result.setPattern(RegexDefine.URL_REGEX_PATTERN);
            result.setMatcher(matcher);
            result.setUrl(matcher.group(0));
            result.setUri(matcher.group(1));
            result.setSchema(matcher.group(2));
            result.setPath(matcher.group(3));
            result.setParam(matcher.group(4));
        }
        return result;
    }

    public static UriRegexResult matchUri(String value) {
        UriRegexResult result = UriRegexResult.instance();
        Matcher matcher = RegexDefine.URI_REGEX_PATTERN.matcher(value);
        if (matcher.matches()) {
            result.setMatched(true);
            result.setRegex(RegexDefine.URI_REGEX);
            result.setPattern(RegexDefine.URI_REGEX_PATTERN);
            result.setMatcher(matcher);
            result.setUri(matcher.group(0));
            result.setSchema(matcher.group(1));
            result.setPath(matcher.group(2));
        }
        return result;
    }

    public static UrlParamRegexResult matchUrlParam(String value) {
        UrlParamRegexResult result = UrlParamRegexResult.instance();
        Matcher matcher = RegexDefine.URL_PARAM_REGEX_PATTERN.matcher(value);
        if (matcher.matches()) {
            result.setMatched(true);
            result.setRegex(RegexDefine.URL_PARAM_REGEX);
            result.setPattern(RegexDefine.URL_PARAM_REGEX_PATTERN);
            result.setMatcher(matcher);
            result.setParam(matcher.group(1));
        }
        return result;
    }
}

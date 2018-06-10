/*
 * 文件名称：RegexDefine.java
 * 系统名称：[系统名称]
 * 模块名称：正则表达式定义类
 * 软件版权：Copyright (c) 2011-2018, liming20110711@163.com All Rights Reserved.
 * 功能说明：[请在此处输入功能说明]
 * 开发人员：Rushing0711
 * 创建日期：20180611 00:44
 * 修改记录：
 * <Version>        <DateSerial>        <Author>        <Description>
 * 1.0.0            20180611-01         Rushing0711     M201806110044 新建文件
 ********************************************************************************/
package com.coding.wechat.component.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式定义类.
 *
 * <p>创建时间: <font style="color:#00FFFF">20180611 00:44</font><br>
 * [请在此输入功能详述]
 *
 * @author Rushing0711
 * @version 1.0.0
 * @since 1.0.0
 */
public abstract class RegexDefine {

    /** 正整数正则表达式 */
    public static final String POSITIVE_INTEGER_REGEX = "^[1-9]\\d*$";

    /** {@linkplain #POSITIVE_INTEGER_REGEX} */
    public static final Pattern POSITIVE_INTEGER_REGEX_PATTERN =
            Pattern.compile(POSITIVE_INTEGER_REGEX);

    /** CSV文件配置的正则表达式，匹配示例： abc1.csv abc1.csv@1-5:8:10 abc1.csv@1-5:8:10|abc2.csv@1-3:5:8-20... */
    public static final String CSV_FILE_CONFIG_REGEX =
            "^\\w+\\.csv(@([1-9]\\d*)+((:|-)([1-9]\\d*)+)*)?(\\|\\w+\\.csv(@([1-9]\\d*)+((:|-)([1-9]\\d*)+)*)?)*$";

    /** {@linkplain #CSV_FILE_CONFIG_REGEX} */
    public static final Pattern CSV_FILE_CONFIG_REGEX_PATTERN =
            Pattern.compile(CSV_FILE_CONFIG_REGEX);

    /**
     * <span style="color:green;font-size:16px"> 匹配交易请求报文依赖<br/>
     * 正则： <code>^(\$R)\.(\w+)$<code><br/>
     * 定义：<code>$R.key</code><br/>
     * 示例：<code>$R.TransCode</code></span>
     */
    public static final String $R_REGEX = "^(\\$R)\\.(\\w+)$";

    /** {@linkplain #$R_REGEX} */
    public static final Pattern $R_REGEX_PATTERN = Pattern.compile($R_REGEX);

    /**
     * <span style="color:green;font-size:16px"> 匹配主机请求报文依赖<br/>
     * 正则： <code>^(\$H)\.(\w+)$<code><br/>
     * 定义：<code>$H.key</code><br/>
     * 示例：<code>$H.ExSerial</code></span>
     */
    public static final String $H_REGEX = "^(\\$H)\\.(\\w+)$";

    /** {@linkplain #$H_REGEX} */
    public static final Pattern $H_REGEX_PATTERN = Pattern.compile($H_REGEX);

    /**
     * <span style="color:green;font-size:16px"> 匹配交易应答报文依赖<br/>
     * 正则： <code>^(\$S)\.(\w+)\.(\w+)$<code><br/>
     * 定义：<code>$S.resultKey.key</code><br/>
     * 示例：<code>$S.result.SerialNo</code></span>
     */
    public static final String $S_REGEX = "^(\\$S)\\.(\\w+).(\\w+)$";

    /** {@linkplain #$S_REGEX} */
    public static final Pattern $S_REGEX_PATTERN = Pattern.compile($S_REGEX);

    /**
     * <span style="color:green;font-size:16px"> 匹配缓存中数据库依赖<br/>
     * 正则： <code>^(\$T)\.([1-9]\d*)(\[([1-9]\d*)\])?\[(\w+)\]$<code><br/>
     * 定义：<code>$T.SeqNum[RowNum][refCol]</code><br/>
     * 示例：<code>$T.1[10][in_client_no]</code></span>
     */
    public static final String $T_CACHE_REGEX =
            "^(\\$T)\\.([1-9]\\d*)(\\[([1-9]\\d*)\\])?\\[(\\w+)\\]$";

    /** {@linkplain #$T_CACHE_REGEX} */
    public static final Pattern $T_CACHE_REGEX_PATTERN = Pattern.compile($T_CACHE_REGEX);

    /**
     * <span style="color:green;font-size:16px"> 匹配数据库依赖<br/>
     * 正则：
     * <code>^(\$T)\.(\w+)\[(((\w+)\|((\$[R|H]\.|\$S\.\w+\.)?[\w\u4e00-\u9fa5]+))(&((\w+)\|((\$[R|H]\.|\$S\.\w+\.)?[\w\u4e00-\u9fa5]+)))*)\]\[(\w+)\]$<code><br/>
     * 定义：<code>$T.tableName[key1|val1&key2|$R.BankNo&key3|$H.ExSerial&key4|$S.result.SerialNO...][refCol]</code><br/>
     * 示例：<code>$T.tbsysarg[seller_code|022&status|0][init_date]</code></span>
     *
     */
    public static final String $T_REGEX =
            "^(\\$T)\\.(\\w+)\\[(((\\w+)\\|((\\$[R|H]\\.|\\$S\\.\\w+\\.)?[\\w\\u4e00-\\u9fa5]+))(&((\\w+)\\|((\\$[R|H]\\.|\\$S\\.\\w+\\.)?[\\w\\u4e00-\\u9fa5]+)))*)\\]\\[(\\w+)\\]$";

    /** {@linkplain #$T_REGEX} */
    public static final Pattern $T_REGEX_PATTERN = Pattern.compile($T_REGEX);

    /**
     * <span style="color:green;font-size:16px"> 匹配字符串中的引用变量<br/>
     * 正则：
     * <code>((\w+)=(((\$[R|H])\.(\w+))|((\$S)\.(\w+)\.(\w+))|((\$T)\.([1-9]\d*)(\[([1-9]\d*)\])?\[(\w+)\])|((\$T)\.(\w+)\[(((\w+)\|((\$[R|H]\.|\$S\.\w+\.)?[\w\u4e00-\u9fa5]+))(&((\w+)\|((\$[R|H]\.|\$S\.\w+\.)?[\w\u4e00-\u9fa5]+)))*)\]\[(\w+)\])))<code><br/>
     * 定义：where key1=val1 and key2=$R.key and key3=$H.key and key4=$S.resultKey.key and key5=$T.1[2][refCol] and key6=$T.tableName[key61|val61&key62|$R.BankNo&key63|$H.ExSerial&key64|$S.result.SerialNO][refCol]<br/>
     */
    public static final String FETCH_$RHST_REGEX =
            "((\\w+)=(((\\$[R|H])\\.(\\w+))|((\\$S)\\.(\\w+)\\.(\\w+))|((\\$T)\\.([1-9]\\d*)(\\[([1-9]\\d*)\\])?\\[(\\w+)\\])|((\\$T)\\.(\\w+)\\[(((\\w+)\\|((\\$[R|H]\\.|\\$S\\.\\w+\\.)?[\\w\\u4e00-\\u9fa5]+))(&((\\w+)\\|((\\$[R|H]\\.|\\$S\\.\\w+\\.)?[\\w\\u4e00-\\u9fa5]+)))*)\\]\\[(\\w+)\\])))";
    //	public static final String FETCH_$RHST_REGEX =
    // "((\\w+)=(((\\$[R|H])\\.(\\w+))|((\\$S)\\.(\\w+)\\.(\\w+))|((\\$T)\\.(\\w+)\\[([1-9]\\d*)(\\-([1-9]\\d*))?\\]\\[(\\w+)\\])|((\\$T)\\.(\\w+)\\[(((\\w+)\\|((\\$[R|H]\\.|\\$S\\.\\w+\\.)?[\\w\\u4e00-\\u9fa5]+))(&((\\w+)\\|((\\$[R|H]\\.|\\$S\\.\\w+\\.)?[\\w\\u4e00-\\u9fa5]+)))*)\\]\\[(\\w+)\\])))";

    /** {@linkplain #FETCH_$RHST_REGEX} */
    public static final Pattern FETCH_$RHST_REGEX_PATTERN = Pattern.compile(FETCH_$RHST_REGEX);

    /**
     * 字段说明: <span style="color:green;font-size:16px"> 日志格式化正则表达式<br>
     * 正则：<code>{}</code> 定义：{}决定重新改写{}日志的格式化方式为{}
     */
    public static final String LOG_FORMATER_REGEX = "\\{\\}";

    /** 字段说明: {@linkplain #LOG_FORMATER_REGEX} */
    public static final Pattern LOG_FORMATER_REGEX_PATTERN = Pattern.compile(LOG_FORMATER_REGEX);

    public static final String MOBILE_REGEX =
            "^((?:13[0-9])|(?:14[5|7])|(?:15(?:[0-3]|[5-9]))|(?:17[013678])|(?:18[0,5-9]))\\d{4}(\\d{4})$";
    public static final Pattern MOBILE_REGEX_PATTERN =
            Pattern.compile(
                    "^((?:13[0-9])|(?:14[5|7])|(?:15(?:[0-3]|[5-9]))|(?:17[013678])|(?:18[0,5-9]))\\d{4}(\\d{4})$");

    // (((^https?:(?:\/\/)?)(?:[-;:&=\+\$,\w]+@)?[A-Za-z0-9.-]+|(?:www.|[-;:&=\+\$,\w]+@)[A-Za-z0-9.-]+)((?:\/[\+~%\/.\w-_]*)?\??(?:[-\+=&;%@.\w_]*)#?(?:[\w]*))?)$
    public static final String URL_REGEX =
            "(^(https?)://(?:[\\w:.-]+)?((?:\\/[\\+~%\\/.\\w-_]*)?))\\??([-\\+=&;%@.\\w_]*)#?(?:[\\w]*)$";
    public static final Pattern URL_REGEX_PATTERN = Pattern.compile(URL_REGEX);

    public static final String URI_REGEX = "^(https?)://(?:[\\w:.-]+)?((?:\\/[\\+~%\\/.\\w-_]*)?)$";
    public static final Pattern URI_REGEX_PATTERN = Pattern.compile(URI_REGEX);

    public static final String URL_PARAM_REGEX = "\\??([-\\+=&;%@.\\w_]*)#?(?:[\\w]*)";
    public static final Pattern URL_PARAM_REGEX_PATTERN = Pattern.compile(URL_PARAM_REGEX);

    public static void main(String[] args) {
        String $RValue = "$R.key";
        Pattern $RPattern = $R_REGEX_PATTERN;
        Matcher $RMatcher = $RPattern.matcher($RValue);
        if ($RMatcher.matches()) {
            System.out.println($R_REGEX);
            for (int i = 0; i <= $RMatcher.groupCount(); i++) {
                System.out.println(String.format("group(%d)=%s", i, $RMatcher.group(i)));
            }
        }
        System.out.println();

        String $HValue = "$H.key";
        Pattern $HPattern = $H_REGEX_PATTERN;
        Matcher $HMatcher = $HPattern.matcher($HValue);
        if ($HMatcher.matches()) {
            System.out.println($H_REGEX);
            for (int i = 0; i <= $HMatcher.groupCount(); i++) {
                System.out.println(String.format("group(%d)=%s", i, $HMatcher.group(i)));
            }
        }
        System.out.println();

        String $SValue = "$S.resultKey.key";
        Pattern $SPattern = $S_REGEX_PATTERN;
        Matcher $SMatcher = $SPattern.matcher($SValue);
        if ($SMatcher.matches()) {
            System.out.println($S_REGEX);
            for (int i = 0; i <= $SMatcher.groupCount(); i++) {
                System.out.println(String.format("group(%d)=%s", i, $SMatcher.group(i)));
            }
        }
        System.out.println();

        String $TCacheValue = "$T.1[2][refCol]";
        Pattern $TCachePattern = $T_CACHE_REGEX_PATTERN;
        Matcher $TCacheMatcher = $TCachePattern.matcher($TCacheValue);
        if ($TCacheMatcher.matches()) {
            System.out.println($T_CACHE_REGEX);
            for (int i = 0; i <= $TCacheMatcher.groupCount(); i++) {
                System.out.println(String.format("group(%d)=%s", i, $TCacheMatcher.group(i)));
            }
        }
        System.out.println();

        String $TValue =
                "$T.tableName[key1|val1&key2|$R.BankNo&key3|$H.ExSerial&key4|$S.result.SerialNO][refCol]";
        Pattern $TPattern = $T_REGEX_PATTERN;
        Matcher $TMatcher = $TPattern.matcher($TValue);
        if ($TMatcher.matches()) {
            System.out.println($T_REGEX);
            for (int i = 0; i <= $TMatcher.groupCount(); i++) {
                System.out.println(String.format("group(%d)=%s", i, $TMatcher.group(i)));
            }
        }
        System.out.println();

        String fetchValue =
                "where key1=val1 and key2=$R.key and key3=$H.key and key4=$S.resultKey.key and key5=$T.1[2][refCol] and key6=$T.tableName[key61|val61&key62|$R.BankNo&key63|$H.ExSerial&key64|$S.result.SerialNO][refCol]";
        Pattern fetchPattern = FETCH_$RHST_REGEX_PATTERN;
        Matcher fetchMatcher = fetchPattern.matcher(fetchValue);
        while (fetchMatcher.find()) {
            System.out.println(
                    String.format(
                            "在索引区间[%d, %s]找到匹配组\"%s\"",
                            fetchMatcher.start(), fetchMatcher.end(), fetchMatcher.group()));
            System.out.println(String.format("group(3)=%s", fetchMatcher.group(3)));
        }
        System.out.println();

        System.out.println(POSITIVE_INTEGER_REGEX_PATTERN.matcher("").matches());

        String logValue = "{}决定重新改写{}日志的格式化方式为{}";
        Pattern logPattern = LOG_FORMATER_REGEX_PATTERN;
        Matcher logMatcher = logPattern.matcher(logValue);
        int index = 0;
        while (logMatcher.find()) {
            System.out.println(
                    String.format(
                            "在索引区间[%d, %s]找到匹配组\"%s\"",
                            logMatcher.start(), logMatcher.end(), logMatcher.group()));
            String tempLogValue = logMatcher.replaceFirst(String.format("{%d}", index++));
            System.out.println("替换结果为：" + tempLogValue);
            logMatcher.reset(tempLogValue);
        }
        System.out.println();

        String urlValue =
                "http://ws.stream.qqmusic.qq.com/name/patch/M800002ApuLX0GE7HH.mp3?vkey=BE92D5F4E32FE88C08A709D7654761764B513995499282E4A10C3D6B80BD74C9A8AF9A773ADEE10BB7F9FBBD53F7FFCF0E68561B221E3993&guid=-3804233880274922019&fromtag=50";
        Pattern urlPattern = URL_REGEX_PATTERN;
        Matcher urlMatcher = urlPattern.matcher(urlValue);
        if (urlMatcher.matches()) {
            System.out.println(URL_REGEX);
            for (int i = 0; i <= urlMatcher.groupCount(); i++) {
                System.out.println(String.format("group(%d)=%s", i, urlMatcher.group(i)));
            }
        }
        System.out.println();

        String uriValue = "http://ws.stream.qqmusic.qq.com/name/patch/M800002ApuLX0GE7HH.mp3";
        Pattern uriPattern = URI_REGEX_PATTERN;
        Matcher uriMatcher = uriPattern.matcher(uriValue);
        if (uriMatcher.matches()) {
            System.out.println(URI_REGEX);
            for (int i = 0; i <= uriMatcher.groupCount(); i++) {
                System.out.println(String.format("group(%d)=%s", i, uriMatcher.group(i)));
            }
        }
        System.out.println();

        String urlParamValue =
                "?vkey=BE92D5F4E32FE88C08A709D7654761764B513995499282E4A10C3D6B80BD74C9A8AF9A773ADEE10BB7F9FBBD53F7FFCF0E68561B221E3993&guid=-3804233880274922019&fromtag=50";
        Pattern urlParamPattern = URL_PARAM_REGEX_PATTERN;
        Matcher urlParamMatcher = urlParamPattern.matcher(urlParamValue);
        if (urlParamMatcher.matches()) {
            System.out.println(URI_REGEX);
            for (int i = 0; i <= urlParamMatcher.groupCount(); i++) {
                System.out.println(String.format("group(%d)=%s", i, urlParamMatcher.group(i)));
            }
        }
        System.out.println();
    }
}

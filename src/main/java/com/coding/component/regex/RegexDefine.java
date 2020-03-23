/*
 * 文件名称：RegexDefine.java
 * 系统名称：[系统名称]
 * 模块名称：正则表达式定义类
 * 软件版权：Copyright (c) 2011-2018, liming20110711@163.com All Rights Reserved.
 * 功能说明：[请在此处输入功能说明]
 * 开发人员：Rushing0711
 * 创建日期：20180424 14:55
 * 修改记录：
 * <Version>        <DateSerial>        <Author>        <Description>
 * 1.0.0            20180424-01         Rushing0711     M201804241455 新建文件
 ********************************************************************************/
package com.coding.component.regex;

import java.util.regex.Pattern;

/**
 * 正则表达式定义类.
 *
 * <p>创建时间: <font style="color:#00FFFF">20180424 14:56</font><br>
 * [请在此输入功能详述]
 *
 * @author Rushing0711
 * @version 1.0.0
 * @since 1.0.0
 */
public abstract class RegexDefine {

    /**
     * 字段说明: 正整数正则表达式<br/>
     * 正则: <code>^[1-9]\d*$</code><br/>
     * 定义: 正整数
     * 示例: 521
     */
    public static final String POSITIVE_INTEGER_REGEX = "^[1-9]\\d*$";

    /**
     * 字段说明: {@linkplain #POSITIVE_INTEGER_REGEX}
     */
    public static final Pattern POSITIVE_INTEGER_REGEX_PATTERN = Pattern.compile(POSITIVE_INTEGER_REGEX);

    /**
     * 字段说明: CSV文件名的正则表达式，匹配单个CSV文件名<br/>
     * 正则: ^(([\w-]+\.csv)(@[1-9]\d*((:|-)[1-9]\d*)*)?)$<br/>
     * 定义: abc1.csv 或 abc1.csv@1-5:8:10 或 abc2.csv@1-3:5:8-20<br/>
     * 示例: abc2.csv@1-3:5:8-20
     */
    public static final String CSV_FILE_REGEX = "^([\\w-]+\\.csv)(@[1-9]\\d*((:|-)[1-9]\\d*)*)?$";

    /**
     * 字段说明: {@linkplain #CSV_FILE_REGEX}
     */
    public static final Pattern CSV_FILE_REGEX_PATTERN = Pattern.compile(CSV_FILE_REGEX);

    /**
     * 字段说明: CSV文件配置的正则表达式，匹配单个或多个CSV文件名，多个CSV文件名以<code>|</code>组合<br/>
     * 正则: ^(([\w-]+\.csv)(@[1-9]\d*((:|-)[1-9]\d*)*)?)(\|(([\w-]+\.csv)(@[1-9]\d*((:|-)[1-9]\d*)*)?))*$<br/>
     * 定义: abc1.csv 或 abc1.csv@1-5:8:10 或 abc1.csv@1-5:8:10|abc2.csv@1-3:5:8-20...<br/>
     * 示例: abc1.csv@35|abc1.csv@1-5:8:10|abc2.csv@1-3:5:8-20
     */
    public static final String CSV_FILE_CONFIG_REGEX = "^(([\\w-]+\\.csv)(@[1-9]\\d*((:|-)[1-9]\\d*)*)?)(\\|(([\\w-]+\\.csv)(@[1-9]\\d*((:|-)[1-9]\\d*)*)?))*$";

    /**
     * 字段说明: {@linkplain #CSV_FILE_CONFIG_REGEX}
     */
    public static final Pattern CSV_FILE_CONFIG_REGEX_PATTERN = Pattern.compile(CSV_FILE_CONFIG_REGEX);

    /**
     * 示例1：完全匹配的正则表达式定义.
     *
     * <p>创建时间: <font style="color:#00FFFF">20180424 15:19</font><br>
     *
     * <ul>
     *   <li>正则：^(\$R)\.(\w+)$
     *   <li>定义：$R.key
     *   <li>示例：$R.TransCode
     * </ul>
     *
     * @since 1.0.0
     */
    public static final String $R_REGEX = "^(\\$R)\\.(\\w+)$";

    /** {@linkplain #$R_REGEX} */
    public static final Pattern $R_REGEX_PATTERN = Pattern.compile($R_REGEX);

    /**
     * 示例2：部分匹配的正则表达式定义.
     *
     * <p>创建时间: <font style="color:#00FFFF">20180424 15:24</font><br>
     *
     * <ul>
     *   <li>正则：{}
     *   <li>定义：{}
     *   <li>示例：{}决定重新改写{}日志的格式化方式为{}
     * </ul>
     *
     * @since 1.0.0
     */
    public static final String LOG_FORMATER_REGEX = "\\{\\}";

    /** 字段说明: {@linkplain #LOG_FORMATER_REGEX} */
    public static final Pattern LOG_FORMATER_REGEX_PATTERN = Pattern.compile(LOG_FORMATER_REGEX);

    /**
     * 用户手机号正则表达式定义.
     *
     * <p>创建时间: <font style="color:#00FFFF">20180424 15:41</font><br>
     *
     * <p>匹配校验手机号的合法性，并能获取手机号开头3个数字，与尾号4个数字
     *
     * <ul>
     *   <li>正则：^((?:13[0-9])|(?:14[5|7])|(?:15(?:[0-3]|[5-9]))|(?:17[013678])|(?:18[0,5-9]))\d{4}(\d{4})$
     *   <li>定义：11位手机号码
     *   <li>示例：18767188240
     * </ul>
     *
     * @since 1.0.0
     */
    public static final String MOBILE_REGEX =
            "^((?:13[0-9])|(?:14[5|7|9])|(?:15(?:[0-3]|[5-9]))|(?:16[6])|(?:17[013678])|(?:18[0-9])|(?:19[89]))\\d{4}(\\d{4})$";

    /** 字段说明：{@linkplain #MOBILE_REGEX}. */
    public static final Pattern MOBILE_REGEX_PATTERN = Pattern.compile(MOBILE_REGEX);

    // (((^https?:(?:\/\/)?)(?:[-;:&=\+\$,\w]+@)?[A-Za-z0-9.-]+|(?:www.|[-;:&=\+\$,\w]+@)[A-Za-z0-9.-]+)((?:\/[\+~%\/.\w-_]*)?\??(?:[-\+=&;%@.\w_]*)#?(?:[\w]*))?)$
    public static final String URL_REGEX =
            "(^(https?)://(?:[\\w:.-]+)?((?:\\/[\\+~%\\/.\\w-_]*)?))\\??([-\\+=&;%@.\\w_]*)#?(?:[\\w]*)$";
    public static final Pattern URL_REGEX_PATTERN = Pattern.compile(URL_REGEX);

    public static final String URI_REGEX = "^(https?)://(?:[\\w:.-]+)?((?:\\/[\\+~%\\/.\\w-_]*)?)$";
    public static final Pattern URI_REGEX_PATTERN = Pattern.compile(URI_REGEX);

    public static final String URL_PARAM_REGEX = "\\??([-\\+=&;%@.\\w_]*)#?(?:[\\w]*)";
    public static final Pattern URL_PARAM_REGEX_PATTERN = Pattern.compile(URL_PARAM_REGEX);

    public static final String PROXY_GOOD_URL_REGEX =
            "(?:url|src)=(?:\"([^\\n\\r\"']+)\"|'([^\\n\\r\"']+)')|url\\((?:\"([^\\n\\r\"']+)\"|'([^\\n\\r\"']+)'|&quot;([^\\n\\r\"']+)&quot;)\\)";
    public static final Pattern PROXY_GOOD_URL_REGEX_PATTERN =
            Pattern.compile(PROXY_GOOD_URL_REGEX);

    public static final String PROXY_BAD_URL_REGEX =
            "(?:href|url|src)=(?:\"([^\\n\\r\"']+)|'([^\\n\\r\"']+))$|url\\((?:\"([^\\n\\r\"']+)|'([^\\n\\r\"']+)|&quot;([^\\n\\r\"']+))$";
    public static final Pattern PROXY_BAD_URL_REGEX_PATTERN = Pattern.compile(PROXY_BAD_URL_REGEX);

    public static final String STRICT_FILENAME_REGEX =
            "^(?:([^<>/\\\\\\|:\"\"\\*\\?]+)\\.(\\w+))|(?:([^<>/\\\\\\|:\"\"\\*\\?]+))$";
    public static final Pattern STRICT_FILENAME_REGEX_PATTERN =
            Pattern.compile(STRICT_FILENAME_REGEX);

    public static final String FILENAME_REGEX =
            "(?:([^<>/\\\\\\|:\"\"\\*\\?]+)\\.(\\w+))|(?:([^<>/\\\\\\|:\"\"\\*\\?]+))$";
    public static final Pattern FILENAME_REGEX_PATTERN = Pattern.compile(FILENAME_REGEX);

    public static final String IMAGE_REGEX =
            "([^<>/\\\\\\|:\"\"\\*\\?]+)\\.(jpg|JPG|jpeg|JPEG|gif|GIF|png|PNG)$";
    public static final Pattern IMAGE_REGEX_PATTERN = Pattern.compile(IMAGE_REGEX);
    public static final String AUDIO_REGEX =
            "([^<>/\\\\\\|:\"\"\\*\\?]+)\\.(mp3|MP3|wav|WAV|ape|APE|flac)$";
    public static final Pattern AUDIO_REGEX_PATTERN = Pattern.compile(AUDIO_REGEX);
    public static final String VEDIO_REGEX =
            "([^<>/\\\\\\|:\"\"\\*\\?]+)\\.(mp4|avi|rmvb|flv|wmv|vob|mkv|mov)$";
    public static final Pattern VEDIO_REGEX_PATTERN = Pattern.compile(VEDIO_REGEX);

    // https://servicewechat.com/{appid}/{version}/page-frame.html
    // ^https://servicewechat.com/"+appappId+"/(\\d+(\\.\\d+)?|devtools)/page-frame.html$
}

/*
 * 文件名称：RegexResult.java
 * 系统名称：[系统名称]
 * 模块名称：正则表达式匹配结果类
 * 软件版权：Copyright (c) 2011-2018, liming20110711@163.com All Rights Reserved.
 * 功能说明：[请在此处输入功能说明]
 * 开发人员：Rushing0711
 * 创建日期：20180611 00:45
 * 修改记录：
 * <Version>        <DateSerial>        <Author>        <Description>
 * 1.0.0            20180611-01         Rushing0711     M201806110045 新建文件
 ********************************************************************************/
package com.coding.wechat.component.regex;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式匹配结果类.
 *
 * <p>创建时间: <font style="color:#00FFFF">20180611 00:45</font><br>
 * [请在此输入功能详述]
 *
 * @author Rushing0711
 * @version 1.0.0
 * @since 1.0.0
 */
public class RegexResult {

    private boolean matched;
    private Map<String, Object> result;
    private Map<String, Object> extraData;

    public static RegexResult instance() {
        return new RegexResult();
    }

    protected RegexResult() {
        super();
        this.matched = false;
        this.result = new HashMap<String, Object>();
        this.extraData = new HashMap<String, Object>();
    }

    public boolean isMatched() {
        return matched;
    }

    public void setMatched(boolean matched) {
        this.matched = matched;
    }

    public Map<String, Object> getResult() {
        return result;
    }

    public <T> T getResult(Class<T> clazz, String key) {
        Object obj = this.result.get(key);
        if (clazz.isInstance(obj)) {
            return clazz.cast(obj);
        }
        return null;
    }

    public String getResult(String key) {
        return (String) this.result.get(key);
    }

    public void setResult(String key, Object value) {
        this.result.put(key, value);
    }

    public String getRawValue() {
        return (String) this.extraData.get("$X_RAW_VALUE");
    }

    public void setRawValue(String rawValue) {
        this.extraData.put("$X_RAW_VALUE", rawValue);
    }

    public String getRegex() {
        return (String) this.extraData.get("$X_REGEX");
    }

    public void setRegex(String value) {
        this.extraData.put("$X_REGEX", value);
    }

    public Pattern getPattern() {
        return (Pattern) this.extraData.get("$X_REGEX_PATTERN");
    }

    public void setPattern(Pattern pattern) {
        this.extraData.put("X_REGEX_PATTERN", pattern);
    }

    public Matcher getMatcher() {
        return (Matcher) this.extraData.get("$X_REGEX_MATCHER");
    }

    public void setMatcher(Matcher matcher) {
        this.extraData.put("$X_REGEX_MATCHER", matcher);
    }
}

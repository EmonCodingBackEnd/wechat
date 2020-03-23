package com.coding.component.regex;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.util.StringUtils;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class RegexDefineTest {
    @Test
    public void match$R() throws Exception {
        String $RValue = "$R.key";
        Pattern $RPattern = RegexDefine.$R_REGEX_PATTERN;
        Matcher $RMatcher = $RPattern.matcher($RValue);
        if ($RMatcher.matches()) {
            System.out.println(RegexDefine.$R_REGEX);
            for (int i = 0; i <= $RMatcher.groupCount(); i++) {
                System.out.println(String.format("group(%d)=%s", i, $RMatcher.group(i)));
            }
        }
    }

    @Test
    public void matchLogFormater() throws Exception {
        String logValue = "{}决定重新改写{}日志的格式化方式为{}";
        Pattern logPattern = RegexDefine.LOG_FORMATER_REGEX_PATTERN;
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
    }

    @Test
    public void matchMobile() throws Exception {
        String mobileValue = "18767188240";
        Pattern mobilePattern = RegexDefine.MOBILE_REGEX_PATTERN;
        Matcher mobileMatcher = mobilePattern.matcher(mobileValue);
        if (mobileMatcher.matches()) {
            System.out.println(RegexDefine.MOBILE_REGEX);
            for (int i = 0; i <= mobileMatcher.groupCount(); i++) {
                System.out.println(String.format("group(%d)=%s", i, mobileMatcher.group(i)));
            }
        }
    }

    @Test
    public void matchUrl() throws Exception {
        String urlValue =
                "http://ws.stream.qqmusic.qq.com/name/patch/M800002ApuLX0GE7HH.mp3?vkey=BE92D5F4E32FE88C08A709D7654761764B513995499282E4A10C3D6B80BD74C9A8AF9A773ADEE10BB7F9FBBD53F7FFCF0E68561B221E3993&guid=-3804233880274922019&fromtag=50";
        Pattern urlPattern = RegexDefine.URL_REGEX_PATTERN;
        Matcher urlMatcher = urlPattern.matcher(urlValue);
        if (urlMatcher.matches()) {
            System.out.println(RegexDefine.URL_REGEX);
            for (int i = 0; i <= urlMatcher.groupCount(); i++) {
                System.out.println(String.format("group(%d)=%s", i, urlMatcher.group(i)));
            }
        }
    }

    @Test
    public void matchUri() throws Exception {
        String uriValue = "http://ws.stream.qqmusic.qq.com/name/patch/M800002ApuLX0GE7HH.mp3";
        Pattern uriPattern = RegexDefine.URI_REGEX_PATTERN;
        Matcher uriMatcher = uriPattern.matcher(uriValue);
        if (uriMatcher.matches()) {
            System.out.println(RegexDefine.URI_REGEX);
            for (int i = 0; i <= uriMatcher.groupCount(); i++) {
                System.out.println(String.format("group(%d)=%s", i, uriMatcher.group(i)));
            }
        }
    }

    @Test
    public void matchUrlParam() throws Exception {
        String urlParamValue =
                "?vkey=BE92D5F4E32FE88C08A709D7654761764B513995499282E4A10C3D6B80BD74C9A8AF9A773ADEE10BB7F9FBBD53F7FFCF0E68561B221E3993&guid=-3804233880274922019&fromtag=50";
        Pattern urlParamPattern = RegexDefine.URL_PARAM_REGEX_PATTERN;
        Matcher urlParamMatcher = urlParamPattern.matcher(urlParamValue);
        if (urlParamMatcher.matches()) {
            System.out.println(RegexDefine.URI_REGEX);
            for (int i = 0; i <= urlParamMatcher.groupCount(); i++) {
                System.out.println(String.format("group(%d)=%s", i, urlParamMatcher.group(i)));
            }
        }
    }

    @Test
    public void matchGoodUrl() throws Exception {
        String goodUrlValue = RegexDefineTestHelper.srcGoodValue;
        Pattern goodUrlPattern = RegexDefine.PROXY_GOOD_URL_REGEX_PATTERN;

        Map<String, String> replaceMap = Maps.newHashMap();
        replaceMap.put(
                "https://mp.weixin.qq.com",
                "https://exp.mynatapp.cc/wechat/http/readByUrl?urlStr=");
        replaceMap.put(
                "https://mmbiz.qpic.cn",
                "https://exp.mynatapp.cc/wechat/http/readByUrlImg?urlStr=");
        Matcher goodUrlMatcher = goodUrlPattern.matcher(goodUrlValue);
        while (goodUrlMatcher.find()) {
            String matchContent = goodUrlMatcher.group();
            String goodUrl = null;
            for (int i = 1; i <= goodUrlMatcher.groupCount(); i++) {
                if (goodUrlMatcher.group(i) != null) {
                    goodUrl = goodUrlMatcher.group(i);
                    break;
                }
            }
            String goodUrlParsed = null;
            if (!StringUtils.isEmpty(goodUrl)) {
                for (Map.Entry<String, String> entry : replaceMap.entrySet()) {
                    if (goodUrl.startsWith(entry.getKey())) {
                        goodUrlParsed =
                                entry.getValue()
                                        + URLEncoder.encode(goodUrl, StandardCharsets.UTF_8.name());
                        break;
                    }
                }
            }

            System.out.println(
                    String.format(
                            "在索引区间[%d, %s]找到匹配组【%s】==>【%s】==>【%s】",
                            goodUrlMatcher.start(),
                            goodUrlMatcher.end(),
                            matchContent,
                            goodUrl,
                            goodUrlParsed));
        }
    }

    @Test
    public void matchBadUrl() throws Exception {
        String badUrlValue = RegexDefineTestHelper.srcBadValue;
        Pattern badUrlPattern = RegexDefine.PROXY_BAD_URL_REGEX_PATTERN;
        Matcher badUrlMatcher = badUrlPattern.matcher(badUrlValue);
        while (badUrlMatcher.find()) {
            System.out.println(
                    String.format(
                            "在索引区间[%d, %s]找到匹配组【%s】",
                            badUrlMatcher.start(), badUrlMatcher.end(), badUrlMatcher.group()));
        }
    }

    @Test
    public void matchStrictFilename() throws Exception {
        String filenameValue = "寒夜方舟.mp3";
        Pattern filenamePattern = RegexDefine.STRICT_FILENAME_REGEX_PATTERN;
        Matcher filenameMatcher = filenamePattern.matcher(filenameValue);
        if (filenameMatcher.matches()) {
            System.out.println(RegexDefine.STRICT_FILENAME_REGEX);
            for (int i = 0; i <= filenameMatcher.groupCount(); i++) {
                System.out.println(String.format("group(%d)=%s", i, filenameMatcher.group(i)));
            }
        }
    }

    @Test
    public void matchFilename() throws Exception {
        String filepathValue = "parentDirectory/寒夜方舟.mp3";
        Pattern filepathPattern = RegexDefine.FILENAME_REGEX_PATTERN;
        Matcher filepathMatcher = filepathPattern.matcher(filepathValue);
        while (filepathMatcher.find()) {
            System.out.println(
                    String.format(
                            "在索引区间[%d, %s]找到匹配组【%s】",
                            filepathMatcher.start(),
                            filepathMatcher.end(),
                            filepathMatcher.group()));
        }
    }

    @Test
    public void matchImage() throws Exception {
        String imageValue = "parentDirectory/寒夜方舟.png";
        Pattern imagePattern = RegexDefine.IMAGE_REGEX_PATTERN;
        Matcher imageMatcher = imagePattern.matcher(imageValue);
        while (imageMatcher.find()) {
            System.out.println(
                    String.format(
                            "在索引区间[%d, %s]找到匹配组【%s】",
                            imageMatcher.start(), imageMatcher.end(), imageMatcher.group()));
        }
    }

    @Test
    public void matchAudio() throws Exception {
        String audioValue = "parentDirectory/寒夜方舟.mp3";
        audioValue = "00af57db7f1d4e759037d23f56831e4b.mp3";
        Pattern audioPattern = RegexDefine.AUDIO_REGEX_PATTERN;
        Matcher audioMatcher = audioPattern.matcher(audioValue);
        while (audioMatcher.find()) {
            System.out.println(
                    String.format(
                            "在索引区间[%d, %s]找到匹配组【%s】",
                            audioMatcher.start(), audioMatcher.end(), audioMatcher.group()));
        }
    }

    @Test
    public void matchVedio() throws Exception {
        String audioValue = "parentDirectory/寒夜方舟.mp3";
        Pattern audioPattern = RegexDefine.VEDIO_REGEX_PATTERN;
        Matcher audioMatcher = audioPattern.matcher(audioValue);
        while (audioMatcher.find()) {
            System.out.println(
                    String.format(
                            "在索引区间[%d, %s]找到匹配组【%s】",
                            audioMatcher.start(), audioMatcher.end(), audioMatcher.group()));
        }
    }
}

package com.coding.wechat.component.http;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.Map;

@Slf4j
public class HttpToolsTest {
    @Test
    public void doGet() throws Exception {
        String result;
        try {
            result = HttpTools.doGet("http://localhost:8080/wechat/http/test");
        } catch (IOException e) {
            result = null;
        }
        Assert.notNull(result, "方法执行错误");
    }

    @Test
    public void doGet1() throws Exception {
        String result;
        try {
            result = HttpTools.doGet("http://localhost:8080/wechat/http/test?key=100");
        } catch (IOException e) {
            result = null;
        }
        Assert.notNull(result, "方法执行错误");
    }

    @Test
    public void doGet2() throws Exception {
        String result;
        try {
            result = HttpTools.doGet("http://localhost:8080/wechat/http/test?key=100&timeout=3000");
        } catch (IOException e) {
            result = null;
        }
        Assert.notNull(result, "方法执行错误");
    }

    @Test
    public void doGet3() throws Exception {
        String result;
        try {
            result = HttpTools.doGet("http://localhost:8080/wechat/http/test?key=100&timeout=6000");
        } catch (IOException e) {
            result = null;
        }
        Assert.notNull(result, "方法执行错误");
    }

    @Test
    public void doGet4() throws Exception {
        String result;
        try {
            result =
                    HttpTools.doGet(
                            "http://localhost:8080/wechat/http/test?key=100&timeout=6000", 10000);
        } catch (IOException e) {
            result = null;
        }
        Assert.notNull(result, "方法执行错误");
    }

    @Test
    public void doGet5() throws Exception {
        String result;
        try {
            Map<String, String> paramMap = ImmutableMap.of("key", "100", "timeout", "3000");
            result = HttpTools.doGet("http://localhost:8080/wechat/http/test", paramMap);
        } catch (IOException e) {
            result = null;
        }
        Assert.notNull(result, "方法执行错误");
    }

    @Test
    public void doGet6() throws Exception {
        String result;
        try {
            String paramString = "?key=100&timeout=3000";
            result = HttpTools.doGet("http://localhost:8080/wechat/http/test", paramString);
        } catch (IOException e) {
            result = null;
        }
        Assert.notNull(result, "方法执行错误");
    }

    @Test
    public void doGet7() throws Exception {
        String result;
        try {
            String paramString = "key=100&timeout=3000";
            result = HttpTools.doGet("http://localhost:8080/wechat/http/test", paramString);
        } catch (IOException e) {
            result = null;
        }
        Assert.notNull(result, "方法执行错误");
    }

    @Test
    public void doPost() throws Exception {
        String result;
        try {
            result = HttpTools.doPost("http://localhost:8080/wechat/http/test");
        } catch (IOException e) {
            result = null;
        }
        Assert.notNull(result, "方法执行错误");
    }

    @Test
    public void doPost1() throws Exception {
        String result;
        try {
            result = HttpTools.doPost("http://localhost:8080/wechat/http/test?key=100");
        } catch (IOException e) {
            result = null;
        }
        Assert.notNull(result, "方法执行错误");
    }

    @Test
    public void doPost2() throws Exception {
        String result;
        try {
            result = HttpTools.doPost("http://localhost:8080/wechat/http/test?key=100&timeout=3000");
        } catch (IOException e) {
            result = null;
        }
        Assert.notNull(result, "方法执行错误");
    }

    @Test
    public void doPost3() throws Exception {
        String result;
        try {
            result = HttpTools.doPost("http://localhost:8080/wechat/http/test?key=100&timeout=6000");
        } catch (IOException e) {
            result = null;
        }
        Assert.notNull(result, "方法执行错误");
    }

    @Test
    public void doPost4() throws Exception {
        String result;
        try {
            result =
                    HttpTools.doPost(
                            "http://localhost:8080/wechat/http/test?key=100&timeout=6000", 10000);
        } catch (IOException e) {
            result = null;
        }
        Assert.notNull(result, "方法执行错误");
    }

    @Test
    public void doPost5() throws Exception {
        String result;
        try {
            Map<String, String> paramMap = ImmutableMap.of("key", "100", "timeout", "3000");
            result = HttpTools.doPost("http://localhost:8080/wechat/http/test", paramMap);
        } catch (IOException e) {
            result = null;
        }
        Assert.notNull(result, "方法执行错误");
    }

    @Test
    public void doPost6() throws Exception {
        String result;
        try {
            String paramString = "?key=100&timeout=3000";
            result = HttpTools.doPost("http://localhost:8080/wechat/http/test", paramString);
        } catch (IOException e) {
            result = null;
        }
        Assert.notNull(result, "方法执行错误");
    }

    @Test
    public void doPost7() throws Exception {
        String result;
        try {
            String paramString = "key=100&timeout=3000";
            result = HttpTools.doPost("http://localhost:8080/wechat/http/test", paramString);
        } catch (IOException e) {
            result = null;
        }
        Assert.notNull(result, "方法执行错误");
    }
}

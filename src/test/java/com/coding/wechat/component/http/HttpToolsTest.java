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
            result =
                    HttpTools.doPost("http://localhost:8080/wechat/http/test?key=100&timeout=3000");
        } catch (IOException e) {
            result = null;
        }
        Assert.notNull(result, "方法执行错误");
    }

    @Test
    public void doPost3() throws Exception {
        String result;
        try {
            result =
                    HttpTools.doPost("http://localhost:8080/wechat/http/test?key=100&timeout=6000");
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

    // 公众号模板消息
    @Test
    public void doPost8() throws Exception {
        //        String url =
        // "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wxda9f1fa3d94b9c19&secret=44c3c418ee8460458e2617f83f528c5d";
        //        String result = HttpTools.doGet(url);
        //        log.info(result);

        String acces_token =
                "11_v32JWw3X9oH64dTJzLak_U07QXHnvQ_NHvXFI-M0g7A8zjDqgwBgyVxn3NXg9OXlaWWI4rjv_GS8XPA0_P9TNZ0luRtHgdWnRHIWyq7UDSThbPsfyJX3GBdO4rXaWJ-D0SR7qzzJLu7irJSqTVWbAGAIKL";
        String url =
                "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="
                        + acces_token;
        String json =
                "{\"touser\":\"wxda9f1fa3d94b9c19\",\"template_id\":\"q3ck6p5gBpYhTvyQSE60Z9PlSNbO7EhVfcxrT1k_YKY\",\"appid\":\"1111\",\"data\":{\"first\": {\"value\":\"恭喜你购买成功！\",\"color\":\"#173177\"},\"keyword1\":{\"value\":\"巧克力\",\"color\":\"#173177\"},\"keyword2\": {\"value\":\"39.8元\",\"color\":\"#173177\"},\"keyword3\": {\"value\":\"2014年9月22日\",\"color\":\"#173177\"},\"remark\":{\"value\":\"欢迎再次购买！\",\"color\":\"#173177\"}}}";
        String result = HttpTools.doPost(url, json);
        log.info(result);
    }

    // 小程序服务通知
    @Test
    public void doPost9() throws Exception {
        //        String url =
        // "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wxda9f1fa3d94b9c19&secret=44c3c418ee8460458e2617f83f528c5d";
        //        String result = HttpTools.doGet(url);
        //        log.info(result);

        String acces_token =
                "11_ceoaq0R5E9JXAP9MBVYrsqiIoMEHYWYgN5ZaoAgbQj13xYzEtZm1QpT0bYTgAjWKuexHJVcEezp_UTsngIGyZd-lJs7R40WkcUHqPnNC5LJDHiKjfljGmA2WPBd9ofopwq9KRWO1vgJqCArZZMGeAFAIVP";
        String url =
                "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token="
                        + acces_token;
        String json =
                "{\"touser\":\"o8z3i5AZZK_bIXud3RYmmDGKYbHg\",\"template_id\":\"ow6n0FP73DIhagyVZLCLbpaUKzPKRMD7yJ4MKiqblnk\",\"page\":\"index\",\"form_id\":\"wx23160601235111e1053f6bd82332926398\",\"data\":{\"keyword1\":{\"value\":\"1\",\"color\":\"#173177\"},\"keyword2\":{\"value\":\"2\",\"color\":\"#173177\"},\"keyword3\":{\"value\":\"3\",\"color\":\"#173177\"},\"keyword4\":{\"value\":\"4\",\"color\":\"#173177\"}}}";
        String result = HttpTools.doPostJson(url, json);
        log.info(result);
    }

    // 小程序服务通知
    @Test
    public void doPost10() throws Exception {
        String acces_token =
                "11_ceoaq0R5E9JXAP9MBVYrsqiIoMEHYWYgN5ZaoAgbQj13xYzEtZm1QpT0bYTgAjWKuexHJVcEezp_UTsngIGyZd-lJs7R40WkcUHqPnNC5LJDHiKjfljGmA2WPBd9ofopwq9KRWO1vgJqCArZZMGeAFAIVP";
        String url =
                "https://api.weixin.qq.com/cgi-bin/wxopen/template/list?access_token="
                        + acces_token;
        String json = "{\"offset\":0,\"count\":10}";
        String result = HttpTools.doPostJson(url, json);
        log.info(result);
    }
}

package com.coding.wechat.utils.http;

import com.coding.wechat.component.http.HttpTools;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class HttpToolsTest {
    @Test
    public void doGet() throws Exception {
        String url = "http://tool.oschina.net";
        String result = HttpTools.doGet(url);
        log.info("【doGet】应答内容长度={}", result.length());
    }

    @Test
    public void doGet1() throws Exception {}
}

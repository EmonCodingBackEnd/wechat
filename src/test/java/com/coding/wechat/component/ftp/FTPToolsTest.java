package com.coding.wechat.component.ftp;

import com.coding.wechat.component.ftp.result.UploadResult;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class FTPToolsTest {
    @Test
    public void uploadFileAutoDetectDirectory1() throws Exception {}

    @Test
    public void uploadFileAutoDetectDirectory2() throws Exception {}

    @Test
    public void uploadFileAutoDetectDirectory3() throws Exception {
        UploadResult uploadResult = FTPTools.uploadFileAutoDetectDirectory("abc", "abc content");
        log.info(uploadResult.toString());
    }
}

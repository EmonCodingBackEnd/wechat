package com.coding.wechat.component.ftp.template;

import com.coding.wechat.component.ftp.config.FTPConfig;
import com.coding.wechat.component.ftp.param.UploadParam;
import com.coding.wechat.component.ftp.param.UploadParamBuilder;
import com.coding.wechat.component.ftp.result.UploadResult;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class FTPTemplateTest {

    @Autowired FTPConfig ftpConfig;
    @Autowired FTPTemplate ftpTemplate;

    @Test
    public void uploadFile() throws Exception {
        UploadParam uploadParam =
                UploadParamBuilder.custom()
                        .autoDetect(true)
                        .content("test.mp3", "Just For Test")
                        .build();
        UploadResult uploadResult =
                ftpTemplate.uploadFile(ftpConfig.getServer("default"), uploadParam);
        log.info(uploadResult.toString());
        uploadResult = ftpTemplate.uploadFile(ftpConfig.getServer("house"), uploadParam);
        log.info(uploadResult.hasFailure() + "");
    }
}

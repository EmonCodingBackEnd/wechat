package com.coding.wechat.component.ftp.template;

import com.coding.wechat.component.ftp.config.FTPConfig;
import com.coding.wechat.component.ftp.param.*;
import com.coding.wechat.component.ftp.result.ListResult;
import com.coding.wechat.component.ftp.result.UploadResult;
import com.coding.wechat.component.regex.RegexDefine;
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
                        .content("test.ape", "Just For Test")
                        .build();
        UploadResult uploadResult =
                ftpTemplate.uploadFile(ftpConfig.getServer("default"), uploadParam);
        log.info(uploadResult.toString());
        uploadResult = ftpTemplate.uploadFile(ftpConfig.getServer("house"), uploadParam);
        log.info(uploadResult.toString());
    }

    @Test
    public void listFiles() throws Exception {
        ListParam listParam = ListParamBuilder.custom().limit(2).build();
        ListResult listResult = ftpTemplate.listFiles(ftpConfig.getServer("default"), listParam);
        log.info(listResult.toString());
        listParam = ListParamBuilder.custom().pattern(RegexDefine.AUDIO_REGEX_PATTERN).build();
        listResult = ftpTemplate.listFiles(ftpConfig.getServer("default"), listParam);
        log.info(listResult.toString());
        listParam =
                ListParamBuilder.custom()
                        .filter(
                                new FilenameFilter() {
                                    @Override
                                    public boolean accept(String name) {
                                        log.info(name);
                                        return true;
                                    }
                                })
                        .build();
        listResult = ftpTemplate.listFiles(ftpConfig.getServer("default"), listParam);
        log.info(listResult.toString());
    }
}

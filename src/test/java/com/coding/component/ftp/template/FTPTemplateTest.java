package com.coding.component.ftp.template;

import com.coding.component.ftp.config.FTPConfig;
import com.coding.component.ftp.filter.FilenameFilter;
import com.coding.component.ftp.param.*;
import com.coding.component.ftp.result.DownloadResult;
import com.coding.component.ftp.result.ListResult;
import com.coding.component.ftp.result.UploadResult;
import com.coding.component.regex.RegexDefine;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPFileFilter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class FTPTemplateTest {
    @Test
    public void getFtpClientPool() throws Exception {}

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
        ListParam listParam =
                ListParamBuilder.custom()
                        .limit(2)
                        .remoteDirectory("audios")
                        .pattern("[\\w]+\\.ape")
                        .pattern(RegexDefine.AUDIO_REGEX_PATTERN)
                        .filter(
                                new FilenameFilter() {
                                    @Override
                                    public boolean accept(String name) {
                                        log.info(name);
                                        return true;
                                    }
                                })
                        .filter(
                                new FTPFileFilter() {
                                    @Override
                                    public boolean accept(FTPFile file) {
                                        if (file.isDirectory()) {
                                            return false;
                                        }
                                        return true;
                                    }
                                })
                        .build();
        ListResult listResult = ftpTemplate.listFiles(ftpConfig.getServer("default"), listParam);
        log.info(listResult.toString());
    }

    @Test
    public void downloadFile() throws Exception {
        DownloadParam downloadParam =
                DownloadParamBuilder.custom().remoteDirectory("audios/s").localDirectory("/home/saas/download").build();
        DownloadResult downloadResult =
                ftpTemplate.downloadFile(ftpConfig.getServer("default"), downloadParam);
        log.info(downloadResult.toString());
    }

    @Test
    public void deleteFile() throws Exception {}
}

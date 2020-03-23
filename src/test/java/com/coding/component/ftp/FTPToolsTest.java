package com.coding.component.ftp;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class FTPToolsTest {
    @Test
    public void uploadFileAutoDetectDirectory1() throws Exception {
        FTPTools.uploadFileAutoDetectDirectory("test.mp3", "我的内容是模拟MP3文件");
    }

    @Test
    public void uploadFileAutoDetectDirectory2() throws Exception {
        FTPTools.uploadFile("dir1/dir2/dir3", "test.mp3", "我的内容是模拟MP3文件");
    }

    @Test
    public void uploadFileAutoDetectDirectory3() throws Exception {}
}

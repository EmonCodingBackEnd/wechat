/*
 * 文件名称：FTPController.java
 * 系统名称：FTP工具类测试接口
 * 模块名称：[模块名称]
 * 软件版权：Copyright (c) 2011-2018, liming20110711@163.com All Rights Reserved.
 * 功能说明：[请在此处输入功能说明]
 * 开发人员：Rushing0711
 * 创建日期：20180627 23:26
 * 修改记录：
 * <Version>        <DateSerial>        <Author>        <Description>
 * 1.0.0            20180627-01         Rushing0711     M201806272326 新建文件
 ********************************************************************************/
package com.coding.wechat.controller;

import com.coding.wechat.component.ftp.FTPTools;
import com.coding.wechat.component.ftp.result.UploadResult;
import com.coding.wechat.component.ftp.result.UploadResultItem;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * Http工具类测试接口.
 *
 * <p>创建时间: <font style="color:#00FFFF">20180606 12:29</font><br>
 * [请在此输入功能详述]
 *
 * @author Rushing0711
 * @version 1.0.0
 * @since 1.0.0
 */
@RestController
@RequestMapping("/ftp")
@Slf4j
public class FTPController {

    @PostMapping("upload")
    @ResponseBody
    public Map<String, UploadResultItem> uploadAudio(@RequestParam("file") MultipartFile[] file) {
        UploadResult uploadResult =
                FTPTools.uploadFileAutoDetectDirectory(Lists.newArrayList(file));
        for (Map.Entry<String, UploadResultItem> entry : uploadResult.getSuccessMap().entrySet()) {
            log.info(entry.getValue().getUrl());
        }
        return uploadResult.getSuccessMap();
    }
}

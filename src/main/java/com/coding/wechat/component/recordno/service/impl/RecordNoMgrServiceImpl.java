/*
 * 文件名称：RecordNoMgrServiceImpl.java
 * 系统名称：[系统名称]
 * 模块名称：[模块名称]
 * 软件版权：Copyright (c) 2011-2018, liming20110711@163.com All Rights Reserved.
 * 功能说明：[请在此处输入功能说明]
 * 开发人员：Rushing0711
 * 创建日期：20181107 00:00
 * 修改记录：
 * <Version>        <DateSerial>        <Author>        <Description>
 * 1.0.0            20181107-01         Rushing0711     M201811070000 新建文件
 ********************************************************************************/
package com.coding.wechat.component.recordno.service.impl;

import com.coding.wechat.component.recordno.DO.RecordNoMgr;
import com.coding.wechat.component.recordno.repository.RecordNoMgrRepository;
import com.coding.wechat.component.recordno.service.RecordNoMgrService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Service
@Slf4j
public class RecordNoMgrServiceImpl implements RecordNoMgrService {
    @Autowired private RecordNoMgrRepository recordNoMgrRepository;

    @Transactional
    @Override
    public String getTenantRecordNo() {
        return getRecordNoByTableName("tenant");
    }

    @Transactional
    @Override
    public String getShopRecordNo() {
        return getRecordNoByTableName("shop");
    }

    @Transactional
    @Override
    public String getUserinfoRecordNo() {
        return getRecordNoByTableName("userinfo");
    }

    private String getRecordNoByTableName(String tableName) {
        RecordNoMgr recordNoMgr = recordNoMgrRepository.findByTableName(tableName);
        if (recordNoMgr == null) {
            log.error("【记录号获取】记录号生成管理表不存在名称为 {} 的表", tableName);
            throw new RuntimeException(String.format("表 %s 记录号获取失败！", tableName));
        }
        // 锁定数据库数据
        recordNoMgr.setModifyTime(new Date());
        recordNoMgrRepository.saveAndFlush(recordNoMgr);

        // 如果是20位编号（6位table_no+8位workdate+6位max_record_no）
        if (1 == recordNoMgr.getRecordType()) {
            // 如果日期不是当前日期，则更新
            if (!new Date(recordNoMgr.getWorkdate().getTime())
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate()
                    .equals(LocalDate.now())) {
                recordNoMgr.setWorkdate(new Date());
                recordNoMgr.setMaxRecordNo(0);
                recordNoMgrRepository.saveAndFlush(recordNoMgr);
            }

        } else {
            // 如果是12位编号（6位table_no+6位max_record_no[从100001开始]）
            if (recordNoMgr.getMaxRecordNo() < 100000) {
                recordNoMgr.setWorkdate(new Date());
                recordNoMgr.setMaxRecordNo(100000);
                recordNoMgrRepository.saveAndFlush(recordNoMgr);
            }
        }

        // 更新并获取
        recordNoMgr.setMaxRecordNo(recordNoMgr.getMaxRecordNo() + 1);
        recordNoMgrRepository.saveAndFlush(recordNoMgr);

        StringBuilder recordNo = new StringBuilder();
        recordNo.append(recordNoMgr.getTableNo());
        if (1 == recordNoMgr.getRecordType()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            recordNo.append(LocalDate.now().format(formatter));
        }
        recordNo.append(integerToString(recordNoMgr.getMaxRecordNo()));
        log.info("【记录号获取】表 {} 记录号 {}", tableName, recordNo);
        log.info(Long.MAX_VALUE + "");
        return recordNo.toString();
    }

    private String integerToString(Integer number) {
        int len = 6;
        StringBuilder sb = new StringBuilder();
        String temp = Long.toString(number);
        int templen = temp.length();
        if (templen < len) {
            for (int i = 0; i < len - templen; i++) {
                sb.append(0);
            }
            sb.append(temp);
        } else {
            sb.append(temp.substring(templen - len));
        }
        return sb.toString();
    }
}

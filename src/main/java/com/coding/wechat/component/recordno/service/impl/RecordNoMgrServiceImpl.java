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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class RecordNoMgrServiceImpl implements RecordNoMgrService {

    @Autowired private RecordNoMgrRepository recordNoMgrRepository;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    @Transactional
    @Override
    public String getTenantRecordNo() {
        return getRecordNoByTableName("tenant", 1).get(0);
    }

    @Transactional
    @Override
    public List<String> getBatchTenantRecordNo(Integer recordNoNum) {
        return getRecordNoByTableName("tenant", recordNoNum);
    }

    @Transactional
    @Override
    public String getShopRecordNo() {
        return getRecordNoByTableName("shop", 1).get(0);
    }

    @Transactional
    @Override
    public List<String> getBatchShopRecordNo(Integer recordNoNum) {
        return getRecordNoByTableName("shop", recordNoNum);
    }

    @Transactional
    @Override
    public String getUserinfoRecordNo() {
        return getRecordNoByTableName("userinfo", 1).get(0);
    }

    @Transactional
    @Override
    public List<String> getBatchUserinfoRecordNo(Integer recordNoNum) {
        return getRecordNoByTableName("userinfo", recordNoNum);
    }

    private List<String> getRecordNoByTableName(String tableName, int recordNoNum) {
        // 悲观锁锁定数据库数据
        recordNoMgrRepository.lockRecord(tableName);

        RecordNoMgr recordNoMgr = recordNoMgrRepository.findByTableName(tableName);
        if (recordNoMgr == null) {
            log.error("【记录号获取】记录号生成管理表不存在名称为 {} 的表", tableName);
            throw new RuntimeException(String.format("表 %s 记录号获取失败！", tableName));
        }
        if (recordNoNum <= 0 || recordNoNum > 1000) {
            log.error("【记录号获取】一次获取的记录号数量不合法，合法范围[1-1000]");
            throw new RuntimeException(String.format("表 %s 记录号获取失败！", tableName));
        }
        if (recordNoNum + recordNoMgr.getMaxRecordNo() > Integer.MAX_VALUE) {
            log.error("【记录号获取】一次获取的记录号数量不合法，记录值超限");
            throw new RuntimeException(String.format("表 %s 记录号获取失败！", tableName));
        }

        RecordType recordType = RecordType.getByRecordType(recordNoMgr.getRecordType());

        switch (recordType) {
            case RECORD_NO_WORKDATE_MAX_RECORD_NO:
                // 如果日期不是当前日期，则更新
                if (!new Date(recordNoMgr.getWorkdate().getTime())
                        .toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate()
                        .equals(LocalDate.now())) {
                    recordNoMgr.setWorkdate(new Date());
                    recordNoMgr.setMaxRecordNo(0);
                    recordNoMgrRepository.save(recordNoMgr);
                }
                break;
            case RECORD_NO_MAX_RECORD_NO:
            case MAX_RECORD_NO:
                if (recordNoMgr.getMaxRecordNo() < 10000000) {
                    recordNoMgr.setWorkdate(new Date());
                    recordNoMgr.setMaxRecordNo(10000000);
                    recordNoMgrRepository.save(recordNoMgr);
                }
                break;
            default:
        }

        // 当前值
        Integer currentMaxRecordNo = recordNoMgr.getMaxRecordNo();

        // 更新并获取
        recordNoMgr.setMaxRecordNo(recordNoMgr.getMaxRecordNo() + recordNoNum);
        recordNoMgrRepository.save(recordNoMgr);

        List<String> recordNoList = new ArrayList<>();
        StringBuilder recordNo = new StringBuilder();
        for (int i = 1; i <= recordNoNum; i++) {
            recordNo.delete(0, recordNo.length());
            switch (recordType) {
                case RECORD_NO_WORKDATE_MAX_RECORD_NO:
                    recordNo.append(recordNoMgr.getTableNo());
                    recordNo.append(LocalDate.now().format(formatter));
                    recordNo.append(integerToString(currentMaxRecordNo++));
                    break;
                case RECORD_NO_MAX_RECORD_NO:
                    recordNo.append(recordNoMgr.getTableNo());
                case MAX_RECORD_NO:
                    recordNo.append(integerToString(currentMaxRecordNo++));
                    break;
                default:
            }
            recordNoList.add(recordNo.toString());
        }

        log.info(
                "【记录号获取】表 {} 记录号 {}",
                tableName,
                Arrays.toString(recordNoList.toArray(new String[] {})));

        return recordNoList;
    }

    private String integerToString(Integer number) {
        int len = 8;
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

    enum RecordType {
        RECORD_NO_WORKDATE_MAX_RECORD_NO(
                1, "19位编号（3位table_no+8位workdate+8位max_record_no[从00000001开始])"),
        RECORD_NO_MAX_RECORD_NO(2, "11位编号（3位table_no+8位max_record_no[从10000001开始]）"),
        MAX_RECORD_NO(3, "8位编号（8位max_record_no[从10000001开始]）"),
        ;

        RecordType(Integer recordType, String description) {
            this.recordType = recordType;
            this.description = description;
        }

        private Integer recordType;

        private String description;

        public static RecordType getByRecordType(Integer type) {
            for (RecordType recordType : RecordType.class.getEnumConstants()) {
                if (recordType.recordType.equals(type)) {
                    return recordType;
                }
            }
            return RECORD_NO_WORKDATE_MAX_RECORD_NO;
        }

        @Override
        public String toString() {
            return "RecordType{"
                    + "recordType="
                    + recordType
                    + ", description='"
                    + description
                    + '\''
                    + '}';
        }
    }
}

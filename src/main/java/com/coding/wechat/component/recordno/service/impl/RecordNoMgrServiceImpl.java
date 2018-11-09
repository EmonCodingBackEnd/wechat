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
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

    private static final String EMPTY = "";

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    @Transactional
    @Override
    public String getTenantRecordNo(String prefix) {
        return getRecordNoByTableName(prefix, "tenant", 1).get(0);
    }

    @Transactional
    @Override
    public List<String> getBatchTenantRecordNo(String prefix, Integer recordNoNum) {
        return getRecordNoByTableName(prefix, "tenant", recordNoNum);
    }

    @Transactional
    @Override
    public String getShopRecordNo(String prefix) {
        return getRecordNoByTableName(prefix, "shop", 1).get(0);
    }

    @Transactional
    @Override
    public List<String> getBatchShopRecordNo(String prefix, Integer recordNoNum) {
        return getRecordNoByTableName(prefix, "shop", recordNoNum);
    }

    @Transactional
    @Override
    public String getGoodsRecordNo(String prefix) {
        return getRecordNoByTableName(prefix, "goods", 1).get(0);
    }

    @Transactional
    @Override
    public List<String> getBatchGoodsRecordNo(String prefix, Integer recordNoNum) {
        return getRecordNoByTableName(prefix, "goods", recordNoNum);
    }

    @Transactional
    @Override
    public String getUserinfoRecordNo(String prefix) {
        return getRecordNoByTableName(prefix, "userinfo", 1).get(0);
    }

    @Transactional
    @Override
    public List<String> getBatchUserinfoRecordNo(String prefix, Integer recordNoNum) {
        return getRecordNoByTableName(prefix, "userinfo", recordNoNum);
    }

    @Transactional
    @Override
    public String getCustomerRecordNo() {
        return getRecordNoByTableName(EMPTY, "customer", 1).get(0);
    }

    @Transactional
    @Override
    public List<String> getBatchCustomerRecordNo(Integer recordNoNum) {
        return getRecordNoByTableName(EMPTY, "customer", recordNoNum);
    }

    @Transactional
    @Override
    public String getOrderRecordNo() {
        return getRecordNoByTableName(EMPTY, "order", 1).get(0);
    }

    @Transactional
    @Override
    public List<String> getBatchOrderRecordNo(Integer recordNoNum) {
        return getRecordNoByTableName(EMPTY, "order", recordNoNum);
    }

    private List<String> getRecordNoByTableName(String prefix, String tableName, int recordNoNum) {
        // 悲观锁锁定数据库数据
        recordNoMgrRepository.lockRecord(tableName);

        RecordNoMgr recordNoMgr = recordNoMgrRepository.findByRecordNameEn(tableName);
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

        RecordLen recordLen = RecordLen.getByRecordLen(recordNoMgr.getRecordLen());
        if (!recordLen.getRecordLen().equals(recordNoMgr.getRecordLen())) {
            recordNoMgr.setRecordLen(recordLen.getRecordLen());
        }

        RecordType recordType = RecordType.getByRecordType(recordNoMgr.getRecordType());
        if (!recordType.getRecordType().equals(recordNoMgr.getRecordType())) {
            recordNoMgr.setRecordType(recordType.getRecordType());
        }

        switch (recordType) {
            case MAX_RECORD_NO:
            case RECORD_NO_MAX_RECORD_NO:
                if (String.valueOf(recordNoMgr.getMaxRecordNo()).length()
                        != recordLen.getRecordLen()) {
                    recordNoMgr.setWorkdate(new Date());
                    recordNoMgr.setMaxRecordNo(recordLen.getRecordValue());
                }
                break;
            case WORKDATE_MAX_RECORD_NO:
            case RECORD_NO_WORKDATE_MAX_RECORD_NO:
                // 如果日期不是当前日期，则更新
                if (!new Date(recordNoMgr.getWorkdate().getTime())
                        .toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate()
                        .equals(LocalDate.now())) {
                    recordNoMgr.setWorkdate(new Date());
                    recordNoMgr.setMaxRecordNo(0);
                }
                break;
            default:
        }

        // 当前值
        Integer currentMaxRecordNo = recordNoMgr.getMaxRecordNo();

        // 更新并获取
        recordNoMgr.setMaxRecordNo(recordNoMgr.getMaxRecordNo() + recordNoNum);
        recordNoMgrRepository.save(recordNoMgr);

        prefix = StringUtils.isEmpty(prefix) ? EMPTY : StringUtils.trimWhitespace(prefix);

        List<String> recordNoList = new ArrayList<>();
        StringBuilder recordNo = new StringBuilder();
        for (int i = 1; i <= recordNoNum; i++) {
            recordNo.delete(0, recordNo.length());
            recordNo.append(prefix);
            switch (recordType) {
                case MAX_RECORD_NO:
                    break;
                case RECORD_NO_MAX_RECORD_NO:
                    recordNo.append(recordNoMgr.getRecordNo());
                    break;
                case WORKDATE_MAX_RECORD_NO:
                    recordNo.append(LocalDate.now().format(formatter));
                    break;
                case RECORD_NO_WORKDATE_MAX_RECORD_NO:
                    recordNo.append(recordNoMgr.getRecordNo());
                    recordNo.append(LocalDate.now().format(formatter));
                    break;
                default:
            }
            recordNo.append(integerToString(currentMaxRecordNo++, recordLen.getRecordLen()));
            recordNoList.add(recordNo.toString());
        }

        log.info(
                "【记录号获取】表 {} 记录号 {}",
                tableName,
                Arrays.toString(recordNoList.toArray(new String[] {})));

        return recordNoList;
    }

    private String integerToString(Integer number, int len) {
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

    @Getter
    enum RecordType {
        MAX_RECORD_NO(1, "[5-8]位max_record_no"),
        RECORD_NO_MAX_RECORD_NO(2, "3位table_no+[5-8]位max_record_no"),
        WORKDATE_MAX_RECORD_NO(3, "8位workdate+[5-8]位max_record_no"),
        RECORD_NO_WORKDATE_MAX_RECORD_NO(4, "3位table_no+8位workdate+[5-8]位max_record_no"),
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

    @Getter
    enum RecordLen {
        LEN_FIVE(5, 10000),
        LEN_SIX(6, 100000),
        LEN_SEVEN(7, 1000000),
        LEN_EIGHT(8, 10000000),
        ;

        RecordLen(Integer recordLen, Integer recordValue) {
            this.recordLen = recordLen;
            this.recordValue = recordValue;
        }

        private Integer recordLen;
        private Integer recordValue;

        public static RecordLen getByRecordLen(Integer len) {
            for (RecordLen recordLen : RecordLen.class.getEnumConstants()) {
                if (recordLen.recordLen.equals(len)) {
                    return recordLen;
                }
            }
            return LEN_EIGHT;
        }

        @Override
        public String toString() {
            return "RecordLen{" + "recordLen=" + recordLen + ", recordValue=" + recordValue + '}';
        }
    }
}

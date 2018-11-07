/*
 * 文件名称：RecordNoMgrRepository.java
 * 系统名称：[系统名称]
 * 模块名称：[模块名称]
 * 软件版权：Copyright (c) 2011-2018, liming20110711@163.com All Rights Reserved.
 * 功能说明：[请在此处输入功能说明]
 * 开发人员：Rushing0711
 * 创建日期：20181106 23:34
 * 修改记录：
 * <Version>        <DateSerial>        <Author>        <Description>
 * 1.0.0            20181106-01         Rushing0711     M201811062334 新建文件
 ********************************************************************************/
package com.coding.wechat.component.recordno.repository;

import com.coding.wechat.component.recordno.DO.RecordNoMgr;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecordNoMgrRepository extends JpaRepository<RecordNoMgr, String> {
    RecordNoMgr findByTableName(String tableName);
}

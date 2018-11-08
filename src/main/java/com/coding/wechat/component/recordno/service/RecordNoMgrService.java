/*
 * 文件名称：RecordNoMgrService.java
 * 系统名称：[系统名称]
 * 模块名称：[模块名称]
 * 软件版权：Copyright (c) 2011-2018, liming20110711@163.com All Rights Reserved.
 * 功能说明：[请在此处输入功能说明]
 * 开发人员：Rushing0711
 * 创建日期：20181106 23:33
 * 修改记录：
 * <Version>        <DateSerial>        <Author>        <Description>
 * 1.0.0            20181106-01         Rushing0711     M201811062333 新建文件
 ********************************************************************************/
package com.coding.wechat.component.recordno.service;

import java.util.List;

public interface RecordNoMgrService {

    String getTenantRecordNo();

    List<String> getBatchTenantRecordNo(Integer recordNoNum);

    String getTenantRecordNo(String prefix);

    List<String> getBatchTenantRecordNo(String prefix, Integer recordNoNum);

    String getShopRecordNo();

    List<String> getBatchShopRecordNo(Integer recordNoNum);

    String getShopRecordNo(String prefix);

    List<String> getBatchShopRecordNo(String prefix, Integer recordNoNum);

    String getUserinfoRecordNo();

    List<String> getBatchUserinfoRecordNo(Integer recordNoNum);

    String getUserinfoRecordNo(String prefix);

    List<String> getBatchUserinfoRecordNo(String prefix, Integer recordNoNum);

    String getCustomerRecordNo();

    List<String> getBatchCustomerRecordNo(Integer recordNoNum);

    String getCustomerRecordNo(String prefix);

    List<String> getBatchCustomerRecordNo(String prefix, Integer recordNoNum);

    String getOrderRecordNo();

    List<String> getBatchOrderRecordNo(Integer recordNoNum);

    String getOrderRecordNo(String prefix);

    List<String> getBatchOrderRecordNo(String prefix, Integer recordNoNum);

    String getGoodsRecordNo();

    List<String> getBatchGoodsRecordNo(Integer recordNoNum);

    String getGoodsRecordNo(String prefix);

    List<String> getBatchGoodsRecordNo(String prefix, Integer recordNoNum);
}

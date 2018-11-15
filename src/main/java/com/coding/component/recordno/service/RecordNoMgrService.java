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
package com.coding.component.recordno.service;

import java.util.List;

public interface RecordNoMgrService {

    /** 租户编号. */
    String getTenantRecordNo();

    /** 批量租户编号. */
    List<String> getBatchTenantRecordNo(Integer recordNoNum);

    /** 门店编号：前缀是租户编号. */
    String getShopRecordNo(String prefix);

    /** 批量门店编号：前缀是租户编号. */
    List<String> getBatchShopRecordNo(String prefix, Integer recordNoNum);

    /** 商品票号：前缀是租户编号. */
    String getGoodsRecordNo(String prefix);

    /** 批量商品票号：前缀是租户编号. */
    List<String> getBatchGoodsRecordNo(String prefix, Integer recordNoNum);

    /** 用户编号：前缀是租户编号. */
    String getUserinfoRecordNo(String prefix);

    /** 批量用户编号：前缀是租户ID. */
    List<String> getBatchUserinfoRecordNo(String prefix, Integer recordNoNum);

    /** 会员编号. */
    String getCustomerRecordNo();

    /** 批量会员编号. */
    List<String> getBatchCustomerRecordNo(Integer recordNoNum);

    /** 订单编号. */
    String getOrderRecordNo();

    /** 批量订单编号. */
    List<String> getBatchOrderRecordNo(Integer recordNoNum);

    /** 商品SKU编号. */
    String getGoodsSpuNo();

    /** 批量商品SKU编号. */
    List<String> getBatchGoodsSpuNo(Integer recordNoNum);

    /** 商品SPEC编号：前缀是商品SKU编号. */
    String getGoodsSpecNo(String prefix);

    /** 批量商品SPEC编号：前缀是商品SKU编号. */
    List<String> getBatchGoodsSpecNo(String prefix, Integer recordNoNum);
}

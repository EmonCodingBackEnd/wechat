/*
 * 文件名称：RecordNoMgr.java
 * 系统名称：[系统名称]
 * 模块名称：[模块名称]
 * 软件版权：Copyright (c) 2011-2018, liming20110711@163.com All Rights Reserved.
 * 功能说明：[请在此处输入功能说明]
 * 开发人员：Rushing0711
 * 创建日期：20181106 23:10
 * 修改记录：
 * <Version>        <DateSerial>        <Author>        <Description>
 * 1.0.0            20181106-01         Rushing0711     M201811062310 新建文件
 ********************************************************************************/
package com.coding.wechat.component.recordno.DO;

import lombok.Data;
import lombok.Getter;

import javax.persistence.*;
import java.util.Date;

/**
 * 记录号生成管理表.
 *
 * <p>创建时间: <font style="color:#00FFFF">20181106 23:10</font><br>
 * [请在此输入功能详述]
 *
 * @author Rushing0711
 * @version 1.0.0
 * @since 1.0.0
 */
@Entity
@Table(name = "record_no_mgr")
@Data
public class RecordNoMgr {

    /** 记录号的数字标识. */
    @Id
    @Column(name = "record_no")
    private String recordNo;

    /** 记录号的类型. */
    @Column(name = "record_type")
    private Integer recordType;

    /** 记录号的英文标识. */
    @Column(name = "record_name_en")
    private String recordNameEn;

    /** 记录号的中文含义. */
    @Column(name = "record_name_ch")
    private String recordNameCh;

    /** 当前最大记录序号. */
    @Column(name = "max_record_no")
    private Integer maxRecordNo;

    /** 记录号变化部分的长度设置. */
    @Column(name = "record_len")
    private Integer recordLen;

    /** 工作日期. */
    @Temporal(TemporalType.DATE)
    @Column(name = "workdate")
    private Date workdate;

    /** 创建时间 */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time", nullable = false)
    private Date createTime;

    /** 修改时间 */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modify_time", nullable = false)
    private Date modifyTime;

    @Version
    @Column(name = "version", nullable = false)
    private int version;
}

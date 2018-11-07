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

    /** 表编号. */
    @Id private String tableNo;

    /** 编号类型. */
    @Column(name = "record_type")
    private Integer recordType;

    /** 表名称. */
    @Column(name = "table_name")
    private String tableName;

    /** 表中文名称. */
    @Column(name = "table_name_ch")
    private String tableNameCh;

    /** 最大序号. */
    @Column(name = "max_record_no")
    private Integer maxRecordNo;

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

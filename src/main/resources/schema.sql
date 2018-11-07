-- --------------------------------------------------------------------------------
-- spring.datasource.initialize=true时，自动调用
-- 建表脚本
-- 要求：
--    1、保证sql可以重复执行，也就是create之前先drop
--    2、sql关键字推荐大写
--    3、字段与表定义，必须带有注释
--    4、索引定义规则：
--      主键： primary key (`col1`, `col2`)
--      唯一索引： unique key `uqe_col1_col2...` (`col1`, `col2`)
--      索引： key `idx_col1_col2...` (`col1`, `col2`)
-- --------------------------------------------------------------------------------

drop index uqe_tableName on record_no_mgr;

drop table if exists record_no_mgr;

/*==============================================================*/
/* Table: record_no_mgr                                         */
/*==============================================================*/
create table record_no_mgr
(
   table_no             varchar(3) not null comment '表编号',
   record_type          tinyint not null comment '编号类型
            1-19位编号（3位table_no+8位workdate+8位max_record_no[从00000001开始])
            2-11位编号（3位table_no+8位max_record_no[从10000001开始]）
            3-8位编号（8位max_record_no[从10000001开始]）
            ',
   table_name           varchar(32) not null comment '表名称',
   table_name_ch        varchar(32) not null comment '表中文名称',
   max_record_no        int not null default 0 comment '当前最大序号',
   workdate             date not null default '2018-11-07' comment '工作日期',
   create_time          timestamp not null default current_timestamp comment '创建时间',
   modify_time          timestamp not null default current_timestamp on update current_timestamp comment '更新时间',
   version              int not null default 0 comment '版本控制',
   primary key (table_no)
);

alter table record_no_mgr comment '记录号生成管理表';

/*==============================================================*/
/* Index: uqe_tableName                                         */
/*==============================================================*/
create unique index uqe_tableName on record_no_mgr
(
   table_name
);

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

drop index uqe_recordNameEn on record_no_mgr;

drop table if exists record_no_mgr;

/*==============================================================*/
/* Table: record_no_mgr                                         */
/*==============================================================*/
create table record_no_mgr
(
   record_no            varchar(3) not null comment '记录号的数字标识',
   record_type          tinyint not null comment '记录号的类型
            1-[5-8]位max_record_no
            2-3位table_no+[5-8]位max_record_no
            3-8位workdate+[5-8]位max_record_no
            4-3位table_no+8位workdate+[5-8]位max_record_no

            ',
   record_name_en       varchar(32) not null comment '记录号的英文标识',
   record_name_ch       varchar(32) not null comment '记录号的中文含义',
   max_record_no        int not null default 0 comment '当前最大记录序号',
   record_len           tinyint not null default 8 comment '记录号变化部分的长度设置',
   workdate             date not null default '2018-11-07' comment '工作日期',
   create_time          timestamp not null default current_timestamp comment '创建时间',
   modify_time          timestamp not null default current_timestamp on update current_timestamp comment '更新时间',
   version              int not null default 0 comment '版本控制',
   primary key (record_no)
);

alter table record_no_mgr comment '记录号生成管理表';

/*==============================================================*/
/* Index: uqe_recordNameEn                                      */
/*==============================================================*/
create unique index uqe_recordNameEn on record_no_mgr
(
   record_name_en
);

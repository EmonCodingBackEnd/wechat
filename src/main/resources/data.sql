-- --------------------------------------------------------------------------------
-- 初始化数据脚本
-- 要求：
--    1、保证sql可以重复执行，也就是insert之前先delete（删除表还是某一条，根据需要而定）
--    2、sql关键字推荐大写
-- --------------------------------------------------------------------------------

-- 数据文件
-- 示例
-- DELETE FROM `user` WHERE id=1;
-- INSERT INTO `user` (id, create_time, modify_time, version, acct, intro, mgr_shops, mobile, name, pwd, role_id, sex, status) VALUES(1, '2018-04-16 18:46:01.000', '2018-04-16 18:46:04.000', 0, 'acct', NULL, '123456,678910', '13777485757', 'wtf', 'e10adc3949ba59abbe56e057f20f883e', 0, NULL, '1');

DELETE FROM record_no_mgr WHERE table_no='101';
INSERT INTO record_no_mgr (table_no, record_type, table_name, table_name_ch, max_record_no, workdate, create_time, modify_time, version) VALUES('101', 1, 'userinfo', '用户信息表11', 0, '2018-11-07', '2018-11-07 12:07:07.000', '2018-11-07 14:58:28.000', 0);
DELETE FROM record_no_mgr WHERE table_no='102';
INSERT INTO record_no_mgr (table_no, record_type, table_name, table_name_ch, max_record_no, workdate, create_time, modify_time, version) VALUES('102', 3, 'tenant', '租户表11', 0, '2018-11-07', '2018-11-07 12:07:23.000', '2018-11-07 14:58:28.000', 0);
DELETE FROM record_no_mgr WHERE table_no='103';
INSERT INTO record_no_mgr (table_no, record_type, table_name, table_name_ch, max_record_no, workdate, create_time, modify_time, version) VALUES('103', 3, 'shop', '门店表11', 0, '2018-11-07', '2018-11-07 12:07:43.000', '2018-11-07 14:58:28.000', 0);


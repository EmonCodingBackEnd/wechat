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

INSERT INTO record_no_mgr (record_no, record_type, record_name_en, record_name_ch, max_record_no, record_len, workdate, create_time, modify_time, version) VALUES('101', 1, 'userinfo', '用户信息表', 0, 8, '2018-11-09', '2018-11-07 12:07:07.000', '2018-11-09 02:47:43.000', 0);
INSERT INTO record_no_mgr (record_no, record_type, record_name_en, record_name_ch, max_record_no, record_len, workdate, create_time, modify_time, version) VALUES('102', 2, 'tenant', '租户表', 0, 8, '2018-11-09', '2018-11-07 12:07:23.000', '2018-11-09 02:47:43.000', 0);
INSERT INTO record_no_mgr (record_no, record_type, record_name_en, record_name_ch, max_record_no, record_len, workdate, create_time, modify_time, version) VALUES('103', 3, 'shop', '门店表', 0, 8, '2018-11-09', '2018-11-07 12:07:43.000', '2018-11-09 02:47:43.000', 0);
INSERT INTO record_no_mgr (record_no, record_type, record_name_en, record_name_ch, max_record_no, record_len, workdate, create_time, modify_time, version) VALUES('104', 4, 'customer', '会员表', 0, 8, '2018-11-09', '2018-11-07 12:07:07.000', '2018-11-09 02:47:43.000', 0);
INSERT INTO record_no_mgr (record_no, record_type, record_name_en, record_name_ch, max_record_no, record_len, workdate, create_time, modify_time, version) VALUES('105', 4, 'order', '订单号', 0, 8, '2018-11-09', '2018-11-07 12:07:07.000', '2018-11-09 02:47:43.000', 0);
INSERT INTO record_no_mgr (record_no, record_type, record_name_en, record_name_ch, max_record_no, record_len, workdate, create_time, modify_time, version) VALUES('106', 4, 'goods', '商品票号', 0, 8, '2018-11-09', '2018-11-07 12:07:07.000', '2018-11-09 02:47:43.000', 0);

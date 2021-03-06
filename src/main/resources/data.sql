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


DELETE FROM record_no_mgr WHERE record_no='101';
INSERT INTO record_no_mgr (record_no, record_type, record_name_en, record_name_ch, max_record_no, record_len, workdate, create_time, modify_time, version) VALUES('101', 1, 'tenant', '租户表', 0, 5, '2018-11-09', '2018-11-07 12:07:23.000', '2018-11-09 02:47:43.000', 0);
DELETE FROM record_no_mgr WHERE record_no='102';
INSERT INTO record_no_mgr (record_no, record_type, record_name_en, record_name_ch, max_record_no, record_len, workdate, create_time, modify_time, version) VALUES('102', 1, 'shop', '门店表', 0, 5, '2018-11-09', '2018-11-07 12:07:43.000', '2018-11-09 02:47:43.000', 0);
DELETE FROM record_no_mgr WHERE record_no='103';
INSERT INTO record_no_mgr (record_no, record_type, record_name_en, record_name_ch, max_record_no, record_len, workdate, create_time, modify_time, version) VALUES('103', 3, 'goods', '商品票号', 0, 5, '2018-11-09', '2018-11-07 12:07:07.000', '2018-11-09 09:53:21.000', 0);
DELETE FROM record_no_mgr WHERE record_no='104';
INSERT INTO record_no_mgr (record_no, record_type, record_name_en, record_name_ch, max_record_no, record_len, workdate, create_time, modify_time, version) VALUES('104', 1, 'userinfo', '用户信息表', 0, 5, '2018-11-09', '2018-11-07 12:07:07.000', '2018-11-09 02:47:43.000', 0);
DELETE FROM record_no_mgr WHERE record_no='105';
INSERT INTO record_no_mgr (record_no, record_type, record_name_en, record_name_ch, max_record_no, record_len, workdate, create_time, modify_time, version) VALUES('105', 4, 'customer', '会员表', 0, 8, '2018-11-09', '2018-11-07 12:07:07.000', '2018-11-09 09:53:20.000', 0);
DELETE FROM record_no_mgr WHERE record_no='106';
INSERT INTO record_no_mgr (record_no, record_type, record_name_en, record_name_ch, max_record_no, record_len, workdate, create_time, modify_time, version) VALUES('106', 4, 'order', '订单号', 0, 8, '2018-11-09', '2018-11-07 12:07:07.000', '2018-11-09 09:53:20.000', 0);
DELETE FROM record_no_mgr WHERE record_no='107';
INSERT INTO record_no_mgr (record_no, record_type, record_name_en, record_name_ch, max_record_no, record_len, workdate, create_time, modify_time, version) VALUES('107', 4, 'goods_spu_no', '商品SPU编号', 0, 8, '2018-11-09', '2018-11-07 12:07:07.000', '2018-11-09 09:53:20.000', 0);
DELETE FROM record_no_mgr WHERE record_no='108';
INSERT INTO record_no_mgr (record_no, record_type, record_name_en, record_name_ch, max_record_no, record_len, workdate, create_time, modify_time, version) VALUES('108', 1, 'goods_spec_no', '商品SPEC编号', 0, 5, '2018-11-09', '2018-11-07 12:07:07.000', '2018-11-09 09:53:20.000', 0);

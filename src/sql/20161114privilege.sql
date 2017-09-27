/*
SQLyog 企业版 - MySQL GUI v8.14 
MySQL - 5.6.28 : Database - test_privilege_ys
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`test_privilege_ys` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_bin */;

USE `test_privilege_ys`;

/*Table structure for table `tbl_privilege_acl` */

DROP TABLE IF EXISTS `tbl_privilege_acl`;

CREATE TABLE `tbl_privilege_acl` (
  `id` varchar(32) COLLATE utf8_bin NOT NULL,
  `release_id` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '来源id',
  `release_sn` varchar(10) COLLATE utf8_bin DEFAULT 'role' COMMENT '来源标示role标示角色user 标示用户',
  `system_sn` varchar(40) COLLATE utf8_bin DEFAULT NULL COMMENT '系统标示',
  `module_id` varchar(40) COLLATE utf8_bin DEFAULT NULL COMMENT '模块id',
  `module_sn` varchar(40) COLLATE utf8_bin DEFAULT NULL COMMENT '模块标示',
  `acl_state` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `creator` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '创建人',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  `updator` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '更新人',
  `del_flag` int(1) DEFAULT '1' COMMENT '删除标识：0：删除 1：存在',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `tbl_privilege_company` */

DROP TABLE IF EXISTS `tbl_privilege_company`;

CREATE TABLE `tbl_privilege_company` (
  `id` varchar(32) COLLATE utf8_bin NOT NULL,
  `pid` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '上级公司id',
  `cname` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '公司中文名称',
  `ename` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '公司英文名称',
  `code` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '公司code',
  `descr` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '描述',
  `status` int(1) NOT NULL DEFAULT '1' COMMENT '状态 1启用 0禁用',
  `creator` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `updator` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `del_flag` int(1) NOT NULL DEFAULT '1' COMMENT '1：存在  0： 删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `tbl_privilege_department` */

DROP TABLE IF EXISTS `tbl_privilege_department`;

CREATE TABLE `tbl_privilege_department` (
  `id` varchar(32) COLLATE utf8_bin NOT NULL,
  `company_id` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '公司id',
  `name` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '名称',
  `note` varchar(80) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `pid` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '父id',
  `order_no` int(11) DEFAULT NULL COMMENT '排序号',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `creator` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '创建人',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  `updator` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '更新人',
  `del_flag` int(1) DEFAULT '1' COMMENT '删除标识0表示删除1表示存在',
  `leader` int(1) DEFAULT '0' COMMENT '是否是leader1:是 0：不是',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `tbl_privilege_dictionary` */

DROP TABLE IF EXISTS `tbl_privilege_dictionary`;

CREATE TABLE `tbl_privilege_dictionary` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '编码',
  `name` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '名称',
  `pcode` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '父编码',
  `system_sn` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '系统标识',
  `sn` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '标识',
  `order_no` int(11) DEFAULT NULL COMMENT '排序号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `tbl_privilege_icsystem` */

DROP TABLE IF EXISTS `tbl_privilege_icsystem`;

CREATE TABLE `tbl_privilege_icsystem` (
  `id` varchar(40) COLLATE utf8_bin NOT NULL,
  `name` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '名称',
  `sn` varchar(40) COLLATE utf8_bin DEFAULT NULL COMMENT '系统标示',
  `url` varchar(60) COLLATE utf8_bin DEFAULT NULL COMMENT '系统url前缀',
  `image` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '系统的图标',
  `note` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '系统备注',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `creator` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '创建人',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  `updator` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '更新人',
  `del_flag` int(1) DEFAULT '1' COMMENT '删除标识0：删除1：存在',
  `order_no` int(2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `sn_unique_index` (`sn`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `tbl_privilege_icsystem_copy` */

DROP TABLE IF EXISTS `tbl_privilege_icsystem_copy`;

CREATE TABLE `tbl_privilege_icsystem_copy` (
  `id` varchar(40) COLLATE utf8_bin NOT NULL,
  `name` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '名称',
  `sn` varchar(40) COLLATE utf8_bin DEFAULT NULL COMMENT '系统标示',
  `url` varchar(60) COLLATE utf8_bin DEFAULT NULL COMMENT '系统url前缀',
  `image` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '系统的图标',
  `note` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '系统备注',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `creator` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '创建人',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  `updator` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '更新人',
  `del_flag` int(1) DEFAULT '1' COMMENT '删除标识0：删除1：存在',
  `order_no` int(2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `sn_unique_index` (`sn`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `tbl_privilege_login_log` */

DROP TABLE IF EXISTS `tbl_privilege_login_log`;

CREATE TABLE `tbl_privilege_login_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ip` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '访问ip',
  `operation_id` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '操作人id',
  `operation_username` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '操作人的姓名',
  `operation_person` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '操作人姓名',
  `operation_content` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '操作内容',
  `operation_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6503 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `tbl_privilege_module` */

DROP TABLE IF EXISTS `tbl_privilege_module`;

CREATE TABLE `tbl_privilege_module` (
  `id` varchar(40) COLLATE utf8_bin NOT NULL,
  `name` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '名称',
  `url` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '链接',
  `sn` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '标识',
  `state` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '存放该模块有哪些权限值可选',
  `image` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '图片路径',
  `order_no` int(5) DEFAULT NULL COMMENT '模块的排序号',
  `pid` varchar(40) COLLATE utf8_bin DEFAULT NULL COMMENT '父模块id',
  `system_id` varchar(40) COLLATE utf8_bin DEFAULT NULL COMMENT '系统id',
  `is_show` int(2) DEFAULT '1',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `creator` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '创建人',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  `updator` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '更新人',
  `del_flag` int(1) DEFAULT '1' COMMENT '删除标识0:删除1：存在',
  PRIMARY KEY (`id`),
  UNIQUE KEY `system_module_sn_index` (`sn`,`system_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `tbl_privilege_pvalue` */

DROP TABLE IF EXISTS `tbl_privilege_pvalue`;

CREATE TABLE `tbl_privilege_pvalue` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `system_id` varchar(40) DEFAULT NULL COMMENT '系统id',
  `position` int(3) NOT NULL COMMENT '整型的位',
  `name` varchar(32) NOT NULL COMMENT '名称',
  `order_no` int(11) DEFAULT NULL COMMENT '排序号',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `creator` varchar(32) DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `updator` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `position_only_index` (`position`),
  KEY `name_only_index` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=507 DEFAULT CHARSET=utf8;

/*Table structure for table `tbl_privilege_role` */

DROP TABLE IF EXISTS `tbl_privilege_role`;

CREATE TABLE `tbl_privilege_role` (
  `id` varchar(32) COLLATE utf8_bin NOT NULL,
  `company_id` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '公司id',
  `name` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '名称',
  `sn` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '标识',
  `note` varchar(80) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `creator` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '创建人',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  `updator` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '更新人',
  `del_flag` int(1) DEFAULT '1' COMMENT '删除标识0：删除 1：存在',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `tbl_privilege_session_data` */

DROP TABLE IF EXISTS `tbl_privilege_session_data`;

CREATE TABLE `tbl_privilege_session_data` (
  `session_id` varchar(32) COLLATE utf8_bin NOT NULL COMMENT 'sessionId',
  `user_info` text COLLATE utf8_bin COMMENT '用户信息',
  `acls_info` text COLLATE utf8_bin COMMENT '权限列表',
  `other_info` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '其他的信息',
  `creation_time` bigint(20) DEFAULT NULL COMMENT 'session时间戳',
  PRIMARY KEY (`session_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `tbl_privilege_shortcut_menu` */

DROP TABLE IF EXISTS `tbl_privilege_shortcut_menu`;

CREATE TABLE `tbl_privilege_shortcut_menu` (
  `id` varchar(32) COLLATE utf8_bin NOT NULL,
  `icon` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '图标',
  `url` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT 'url',
  `ic_system_id` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '系统id',
  `module_id` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '模块id',
  `order_no` int(11) DEFAULT NULL COMMENT '排序号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `tbl_privilege_system_config` */

DROP TABLE IF EXISTS `tbl_privilege_system_config`;

CREATE TABLE `tbl_privilege_system_config` (
  `id` varchar(32) COLLATE utf8_bin NOT NULL,
  `config_name` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '配置名称',
  `config_sn` varchar(60) COLLATE utf8_bin DEFAULT NULL COMMENT '配置标示',
  `config_key` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '配置key',
  `config_value` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '配置key的value值',
  `remark` varchar(80) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `creator` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '创建人',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  `updator` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '更新人',
  `del_flag` int(1) DEFAULT '1' COMMENT '删除标识0：删除1：存在',
  `config_order` int(2) DEFAULT NULL COMMENT '排序号',
  `image` longblob COMMENT '图片',
  PRIMARY KEY (`id`),
  UNIQUE KEY `config_key_unique` (`config_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `tbl_privilege_user` */

DROP TABLE IF EXISTS `tbl_privilege_user`;

CREATE TABLE `tbl_privilege_user` (
  `id` varchar(32) COLLATE utf8_bin NOT NULL,
  `real_name` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '真实姓名',
  `username` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '用户名',
  `password` varchar(40) COLLATE utf8_bin DEFAULT NULL COMMENT '密码',
  `tel` varchar(15) COLLATE utf8_bin DEFAULT NULL COMMENT '电话',
  `phone` varchar(15) COLLATE utf8_bin DEFAULT NULL COMMENT '座机',
  `mobile` varchar(15) COLLATE utf8_bin DEFAULT NULL COMMENT '手机',
  `email` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '邮箱',
  `image` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '头像',
  `company_id` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '公司id',
  `department_id` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '部门id',
  `it_user_id` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT 'it用户id',
  `it_user_name` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT 'it用户姓名',
  `is_leader` int(1) DEFAULT '0' COMMENT '是否是领导1:是  0:否',
  `sex` int(1) DEFAULT '0' COMMENT '性别 0标示男 1标示女  2',
  `address` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '地址',
  `fax` varchar(15) COLLATE utf8_bin DEFAULT NULL COMMENT '传真',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `creator` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '创建人',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  `fail_month` int(3) NOT NULL COMMENT '失效月数',
  `failure_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '失效时间',
  `pwd_ftime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '密码失效日期',
  `pwd_init` int(1) NOT NULL DEFAULT '0' COMMENT '初始密码是否已修改 1是0否',
  `updator` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '更新人',
  `del_flag` int(1) DEFAULT '1' COMMENT '删除标识  0标识已删除   1标识未删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_index` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `tbl_privilege_user_company` */

DROP TABLE IF EXISTS `tbl_privilege_user_company`;

CREATE TABLE `tbl_privilege_user_company` (
  `id` varchar(32) COLLATE utf8_bin NOT NULL,
  `user_id` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '用户id',
  `company_id` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '公司id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `creator` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `updator` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `del_flag` int(1) DEFAULT '1' COMMENT '1:存在  0：删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `tbl_privilege_user_role` */

DROP TABLE IF EXISTS `tbl_privilege_user_role`;

CREATE TABLE `tbl_privilege_user_role` (
  `id` varchar(32) COLLATE utf8_bin NOT NULL,
  `user_id` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '用户id',
  `role_id` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '角色id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `creator` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '创建人',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  `updator` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '更新人',
  `del_flag` int(1) DEFAULT '1' COMMENT '删除标识0：删除1：存在',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `tbl_privilege_user_system` */

DROP TABLE IF EXISTS `tbl_privilege_user_system`;

CREATE TABLE `tbl_privilege_user_system` (
  `id` varchar(32) COLLATE utf8_bin NOT NULL,
  `user_id` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '用户id',
  `system_id` varchar(40) COLLATE utf8_bin DEFAULT NULL COMMENT '系统id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `creator` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '创建人',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  `updator` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '更新人',
  `del_flag` int(1) DEFAULT '1' COMMENT '删除标识0:删除1：存在',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `tbl_sms_delayinfo` */

DROP TABLE IF EXISTS `tbl_sms_delayinfo`;

CREATE TABLE `tbl_sms_delayinfo` (
  `id` varchar(32) COLLATE utf8_bin NOT NULL,
  `create_time` timestamp NULL DEFAULT NULL,
  `delay_second` int(11) DEFAULT NULL,
  `delay_time` timestamp NULL DEFAULT NULL,
  `sms_info_json` varchar(800) COLLATE utf8_bin DEFAULT NULL,
  `status` int(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `tbl_sms_emailtemplate` */

DROP TABLE IF EXISTS `tbl_sms_emailtemplate`;

CREATE TABLE `tbl_sms_emailtemplate` (
  `id` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '主键',
  `name` varchar(60) COLLATE utf8_bin NOT NULL COMMENT '模板名称',
  `sn` varchar(40) COLLATE utf8_bin NOT NULL COMMENT '模板标识',
  `content` longtext COLLATE utf8_bin NOT NULL COMMENT '模板内容',
  `creator` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL,
  `updator` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='邮件模板';

/*Table structure for table `tbl_sms_info` */

DROP TABLE IF EXISTS `tbl_sms_info`;

CREATE TABLE `tbl_sms_info` (
  `id` varchar(96) DEFAULT NULL,
  `source_name` varchar(300) DEFAULT NULL,
  `type` double DEFAULT NULL,
  `tel_num` varchar(96) DEFAULT NULL,
  `content` varchar(900) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creator` varchar(96) DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `updator` varchar(96) DEFAULT NULL,
  `del_flag` double DEFAULT NULL,
  `status` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `tbl_systemmgt_job` */

DROP TABLE IF EXISTS `tbl_systemmgt_job`;

CREATE TABLE `tbl_systemmgt_job` (
  `id` varchar(32) NOT NULL,
  `job_name` varchar(200) NOT NULL COMMENT '任务名称',
  `group_name` varchar(200) NOT NULL COMMENT '任务组',
  `cron_expression` varchar(200) NOT NULL COMMENT '调度配置',
  `job_class` varchar(400) NOT NULL COMMENT '调度具体实现类',
  `running_ip` varchar(50) NOT NULL COMMENT '限制开放ip',
  `status` varchar(20) NOT NULL COMMENT '状态（1、启用，2、停止）',
  `description` varchar(200) NOT NULL COMMENT '描述',
  `create_by` varchar(32) NOT NULL COMMENT '有谁操作',
  `create_time` varchar(20) NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) DEFAULT NULL COMMENT '有谁更新',
  `update_time` varchar(20) DEFAULT NULL COMMENT '更新时间',
  `enabled_flag` char(1) DEFAULT NULL COMMENT '是否禁用',
  `last_runtime` varchar(30) DEFAULT NULL COMMENT '最后执行时间',
  PRIMARY KEY (`id`),
  KEY `idx_job_class` (`job_class`(255))
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统调度任务表';

/*Table structure for table `tbl_systemmgt_job_log` */

DROP TABLE IF EXISTS `tbl_systemmgt_job_log`;

CREATE TABLE `tbl_systemmgt_job_log` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `job_class` varchar(100) NOT NULL COMMENT '调度具体实现类',
  `start_time` varchar(50) NOT NULL COMMENT '开始时间',
  `run_time` int(11) NOT NULL COMMENT '执行时长',
  `error` text NOT NULL COMMENT '错误信息',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统调度日志表';

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

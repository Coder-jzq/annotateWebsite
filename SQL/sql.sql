/*
Navicat MySQL Data Transfer

Source Server         : 人工数据标注(杨杰师兄腾讯云)
Source Server Version : 80032
Source Host           : 43.140.212.52:3306
Source Database       : yjdata

Target Server Type    : MYSQL
Target Server Version : 80032
File Encoding         : 65001

Date: 2023-08-03 10:48:11
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for bz_ann_record
-- ----------------------------
DROP TABLE IF EXISTS `bz_ann_record`;
CREATE TABLE `bz_ann_record` (
  `ann_record_id` int NOT NULL AUTO_INCREMENT,
  `ann_record_dh` int DEFAULT NULL COMMENT '第几个对话',
  `ann_record_jz` int DEFAULT NULL COMMENT '第几个句子',
  `ann_record_user` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '标注人',
  PRIMARY KEY (`ann_record_id`)
) ENGINE=InnoDB AUTO_INCREMENT=149507 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Table structure for bz_record
-- ----------------------------
DROP TABLE IF EXISTS `bz_record`;
CREATE TABLE `bz_record` (
  `record_id` int NOT NULL AUTO_INCREMENT,
  `record_index` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `record_text` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `record_user1` int(10) unsigned zerofill DEFAULT NULL,
  `record_user2` int(10) unsigned zerofill DEFAULT NULL,
  `record_user3` int(10) unsigned zerofill DEFAULT NULL,
  `record_user4` int(10) unsigned zerofill DEFAULT NULL,
  `record_user5` int(10) unsigned zerofill DEFAULT NULL,
  `record_user6` int DEFAULT '0',
  `record_user7` int DEFAULT '0',
  `record_user8` int DEFAULT '0',
  `record_user9` int DEFAULT '0',
  `record_user10` int DEFAULT '0',
  PRIMARY KEY (`record_id`)
) ENGINE=InnoDB AUTO_INCREMENT=277675 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Table structure for bz_record_pause
-- ----------------------------
DROP TABLE IF EXISTS `bz_record_pause`;
CREATE TABLE `bz_record_pause` (
  `record_id` int NOT NULL AUTO_INCREMENT,
  `record_index` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `record_text` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `record_user1` int(10) unsigned zerofill DEFAULT NULL,
  `record_user2` int(10) unsigned zerofill DEFAULT NULL,
  `record_user3` int(10) unsigned zerofill DEFAULT NULL,
  `record_user4` int(10) unsigned zerofill DEFAULT NULL,
  `record_user5` int(10) unsigned zerofill DEFAULT NULL,
  `record_user6` int DEFAULT '0',
  `record_user7` int DEFAULT '0',
  `record_user8` int DEFAULT '0',
  `record_user9` int DEFAULT '0',
  `record_user10` int DEFAULT '0',
  PRIMARY KEY (`record_id`)
) ENGINE=InnoDB AUTO_INCREMENT=277675 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Table structure for bz_user
-- ----------------------------
DROP TABLE IF EXISTS `bz_user`;
CREATE TABLE `bz_user` (
  `user_id` int NOT NULL,
  `username` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `password` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `user_label` int DEFAULT NULL COMMENT '用户id(区别标记)',
  `status` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户身份(管理/用户)',
  `differ` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

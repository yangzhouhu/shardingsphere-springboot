
-- ----------------------------
-- 创建分库分表所在数据库
-- ----------------------------
create database `sharding_1`;
create database `sharding_2`;




-- -----------------------------
-- 以下表需要在上述两个数据库都创建一份
-- -----------------------------
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;
-- ----------------------------
-- Table structure for broad_table
-- ----------------------------
DROP TABLE IF EXISTS `broad_table`;
CREATE TABLE `broad_table`  (
  `id` bigint(20) NOT NULL,
  `code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `enable` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否可用',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for default_table_1
-- ----------------------------
DROP TABLE IF EXISTS `default_table_1`;
CREATE TABLE `default_table_1`  (
  `id` bigint(20) NOT NULL,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `enable` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否可用',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for default_table_2
-- ----------------------------
DROP TABLE IF EXISTS `default_table_2`;
CREATE TABLE `default_table_2`  (
  `id` bigint(20) NOT NULL,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `enable` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否可用',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for my_sharding_1
-- ----------------------------
DROP TABLE IF EXISTS `my_sharding_1`;
CREATE TABLE `my_sharding_1`  (
  `order_id` bigint(20) NOT NULL,
  `user_id` int(11) NOT NULL,
  `status` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `order_no` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `enable` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否可用',
  PRIMARY KEY (`order_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for my_sharding_2
-- ----------------------------
DROP TABLE IF EXISTS `my_sharding_2`;
CREATE TABLE `my_sharding_2`  (
  `order_id` bigint(20) NOT NULL,
  `user_id` int(11) NOT NULL,
  `status` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `order_no` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `enable` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否可用',
  PRIMARY KEY (`order_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for undo_log
-- ----------------------------
DROP TABLE IF EXISTS `undo_log`;
CREATE TABLE `undo_log`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `branch_id` bigint(20) NOT NULL,
  `xid` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `context` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `rollback_info` longblob NOT NULL,
  `log_status` int(11) NOT NULL,
  `log_created` datetime(0) NOT NULL,
  `log_modified` datetime(0) NOT NULL,
  `ext` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `ux_undo_log`(`xid`, `branch_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50728
 Source Host           : localhost:3306
 Source Schema         : userman

 Target Server Type    : MySQL
 Target Server Version : 50728
 File Encoding         : 65001

 Date: 10/02/2023 15:32:18
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '产品id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '产品名',
  `description` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '产品价格',
  `sku` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '产品数量',
  `owner_user_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '产品创建者',
  `quantity` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `manufacturer` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `date_added` datetime(0) NULL DEFAULT NULL,
  `date_last_updated` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of product
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '邮箱',
  `first_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓',
  `last_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名',
  `account_created` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `account_update` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `email`(`email`) USING BTREE COMMENT '索引'
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (9, '$2a$10$elVRYOuuXXbdkYO8XIgTR.50JDq6NVNeQY9XRAXK9qX84sNPmgE.C', '175@qq.com', '李', '白', '2023-01-29 21:34:32', '2023-01-29 21:34:32');
-- INSERT INTO `user` VALUES (11, '$2a$10$y9D0.238WeGhFPvEuya9gelmSwkl0LA0VlGX0LJIFWHGCKZ9AH6Ee', '28333@qq.com', '李', '白', '2023-01-30 16:34:38', '2023-01-31 14:02:02');
INSERT INTO `user` VALUES (12, '$2a$10$U7nm8FwNYy13fNvqZ7LM3.ftZ6CbnkZtj7dB5pwsuKky1T.UFBKh6', '22@qq.com', '李', '白', '2023-01-30 16:37:28', '2023-01-30 16:37:28');
INSERT INTO `user` VALUES (14, '$2a$10$w6nNnZjyr4TL5tut94bAxe72OWEuG6XpAvSYT4eMc1Oof1WP5qMxq', '12@qq.com', '李', '白', '2023-02-01 13:18:18', '2023-02-01 13:18:18');
INSERT INTO `user` VALUES (15, '$2a$10$QhexzK0.12LC33kczr4dh.3emUUjcgtK1t.F3PIhAmZT91hqvx0Km', '11@qq.com', '小所1122属', 'hou222', '2023-02-01 13:24:08', '2023-02-01 13:29:58');
INSERT INTO `user` VALUES (17, '$2a$10$.Izmmyqej/7A.h2Bfcit9.EHnhGee.saiDugyq6yTF/TxDTax2J66', '111@qq.com', '111', '222', '2023-02-01 13:55:04', '2023-02-01 14:09:12');
INSERT INTO `user` VALUES (18, '$2a$10$RpRgHwuDY9gd3cNgpYxyquryrWkoB1BJB46AubPTcvoN/r6kmL.qa', '123456@qq.com', '22', '22', '2023-02-01 14:37:36', '2023-02-01 14:40:36');
INSERT INTO `user` VALUES (19, '$2a$10$rM6tlX3LXhQ3FTpN82h.DOO8pmk78qcoQOoKJy2khfcSmuI.gMTVW', '1234566@qq.com', '李', '白', '2023-02-01 15:13:45', '2023-02-01 15:13:45');

SET FOREIGN_KEY_CHECKS = 1;

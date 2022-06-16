/*
 Navicat Premium Data Transfer

 Source Server         : root
 Source Server Type    : MySQL
 Source Server Version : 80026
 Source Host           : localhost:3306
 Source Schema         : attendance

 Target Server Type    : MySQL
 Target Server Version : 80026
 File Encoding         : 65001

 Date: 08/06/2022 17:30:19
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for attendance
-- ----------------------------
DROP TABLE IF EXISTS `attendance`;
CREATE TABLE `attendance`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `userName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `requiredDays` int NULL DEFAULT 22,
  `actualDays` int NULL DEFAULT NULL,
  `overtimeDays` int NULL DEFAULT NULL,
  `work_overtime_id` int NULL DEFAULT NULL,
  `user_id` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `userName`(`userName`) USING BTREE,
  INDEX `work_overtime_id`(`work_overtime_id`) USING BTREE,
  INDEX `attendance_ibfk_4`(`user_id`) USING BTREE,
  CONSTRAINT `attendance_ibfk_1` FOREIGN KEY (`userName`) REFERENCES `user` (`user_name`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `attendance_ibfk_2` FOREIGN KEY (`work_overtime_id`) REFERENCES `work_overtime` (`work_overtime_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `attendance_ibfk_4` FOREIGN KEY (`user_id`) REFERENCES `work_overtime` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of attendance
-- ----------------------------
INSERT INTO `attendance` VALUES (1, '李', 22, 23, 1, 1, 2);
INSERT INTO `attendance` VALUES (3, '王菲              ', 22, 26, 4, 21, 11);
INSERT INTO `attendance` VALUES (4, '王', 22, 26, 4, 30, 34);
INSERT INTO `attendance` VALUES (5, 'mm', 22, 26, 4, 32, 31);
INSERT INTO `attendance` VALUES (7, NULL, 22, NULL, NULL, 34, 10);
INSERT INTO `attendance` VALUES (28, NULL, 22, NULL, NULL, NULL, 25);

SET FOREIGN_KEY_CHECKS = 1;

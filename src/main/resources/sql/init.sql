-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        10.6.2-MariaDB - mariadb.org binary distribution
-- 服务器操作系统:                      Win64
-- HeidiSQL 版本:                  11.3.0.6295
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

-- 导出  表 userman.image 结构
CREATE TABLE IF NOT EXISTS `image` (
  `image_id` int(64) NOT NULL AUTO_INCREMENT,
  `product_id` int(64) DEFAULT NULL,
  `fileName` varchar(50) DEFAULT NULL,
  `date_created` datetime DEFAULT NULL,
  `s3_bucket_path` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`image_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- 正在导出表  userman.image 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `image` DISABLE KEYS */;
/*!40000 ALTER TABLE `image` ENABLE KEYS */;

-- 导出  表 userman.product 结构
CREATE TABLE IF NOT EXISTS `product` (
  `id` int(64) NOT NULL AUTO_INCREMENT COMMENT '产品id',
  `name` varchar(255) DEFAULT NULL COMMENT '产品名',
  `description` varchar(500) DEFAULT NULL COMMENT '产品价格',
  `sku` varchar(100) DEFAULT NULL COMMENT '产品数量',
  `manufacturer` varchar(255) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `date_added` datetime DEFAULT NULL,
  `date_last_updated` datetime DEFAULT NULL,
  `owner_user_id` int(64) DEFAULT NULL COMMENT '产品创建者',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;

-- 正在导出表  userman.product 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
/*!40000 ALTER TABLE `product` ENABLE KEYS */;

-- 导出  表 userman.user 结构
CREATE TABLE IF NOT EXISTS `user` (
  `id` int(64) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `first_name` varchar(50) DEFAULT NULL COMMENT '姓',
  `last_name` varchar(20) DEFAULT NULL COMMENT '名',
  `password` varchar(64) DEFAULT NULL COMMENT '密码',
  `username` varchar(50) NOT NULL COMMENT '邮箱',
  `account_created` datetime DEFAULT NULL COMMENT '创建时间',
  `account_update` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `email` (`username`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;

-- 正在导出表  userman.user 的数据：~7 rows (大约)
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
-- INSERT INTO `user` (`id`, `first_name`, `last_name`, `password`, `username`, `account_created`, `account_update`) VALUES
-- 	(9, '李', '白', '$2a$10$elVRYOuuXXbdkYO8XIgTR.50JDq6NVNeQY9XRAXK9qX84sNPmgE.C', '175@qq.com', '2023-01-29 21:34:32', '2023-01-29 21:34:32'),
-- 	(12, '李', '白', '$2a$10$U7nm8FwNYy13fNvqZ7LM3.ftZ6CbnkZtj7dB5pwsuKky1T.UFBKh6', '22@qq.com', '2023-01-30 16:37:28', '2023-01-30 16:37:28'),
-- 	(14, '李', '白', '$2a$10$w6nNnZjyr4TL5tut94bAxe72OWEuG6XpAvSYT4eMc1Oof1WP5qMxq', '12@qq.com', '2023-02-01 13:18:18', '2023-02-01 13:18:18'),
-- 	(15, '小所1122属', 'hou222', '$2a$10$QhexzK0.12LC33kczr4dh.3emUUjcgtK1t.F3PIhAmZT91hqvx0Km', '11@qq.com', '2023-02-01 13:24:08', '2023-02-01 13:29:58'),
-- 	(17, '111', '222', '$2a$10$.Izmmyqej/7A.h2Bfcit9.EHnhGee.saiDugyq6yTF/TxDTax2J66', '111@qq.com', '2023-02-01 13:55:04', '2023-02-01 14:09:12'),
-- 	(18, '22', '22', '$2a$10$RpRgHwuDY9gd3cNgpYxyquryrWkoB1BJB46AubPTcvoN/r6kmL.qa', '123456@qq.com', '2023-02-01 14:37:36', '2023-02-01 14:40:36'),
-- 	(19, '李', '白', '$2a$10$rM6tlX3LXhQ3FTpN82h.DOO8pmk78qcoQOoKJy2khfcSmuI.gMTVW', '1234566@qq.com', '2023-02-01 15:13:45', '2023-02-01 15:13:45');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;

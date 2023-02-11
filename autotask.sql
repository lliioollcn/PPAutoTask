-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        5.7.26 - MySQL Community Server (GPL)
-- 服务器操作系统:                      Win64
-- HeidiSQL 版本:                  11.0.0.5919
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- 导出  表 autotask.sys_task 结构
CREATE TABLE IF NOT EXISTS `sys_task` (
  `taskType` int(1) NOT NULL AUTO_INCREMENT,
  `taskName` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`taskType`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- 数据导出被取消选择。

-- 导出  表 autotask.user_data 结构
CREATE TABLE IF NOT EXISTS `user_data` (
  `mid` varchar(50) COLLATE utf8_unicode_ci NOT NULL COMMENT '用户唯一id',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `userName` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '用户名',
  `email` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '用户邮箱',
  `emailAuthed` tinyint(1) NOT NULL DEFAULT '0' COMMENT '用户邮箱是否验证',
  `isAdmin` tinyint(1) NOT NULL DEFAULT '0' COMMENT '用户是否为管理员',
  `passWord` varchar(512) COLLATE utf8_unicode_ci NOT NULL DEFAULT '0' COMMENT '用户密码',
  `isBanned` int(1) NOT NULL DEFAULT '0' COMMENT '用户是否被封禁',
  `reason` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '用户封禁原因',
  PRIMARY KEY (`mid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='用户数据表';

-- 数据导出被取消选择。

-- 导出  表 autotask.user_task 结构
CREATE TABLE IF NOT EXISTS `user_task` (
  `id` int(1) NOT NULL AUTO_INCREMENT,
  `mid` varchar(50) COLLATE utf8_unicode_ci NOT NULL DEFAULT '0',
  `taskType` int(1) NOT NULL DEFAULT '0',
  `taskStatus` int(1) NOT NULL DEFAULT '0',
  `lastTime` bigint(20) NOT NULL DEFAULT '0',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `cookie` text COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='用户任务列表';

-- 数据导出被取消选择。

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;

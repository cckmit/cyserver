-- ----------------------------
--  Table structure for `cy_backschool`
-- ----------------------------
DROP TABLE IF EXISTS `cy_backschool`;
CREATE TABLE `cy_backschool` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `topic` varchar(500) DEFAULT NULL  COMMENT '组织主题',
  `number` varchar(11) DEFAULT NULL COMMENT '计划人数',
  `time` date DEFAULT NULL COMMENT '返校时间',
  `classinfo` varchar(255) DEFAULT NULL COMMENT '班级信息',
  `other` varchar(255) DEFAULT NULL COMMENT '描述',
  `userId` varchar(255) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL COMMENT '创建日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;
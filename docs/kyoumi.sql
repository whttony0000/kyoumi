SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for kyoumi_article
-- ----------------------------
DROP TABLE IF EXISTS `kyoumi_article`;
CREATE TABLE `kyoumi_article` (
  `id` int(20) unsigned NOT NULL AUTO_INCREMENT,
  `createTime` datetime DEFAULT CURRENT_TIMESTAMP,
  `updateTime` datetime DEFAULT CURRENT_TIMESTAMP,
  `creatorId` int(20) unsigned NOT NULL DEFAULT '0',
  `memo` varchar(100) DEFAULT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0.无效 1.有效',
  `categoryId` int(20) unsigned NOT NULL DEFAULT '0' COMMENT '组ID',
  `tagId` int(20) unsigned NOT NULL DEFAULT '0' COMMENT '标签ID',
  `title` varchar(255) DEFAULT NULL COMMENT '标题',
  `content` mediumtext COMMENT '内容',
  `likeCnt` int(20) unsigned NOT NULL DEFAULT '0' COMMENT '喜欢数',
  `bookmarkCnt` int(20) unsigned NOT NULL DEFAULT '0' COMMENT '被收藏数',
  `shareCnt` int(20) unsigned NOT NULL DEFAULT '0' COMMENT '分享数',
  `commentCnt` int(20) unsigned NOT NULL DEFAULT '0' COMMENT '评论数',
  `readCnt` int(20) unsigned NOT NULL DEFAULT '0' COMMENT '阅读数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=70 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for kyoumi_article_bookmark
-- ----------------------------
DROP TABLE IF EXISTS `kyoumi_article_bookmark`;
CREATE TABLE `kyoumi_article_bookmark` (
  `id` int(20) unsigned NOT NULL AUTO_INCREMENT,
  `createTime` datetime DEFAULT CURRENT_TIMESTAMP,
  `updateTime` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creatorId` int(20) unsigned NOT NULL DEFAULT '0',
  `memo` varchar(20) DEFAULT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0.无效 1.有效',
  `individualId` int(20) unsigned NOT NULL DEFAULT '0' COMMENT '用户ID',
  `articleId` int(20) unsigned NOT NULL DEFAULT '0' COMMENT '文章ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_iId_aId` (`individualId`,`articleId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for kyoumi_article_like
-- ----------------------------
DROP TABLE IF EXISTS `kyoumi_article_like`;
CREATE TABLE `kyoumi_article_like` (
  `id` int(20) unsigned NOT NULL AUTO_INCREMENT,
  `createTime` datetime DEFAULT CURRENT_TIMESTAMP,
  `updateTime` datetime DEFAULT CURRENT_TIMESTAMP,
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态',
  `individualId` int(20) unsigned NOT NULL DEFAULT '0' COMMENT '单位ID',
  `articleId` int(20) unsigned NOT NULL DEFAULT '0' COMMENT '文章ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_i_a` (`individualId`,`articleId`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for kyoumi_article_score
-- ----------------------------
DROP TABLE IF EXISTS `kyoumi_article_score`;
CREATE TABLE `kyoumi_article_score` (
  `id` int(20) unsigned NOT NULL AUTO_INCREMENT,
  `createTime` datetime DEFAULT CURRENT_TIMESTAMP,
  `updateTime` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `score` tinyint(4) NOT NULL DEFAULT '0' COMMENT '得分',
  `individualId` int(20) unsigned NOT NULL DEFAULT '0' COMMENT '单位ID',
  `articleId` int(20) unsigned NOT NULL DEFAULT '0' COMMENT '文章ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for kyoumi_category
-- ----------------------------
DROP TABLE IF EXISTS `kyoumi_category`;
CREATE TABLE `kyoumi_category` (
  `id` int(20) unsigned NOT NULL AUTO_INCREMENT,
  `createTime` datetime DEFAULT CURRENT_TIMESTAMP,
  `updateTime` datetime DEFAULT CURRENT_TIMESTAMP,
  `creatorId` int(20) unsigned NOT NULL DEFAULT '0',
  `memo` varchar(20) DEFAULT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0.无效 1.有效',
  `name` varchar(20) DEFAULT NULL COMMENT '分类',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `photoKey` varchar(64) DEFAULT NULL COMMENT '头像',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for kyoumi_comment
-- ----------------------------
DROP TABLE IF EXISTS `kyoumi_comment`;
CREATE TABLE `kyoumi_comment` (
  `id` int(20) unsigned NOT NULL AUTO_INCREMENT,
  `createTime` datetime DEFAULT CURRENT_TIMESTAMP,
  `updateTime` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creatorId` int(20) unsigned NOT NULL DEFAULT '0',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0.无效 1.有效',
  `content` varchar(2000) DEFAULT NULL COMMENT '评论内容',
  `articleId` int(20) unsigned NOT NULL DEFAULT '0' COMMENT '文章ID',
  `floor` int(20) unsigned NOT NULL DEFAULT '0' COMMENT '楼层',
  `likeCnt` int(20) unsigned NOT NULL DEFAULT '0' COMMENT '赞数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for kyoumi_individual
-- ----------------------------
DROP TABLE IF EXISTS `kyoumi_individual`;
CREATE TABLE `kyoumi_individual` (
  `id` int(20) unsigned NOT NULL AUTO_INCREMENT,
  `createTime` datetime DEFAULT CURRENT_TIMESTAMP,
  `updateTime` datetime DEFAULT CURRENT_TIMESTAMP,
  `creatorId` int(20) unsigned NOT NULL DEFAULT '0',
  `memo` varchar(20) DEFAULT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0.无效 1.有效 2.锁定',
  `name` varchar(100) DEFAULT NULL COMMENT '名字',
  `birthday` varchar(10) DEFAULT NULL COMMENT '生日',
  `mail` varchar(20) DEFAULT NULL COMMENT '邮箱',
  `passwd` varchar(128) DEFAULT NULL COMMENT '密码',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `level` int(20) unsigned NOT NULL DEFAULT '0',
  `score` int(20) NOT NULL DEFAULT '0' COMMENT '得分',
  `mailMd5Hash` varchar(64) DEFAULT NULL COMMENT 'mailMd5Hash',
  `photoKey` varchar(64) DEFAULT NULL COMMENT '头像',
  `sex` tinyint(4) NOT NULL DEFAULT '0' COMMENT '性别',
  `role` int(20) unsigned NOT NULL DEFAULT '0' COMMENT '角色 1.管理员 2.注册用户 3.待激活用户',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for kyoumi_individual_log
-- ----------------------------
DROP TABLE IF EXISTS `kyoumi_individual_log`;
CREATE TABLE `kyoumi_individual_log` (
  `id` int(20) unsigned NOT NULL AUTO_INCREMENT,
  `createTime` datetime DEFAULT CURRENT_TIMESTAMP,
  `updateTime` datetime DEFAULT CURRENT_TIMESTAMP,
  `creatorId` int(20) unsigned NOT NULL DEFAULT '0',
  `memo` varchar(20) DEFAULT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0.无效 1.有效',
  `type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '1.创建 2.发送验证邮件 3.验证成功',
  `individualId` int(20) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for kyoumi_mid_individual_article
-- ----------------------------
DROP TABLE IF EXISTS `kyoumi_mid_individual_article`;
CREATE TABLE `kyoumi_mid_individual_article` (
  `id` int(20) unsigned NOT NULL AUTO_INCREMENT,
  `createTime` datetime DEFAULT CURRENT_TIMESTAMP,
  `updateTime` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creatorId` int(20) unsigned NOT NULL DEFAULT '0',
  `memo` varchar(20) DEFAULT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0.无效 1.有效',
  `individualId` int(20) unsigned NOT NULL DEFAULT '0' COMMENT '用户ID',
  `articleId` int(20) unsigned NOT NULL DEFAULT '0' COMMENT '文章ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_iId_aId` (`individualId`,`articleId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for kyoumi_mid_individual_category
-- ----------------------------
DROP TABLE IF EXISTS `kyoumi_mid_individual_category`;
CREATE TABLE `kyoumi_mid_individual_category` (
  `id` int(20) unsigned NOT NULL AUTO_INCREMENT,
  `createTime` datetime DEFAULT CURRENT_TIMESTAMP,
  `updateTime` datetime DEFAULT CURRENT_TIMESTAMP,
  `creatorId` int(20) unsigned NOT NULL DEFAULT '0',
  `memo` varchar(20) DEFAULT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0.无效 1.有效',
  `individualId` int(20) unsigned NOT NULL DEFAULT '0' COMMENT '用户ID',
  `categoryId` int(20) unsigned NOT NULL DEFAULT '0' COMMENT '分类ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_iId_cId` (`individualId`,`categoryId`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for kyoumi_mid_individual_individual
-- ----------------------------
DROP TABLE IF EXISTS `kyoumi_mid_individual_individual`;
CREATE TABLE `kyoumi_mid_individual_individual` (
  `id` int(20) unsigned NOT NULL AUTO_INCREMENT,
  `createTime` datetime DEFAULT CURRENT_TIMESTAMP,
  `updateTime` datetime DEFAULT CURRENT_TIMESTAMP,
  `creatorId` int(20) unsigned NOT NULL DEFAULT '0',
  `memo` varchar(20) DEFAULT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0.无效 1.有效',
  `watcherId` int(20) unsigned NOT NULL DEFAULT '0' COMMENT '用户ID',
  `watchedId` int(20) unsigned NOT NULL DEFAULT '0' COMMENT '用户ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_iId_cId` (`watcherId`,`watchedId`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for kyoumi_mid_tag_category
-- ----------------------------
DROP TABLE IF EXISTS `kyoumi_mid_tag_category`;
CREATE TABLE `kyoumi_mid_tag_category` (
  `id` int(20) unsigned NOT NULL AUTO_INCREMENT,
  `createTime` datetime DEFAULT CURRENT_TIMESTAMP,
  `updateTime` datetime DEFAULT CURRENT_TIMESTAMP,
  `creatorId` int(20) unsigned NOT NULL DEFAULT '0',
  `memo` varchar(20) DEFAULT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0.无效 1.有效',
  `tagId` int(20) unsigned NOT NULL DEFAULT '0' COMMENT '标签ID',
  `categoryId` int(20) unsigned NOT NULL DEFAULT '0' COMMENT '分类ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for kyoumi_notice
-- ----------------------------
DROP TABLE IF EXISTS `kyoumi_notice`;
CREATE TABLE `kyoumi_notice` (
  `id` int(20) unsigned NOT NULL AUTO_INCREMENT,
  `createTime` datetime DEFAULT CURRENT_TIMESTAMP,
  `updateTime` datetime DEFAULT CURRENT_TIMESTAMP,
  `creatorId` int(20) unsigned NOT NULL DEFAULT '0',
  `memo` varchar(100) DEFAULT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0.无效 1.有效',
  `individualId` int(20) unsigned NOT NULL DEFAULT '0' COMMENT '个体ID',
  `involvedId` int(20) unsigned NOT NULL DEFAULT '0' COMMENT '参与者ID',
  `type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '通知类型 1.关注者新建文章 2.本人文章收到回复 3.回复收到回复 4.有新关注者',
  `content` varchar(256) NOT NULL DEFAULT '' COMMENT '通知内容',
  `objectId` int(20) unsigned NOT NULL DEFAULT '0' COMMENT '对象ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for kyoumi_photo
-- ----------------------------
DROP TABLE IF EXISTS `kyoumi_photo`;
CREATE TABLE `kyoumi_photo` (
  `id` int(20) unsigned NOT NULL AUTO_INCREMENT,
  `createTime` datetime DEFAULT CURRENT_TIMESTAMP,
  `updateTime` datetime DEFAULT CURRENT_TIMESTAMP,
  `creatorId` int(20) unsigned NOT NULL DEFAULT '0',
  `memo` varchar(20) DEFAULT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0.无效 1.有效',
  `type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '图片类型 1.分类 2.标签 3.个人',
  `objectId` int(20) unsigned NOT NULL DEFAULT '0' COMMENT '图片属性ID',
  `imageKey` varchar(50) DEFAULT NULL COMMENT '图片key',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for kyoumi_role_perm
-- ----------------------------
DROP TABLE IF EXISTS `kyoumi_role_perm`;
CREATE TABLE `kyoumi_role_perm` (
  `id` int(20) unsigned NOT NULL AUTO_INCREMENT,
  `role` int(20) unsigned NOT NULL DEFAULT '0' COMMENT '角色',
  `perm` varchar(500) DEFAULT NULL COMMENT '权限',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for kyoumi_sub_comment
-- ----------------------------
DROP TABLE IF EXISTS `kyoumi_sub_comment`;
CREATE TABLE `kyoumi_sub_comment` (
  `id` int(20) unsigned NOT NULL AUTO_INCREMENT,
  `createTime` datetime DEFAULT CURRENT_TIMESTAMP,
  `updateTime` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creatorId` int(20) unsigned NOT NULL DEFAULT '0',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0.无效 1.有效',
  `content` varchar(2000) DEFAULT NULL COMMENT '评论内容',
  `commentId` int(20) unsigned NOT NULL DEFAULT '0' COMMENT '父评论ID',
  `targetIndividualId` int(20) NOT NULL DEFAULT '0' COMMENT '目标人ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for kyoumi_tag
-- ----------------------------
DROP TABLE IF EXISTS `kyoumi_tag`;
CREATE TABLE `kyoumi_tag` (
  `id` int(20) unsigned NOT NULL AUTO_INCREMENT,
  `createTime` datetime DEFAULT CURRENT_TIMESTAMP,
  `updateTime` datetime DEFAULT CURRENT_TIMESTAMP,
  `creatorId` int(20) unsigned NOT NULL DEFAULT '0',
  `memo` varchar(20) DEFAULT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0.无效 1.有效',
  `name` varchar(20) DEFAULT NULL COMMENT '标签名',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `categoryId` int(20) unsigned NOT NULL COMMENT '分类ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

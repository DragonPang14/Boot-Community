## 现有功能
- 注册，登陆，修改头像
- 支持github的OAuth登陆（请看官方使用文档[这里](https://docs.github.com/en/developers/apps/authorizing-oauth-apps)）
- 发布帖子，以普通文字形式或markdown形式
- 回复帖子，二级回复，多级回复未完成
- 在线人数显示
- 一天热门文章，根据当天浏览量显示
- 回复消息通知

后续有空更新

## 建表脚本
```

-- ----------------------------
-- Table structure for `comment`
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `question_id` bigint DEFAULT NULL,
  `parent_id` bigint NOT NULL,
  `content` varchar(1024) DEFAULT NULL,
  `type` int DEFAULT NULL,
  `creator` bigint DEFAULT NULL,
  `like_count` bigint DEFAULT NULL,
  `gmt_create` bigint DEFAULT NULL,
  `gmt_modified` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for `notification`
-- ----------------------------
DROP TABLE IF EXISTS `notification`;
CREATE TABLE `notification` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `target_id` bigint DEFAULT NULL,
  `target_type` int DEFAULT NULL,
  `sender_id` bigint DEFAULT NULL,
  `receive_id` bigint DEFAULT NULL,
  `noti_content` blob,
  `action` int DEFAULT NULL,
  `status` int DEFAULT '0',
  `gmt_create` bigint DEFAULT NULL,
  `del_flag` int DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- ----------------------------
-- Table structure for `question`
-- ----------------------------
DROP TABLE IF EXISTS `question`;
CREATE TABLE `question` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(100) DEFAULT NULL,
  `description` text,
  `gmt_create` bigint DEFAULT NULL,
  `gmt_modified` bigint DEFAULT NULL,
  `creator` int DEFAULT NULL,
  `comment_count` int DEFAULT '0',
  `view_count` int DEFAULT '0',
  `like_count` int DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for `question_tags`
-- ----------------------------
DROP TABLE IF EXISTS `question_tags`;
CREATE TABLE `question_tags` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `question_id` bigint DEFAULT NULL,
  `tag_id` bigint DEFAULT NULL,
  `gmt_create` bigint DEFAULT NULL,
  `del_flag` int DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for `tag`
-- ----------------------------
DROP TABLE IF EXISTS `tag`;
CREATE TABLE `tag` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `tag_name` varchar(100) DEFAULT NULL,
  `remarks` varchar(100) DEFAULT NULL,
  `gmt_create` bigint DEFAULT NULL,
  `gmt_modify` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `account_id` varchar(50) DEFAULT NULL,
  `token` char(36) DEFAULT NULL,
  `gmt_create` bigint DEFAULT NULL,
  `gmt_modified` bigint DEFAULT NULL,
  `avatar_url` varchar(100) DEFAULT NULL,
  `user_name` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `mobile` char(15) DEFAULT NULL,
  `bio` text,
  `mail` varchar(50) DEFAULT NULL,
  `del_flag` int DEFAULT '0',
  `user_type` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


```
create database `victoricare`;
use victoricare;

DROP TABLE IF EXISTS `person`;
CREATE TABLE IF NOT EXISTS `person` (
  `id` int(11) UNSIGNED ZEROFILL AUTO_INCREMENT PRIMARY KEY,
  `lastname` varchar(40) DEFAULT NULL,
  `firstname` varchar(40) DEFAULT NULL,
  `gender` varchar(15),
  `birth_at` datetime DEFAULT NULL,
  `birth_place` char(2) DEFAULT NULL,
  `tel` varchar(20) DEFAULT NULL,
  `create_at` datetime DEFAULT NULL,
  `create_by` bigint(20) UNSIGNED DEFAULT NULL,
  `delete_by` bigint(20) UNSIGNED DEFAULT NULL,
  `delete_at` datetime DEFAULT NULL,
  `role` varchar(10) NOT NULL,
  `country` char(2) DEFAULT NULL,
  `country_name` varchar(50) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `account` int(11) UNSIGNED DEFAULT NULL,
  KEY `person_ibfk_1` (`create_by`),
  KEY `person_ibfk_2` (`delete_by`),
  KEY `person_ibfk_3` (`account`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
   `id` int(11) UNSIGNED ZEROFILL PRIMARY KEY,
   `email` varchar(100) DEFAULT NULL,
   `password` varchar(250) DEFAULT NULL,
   `username` varchar(50) DEFAULT NULL,
   `ip` varchar(255) DEFAULT NULL,
   `browser` varchar(255) DEFAULT NULL,
   `os` varchar(255) DEFAULT NULL,
   `recovery_at` datetime DEFAULT NULL,
   `recovery` int(11) UNSIGNED DEFAULT NULL,
   `validation_at` datetime DEFAULT NULL,
   `lang` char(2) DEFAULT 'fr',
   `profil_at` datetime DEFAULT NULL,
    KEY `user_ibfk_1` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `account`;
CREATE TABLE IF NOT EXISTS `account` (
  `id` int(11) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `numero` varchar(255) NOT NULL,
  `create_at` datetime NOT NULL,
  `delete_at` datetime DEFAULT NULL,
  `status` char (1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `request`;
CREATE TABLE IF NOT EXISTS `request` (
  `id` int(11) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `create_at` datetime NOT NULL,
  `account` int(11) UNSIGNED NOT NULL,
  `create_by` bigint(20) UNSIGNED NOT NULL,
  `delete_at` datetime DEFAULT NULL,
  `accepted_by` bigint(20) UNSIGNED DEFAULT NULL,
  `reason` varchar(255),
  KEY `request_ibfk_1` (`account`),
  KEY `request_ibfk_2` (`create_by`),
  KEY `request_ibfk_3` (`accepted_by`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `code`;
CREATE TABLE IF NOT EXISTS `code` (
  `id` int(11) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `create_by` bigint(20) UNSIGNED NOT NULL,
  `babysitter` int(11) UNSIGNED NOT NULL,
  `create_at` datetime NOT NULL,
  `cancel_at` datetime DEFAULT NULL,
  `cancel_by` bigint(20) UNSIGNED DEFAULT null,
  `text` char(6) NOT NULL,
  `end_at` datetime NOT NULL, 
  KEY `code_ibfk_1` (`create_by`),
  KEY `code_ibfk_2` (`cancel_by`),
  KEY `code_ibfk_3` (`babysitter`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `babycode`;
CREATE TABLE IF NOT EXISTS `babycode` (
  `code` int(11) UNSIGNED NOT NULL,
  `baby` int(11) UNSIGNED NOT NULL,
  `create_by` bigint(20) UNSIGNED NOT NULL,
  `create_at` datetime NOT NULL,
  `cancel_at` datetime DEFAULT NULL,
  `cancel_by` bigint(20) UNSIGNED DEFAULT null,
  `text` char(6) NOT NULL,
  PRIMARY key(`code`,  `baby`),
  KEY `babycode_ibfk_1` (`code`),
  KEY `babycode_ibfk_2` (`create_by`),
  KEY `babycode_ibfk_3` (`cancel_by`),
  KEY `babycode_ibfk_4` (`baby`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `care`;
CREATE TABLE IF NOT EXISTS `care` (
  `id` int(11) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `create_by` bigint(20) UNSIGNED DEFAULT NULL,
  `create_at` datetime NOT NULL,
  `delete_at` datetime DEFAULT NULL,
  `delete_by` bigint(20) UNSIGNED DEFAULT null,
  `title` varchar(255) NOT NULL,
  `description` text DEFAULT NULL,
  `lang` char(2) DEFAULT 'fr', 
  KEY `care_ibfk_1` (`create_by`),
  KEY `care_ibfk_2` (`delete_by`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `name`;
CREATE TABLE IF NOT EXISTS `name` (
  `id` int(11) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `create_by` bigint(20) UNSIGNED DEFAULT NULL,
  `create_at` datetime NOT NULL,
  `delete_at` datetime DEFAULT NULL,
  `delete_by` bigint(20) UNSIGNED DEFAULT null,
  `name` varchar(255) NOT NULL,
  `definition` text DEFAULT NULL,
  `lang` char(2) DEFAULT 'fr', 
  KEY `name_ibfk_1` (`create_by`),
  KEY `name_ibfk_2` (`delete_by`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `babycare`;
CREATE TABLE IF NOT EXISTS `babycare` (
  `id` bigint(20) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `create_at` datetime NOT NULL,
  `cancel_at` datetime DEFAULT NULL,
  `cancel_by` bigint(20) UNSIGNED DEFAULT null,
  `code` int(11) UNSIGNED DEFAULT NULL,
  `baby` int(11) UNSIGNED NOT NULL,
  `care` int(11) UNSIGNED NOT NULL,
  `create_by` bigint(20) UNSIGNED NOT null,
  `end_at` datetime DEFAULT NULL,
  `end_by` bigint(20) UNSIGNED DEFAULT null,
  KEY `babycare_ibfk_1` (`baby`),
  KEY `babycare_ibfk_2` (`create_by`),
  KEY `babycare_ibfk_3` (`end_by`),
  KEY `babycare_ibfk_4` (`code`),
  KEY `babycare_ibfk_5` (`cancel_by`),
  KEY `babycare_ibfk_6` (`care`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `contact`;
CREATE TABLE IF NOT EXISTS `contact` (
  `id` int(11) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT,
  `create_at` datetime NOT NULL,
  `email` varchar(50) NOT NULL,
  `subject` smallint(1) NOT NULL,
  `username` varchar(255) NOT NULL,
  `text` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `connection`;
CREATE TABLE IF NOT EXISTS `connection` (
  `id` bigint(20) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `user` int(11) UNSIGNED DEFAULT NULL,
  `code` int(11) UNSIGNED DEFAULT NULL, 
  `create_at` datetime NOT NULL,
  `end_at` datetime DEFAULT NULL,
  `role` smallint(5) DEFAULT NULL,
  `ip` varchar(50) DEFAULT NULL,
  `browser` varchar(150) DEFAULT NULL,
  `os` varchar(255) DEFAULT NULL,
  KEY `connection_ibfk_1` (`user`),
  KEY `connection_ibfk_2` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `event`;
CREATE TABLE IF NOT EXISTS `event` (
  `id` int(11) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `create_by` bigint(20) UNSIGNED DEFAULT NULL,
  `cancel_by` bigint(20) UNSIGNED DEFAULT NULL,
  `update_by` bigint(20) UNSIGNED DEFAULT NULL,
  `create_at` datetime NOT NULL,
  `update_at` datetime DEFAULT NULL,
  `start_at` datetime DEFAULT NULL,
  `cancel_at` datetime DEFAULT NULL,
  `end_at` datetime DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `description` text DEFAULT NULL,
  `type` tinyint(2) UNSIGNED NOT NULL,
  KEY `event_ibfk_1` (`create_by`),
  KEY `event_ibfk_2` (`cancel_by`),
  KEY `event_ibfk_3` (`update_by`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `invitation`;
CREATE TABLE IF NOT EXISTS `invitation` (
  `id` int(11) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `event` int(11) UNSIGNED NOT NULL,
  `create_by` bigint(20) UNSIGNED DEFAULT NULL,
  `cancel_by` bigint(20) UNSIGNED DEFAULT NULL,
  `create_at` datetime NOT NULL,
  `cancel_at` datetime DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `username` text DEFAULT NULL,
  `code` varchar(255) NOT NULL,
  `status` char(1) DEFAULT NULL,
  KEY `invitation_ibfk_1` (`event`),
  KEY `invitation_ibfk_2` (`create_by`),
  KEY `invitation_ibfk_3` (`cancel_by`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `suggestion`;
CREATE TABLE IF NOT EXISTS `suggestion` (
  `id` bigint(20) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `invitation` int(11) UNSIGNED NOT NULL,
  `create_at` datetime NOT NULL,
  `delete_at` datetime DEFAULT NULL,
  `accepted_by` bigint(20) UNSIGNED DEFAULT null,
  `accepted_at` datetime DEFAULT NULL,
  KEY `suggestion_ibfk_1` (`invitation`),
  KEY `suggestion_ibfk_2` (`accepted_by`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `toss`;
CREATE TABLE IF NOT EXISTS `toss` (
  `id` int(11) UNSIGNED NOT NULL,
  `duration` smallint(5) UNSIGNED,
  `result` varchar(255) DEFAULT NULL,
  `history` text DEFAULT NULL,
  `views` int(11) UNSIGNED DEFAULT NULL,
  `code` varchar(255),
  KEY `toss_ibfk_1` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `notification`;
CREATE TABLE IF NOT EXISTS `notification` (
  `id` bigint(20) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `first_sight` int(11) UNSIGNED DEFAULT NULL,
  `second_sight` int(11) UNSIGNED DEFAULT NULL,
  `first_sight_at` datetime DEFAULT NULL,
  `second_sight_at` datetime DEFAULT NULL,
  `create_at` datetime NOT NULL,
  `link` varchar(255) NOT NULL,
  `varchar` varchar(255) NOT NULL,
  `params` varchar(255) DEFAULT NULL,
  KEY `notification_ibfk_1` (`first_sight`),
  KEY `notification_ibfk_2` (`second_sight`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;


ALTER TABLE `person`
  ADD CONSTRAINT `person_ibfk_1` FOREIGN KEY (`create_by`) REFERENCES `connection` (`id`),
  ADD CONSTRAINT `person_ibfk_2` FOREIGN KEY (`delete_by`) REFERENCES `connection` (`id`),
  ADD CONSTRAINT `person_ibfk_3` FOREIGN KEY (`account`) REFERENCES `account` (`id`);

--
-- Contraintes pour la table `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `user_ibfk_1` FOREIGN KEY (`id`) REFERENCES `person` (`id`);

--
-- Contraintes pour la table `person`
--
ALTER TABLE `request`
  ADD CONSTRAINT `request_ibfk_1` FOREIGN KEY (`account`) REFERENCES `account` (`id`),
  ADD CONSTRAINT `request_ibfk_2` FOREIGN KEY (`create_by`) REFERENCES `connection` (`id`),
  ADD CONSTRAINT `request_ibfk_3` FOREIGN KEY (`accepted_by`) REFERENCES `connection` (`id`);

  --
-- Contraintes pour la table `code`
--
ALTER TABLE `code`
  ADD CONSTRAINT `code_ibfk_1` FOREIGN KEY (`create_by`) REFERENCES `connection` (`id`),
  ADD CONSTRAINT `code_ibfk_2` FOREIGN KEY (`cancel_by`) REFERENCES `connection` (`id`),
  ADD CONSTRAINT `code_ibfk_3` FOREIGN KEY (`babysitter`) REFERENCES `user` (`id`);

    --
-- Contraintes pour la table `babycode`
--
ALTER TABLE `babycode`
  ADD CONSTRAINT `babycode_ibfk_1` FOREIGN KEY (`code`) REFERENCES `code` (`id`),
  ADD CONSTRAINT `babycode_ibfk_2` FOREIGN KEY (`create_by`) REFERENCES `connection` (`id`),
  ADD CONSTRAINT `babycode_ibfk_3` FOREIGN KEY (`cancel_by`) REFERENCES `connection` (`id`),
  ADD CONSTRAINT `babycode_ibfk_4` FOREIGN KEY (`baby`) REFERENCES `person` (`id`);
    --
-- Contraintes pour la table `connection`
--
ALTER TABLE `connection`
  ADD CONSTRAINT `connection_ibfk_1` FOREIGN KEY (`user`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `connection_ibfk_2` FOREIGN KEY (`code`) REFERENCES `code` (`id`);

  ALTER TABLE `care`
  ADD CONSTRAINT `care_ibfk_1` FOREIGN KEY (`create_by`) REFERENCES `connection` (`id`),
  ADD CONSTRAINT `care_ibfk_2` FOREIGN KEY (`delete_by`) REFERENCES `connection` (`id`);

ALTER TABLE `name`
  ADD CONSTRAINT `name_ibfk_1` FOREIGN KEY (`create_by`) REFERENCES `connection` (`id`),
  ADD CONSTRAINT `name_ibfk_2` FOREIGN KEY (`delete_by`) REFERENCES `connection` (`id`);


ALTER TABLE `babycare`
  ADD CONSTRAINT `babycare_ibfk_1` FOREIGN KEY (`baby`) REFERENCES `person` (`id`),
  ADD CONSTRAINT `babycare_ibfk_2` FOREIGN KEY (`create_by`) REFERENCES `connection` (`id`),
  ADD CONSTRAINT `babycare_ibfk_3` FOREIGN KEY (`end_by`) REFERENCES `connection` (`id`),
  ADD CONSTRAINT `babycare_ibfk_4` FOREIGN KEY (`code`) REFERENCES `code` (`id`),
  ADD CONSTRAINT `babycare_ibfk_5` FOREIGN KEY (`cancel_by`) REFERENCES `connection` (`id`),
  ADD CONSTRAINT `babycare_ibfk_6` FOREIGN KEY (`care`) REFERENCES `care` (`id`);


ALTER TABLE `event`
  ADD CONSTRAINT `event_ibfk_1` FOREIGN KEY (`create_by`) REFERENCES `connection` (`id`),
  ADD CONSTRAINT `event_ibfk_2` FOREIGN KEY (`cancel_by`) REFERENCES `connection` (`id`),
  ADD CONSTRAINT `event_ibfk_3` FOREIGN KEY (`update_by`) REFERENCES `connection` (`id`);


ALTER TABLE `invitation`
  ADD CONSTRAINT `invitation_ibfk_1` FOREIGN KEY (`event`) REFERENCES `event` (`id`),
  ADD CONSTRAINT `invitation_ibfk_2` FOREIGN KEY (`create_by`) REFERENCES `connection` (`id`),
  ADD CONSTRAINT `invitation_ibfk_3` FOREIGN KEY (`cancel_by`) REFERENCES `connection` (`id`);

ALTER TABLE `suggestion`
  ADD CONSTRAINT `suggestion_ibfk_1` FOREIGN KEY (`invitation`) REFERENCES `invitation` (`id`),
  ADD CONSTRAINT `suggestion_ibfk_2` FOREIGN KEY (`accepted_by`) REFERENCES `connection` (`id`);

ALTER TABLE `toss`
  ADD CONSTRAINT `toss_ibfk_1` FOREIGN KEY (`id`) REFERENCES `event` (`id`);

--
-- Contraintes pour la table `notification`
--
ALTER TABLE `notification`
  ADD CONSTRAINT `notification_ibfk_1` FOREIGN KEY (`first_sight`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `notification_ibfk_2` FOREIGN KEY (`second_sight`) REFERENCES `user` (`id`);
COMMIT;
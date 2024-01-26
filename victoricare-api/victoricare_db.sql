CREATE database victoricare_db;
use victoricare_db;

CREATE TABLE `person` (
  `person_id` int(11) UNSIGNED ZEROFILL AUTO_INCREMENT PRIMARY KEY,
  `person_lastname` varchar(25) DEFAULT NULL,
  `person_firstname` varchar(25) DEFAULT NULL,
  `person_gender` varchar(2) DEFAULT NULL,
  `person_birthday` datetime DEFAULT NULL,
  `person_phone` varchar(20) DEFAULT NULL,
  `person_creation_date` datetime NOT NULL,
  `person_update_date` datetime DEFAULT NULL,
  `person_deletion_date` datetime DEFAULT NULL,
  `person_image` varchar(50) DEFAULT NULL,
  `person_roles` varchar(100),
  `person_country` varchar(25) DEFAULT NULL,
  `person_creation_author` int(11) UNSIGNED DEFAULT null,
  `person_update_author` int(11) UNSIGNED DEFAULT null,
  `person_deletion_author` int(11) UNSIGNED DEFAULT null,
  `person_address` int(11) UNSIGNED DEFAULT NULL,
  `person_unique_identifier` varchar(8) UNIQUE NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `child` (
  `child_id` int(11) UNSIGNED PRIMARY KEY,
  `child_family` int(11) UNSIGNED NOT NULL,
  `child_attachment_family` int(11) UNSIGNED DEFAULT NULL,
  `child_attachment_date` datetime DEFAULT NULL,
  `child_detachment_date` datetime DEFAULT NULL,
  `child_attachment_author` int(11) UNSIGNED DEFAULT NULL,
  `child_detachment_author` int(11) UNSIGNED DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `user` (
  `user_id` int(11) UNSIGNED  PRIMARY KEY,
  `user_pseudo` varchar(25) DEFAULT NULL,
  `user_email` varchar(25) NOT NULL,
  `user_pass` varchar(255),
  `user_description` int(11) UNSIGNED DEFAULT NULL,
  `user_pass_date` datetime NOT NULL,
  `user_rights` varchar(100),
  `user_account_creation_date` datetime NOT NULL,
  `user_account_update_date` datetime DEFAULT NULL,
  `user_account_deletion_date` datetime DEFAULT NULL,
  `user_banishment_date` datetime DEFAULT NULL,
  `user_banishment_author`int(11) UNSIGNED DEFAULT null,
  `user_account_deletion_author`int(11) UNSIGNED DEFAULT null,
  `user_sign_up`int(11) UNSIGNED DEFAULT null,
  `user_profiles` varchar(30) DEFAULT NULL,
  `user_admin_code`int(4) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `family` (
  `family_id` int(11) UNSIGNED ZEROFILL AUTO_INCREMENT PRIMARY KEY,
  `family_creation_date` datetime NOT NULL,
  `family_update_date` datetime DEFAULT NULL,
  `family_deletion_date` datetime DEFAULT NULL,
  `family_creation_author` int(11) UNSIGNED DEFAULT null,
  `family_parent_one` int(11) UNSIGNED DEFAULT null,
  `family_parent_two` int(11) UNSIGNED DEFAULT null,
  `family_baby_sitter` bigint(20) UNSIGNED DEFAULT null,
  `family_update_author` int(11) UNSIGNED DEFAULT null,
  `family_deletion_author` int(11) UNSIGNED DEFAULT null
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `baby_sitter` (
  `baby_sitter_id` bigint(20) UNSIGNED ZEROFILL AUTO_INCREMENT PRIMARY key,
  `baby_sitter_code` varchar(8) NOT NULL,
  `baby_sitter_firstname` varchar(50) NOT NULL,
  `baby_sitter_lastname` varchar(50) NOT NULL,
  `baby_sitter_creation_date` datetime NOT NULL,
  `baby_sitter_deletion_date` datetime DEFAULT NULL,
  `baby_sitter_update_date` datetime DEFAULT NULL,
  `baby_sitter_user` int(11) UNSIGNED DEFAULT null,
  `baby_sitter_family` int(11) UNSIGNED NOT null,
  `baby_sitter_creation_author` int(11) UNSIGNED DEFAULT null,
  `baby_sitter_update_author` int(11) UNSIGNED DEFAULT null,
  `baby_sitter_deletion_author` int(11) UNSIGNED DEFAULT null
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `address` (
  `address_id` int(11) UNSIGNED ZEROFILL AUTO_INCREMENT PRIMARY KEY,
  `address_street` varchar(100) NOT NULL,
  `address_additionnal` varchar(255) DEFAULT NULL,
  `address_zip_code` varchar(25) NOT NULL,
  `address_town` varchar(50) NOT NULL,
  `address_country` varchar(25) NOT NULL,
  `address_creation_date` datetime NOT NULL,
  `address_update_date` datetime DEFAULT NULL,
  `address_deletion_date` datetime DEFAULT NULL,
  `address_creation_author` int(11) UNSIGNED DEFAULT null,
  `address_update_author` int(11) UNSIGNED DEFAULT null,
  `address_deletion_author` int(11) UNSIGNED DEFAULT null
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `child_care` (
  `child_care_id` bigint(20) UNSIGNED ZEROFILL AUTO_INCREMENT PRIMARY KEY,
  `child_care_creation_date` datetime NOT NULL,
  `child_care_start_date` datetime NOT NULL,
  `child_care_end_date` datetime NOT NULL,
  `child_care_update_date` datetime DEFAULT NULL,
  `child_care_deletion_date` datetime DEFAULT NULL,
  `child_care_creation_author` int(11) UNSIGNED NOT null,
  `child_care_creation_family` int(11) UNSIGNED NOT null,
  `child_care_update_author` int(11) UNSIGNED DEFAULT NULL,
  `child_care_deletion_author` int(11) UNSIGNED DEFAULT null,
  `child_care_baby_sitter` bigint(20) UNSIGNED DEFAULT null,
  `child_care_responsible` int(11) UNSIGNED DEFAULT null
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `child_care_task` (
  `child_care_task_id` bigint(20) UNSIGNED ZEROFILL AUTO_INCREMENT PRIMARY KEY,
  `child_care_task_child_care` bigint(20) UNSIGNED NOT NULL,
  `child_care_task_child` int(11) UNSIGNED NOT null,
  `child_care_task_creation_date` datetime NOT NULL,
  `child_care_task_update_date` datetime DEFAULT NULL,
  `child_care_task_deletion_date` datetime DEFAULT NULL,
  `child_care_task_task_name` varchar(255) NOT NULL,
  `child_care_task_task_description` varchar(500) DEFAULT NULL,
  `child_care_task_creation_author` int(11) UNSIGNED NOT null,
  `child_care_task_update_author` int(11) UNSIGNED DEFAULT null,
  `child_care_task_deletion_author` int(11) UNSIGNED DEFAULT null,
  `child_care_task_start_date` datetime NOT NULL,
  `child_care_task_end_date` datetime NOT NULL,
  `child_care_task_starting_author` int(11) UNSIGNED DEFAULT null,
  `child_care_task_ending_author` int(11) UNSIGNED DEFAULT null
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `task` (
  `task_id` bigint(20) UNSIGNED ZEROFILL AUTO_INCREMENT PRIMARY KEY,
  `task_name` varchar(255) NOT NULL,
  `task_description` varchar(500) DEFAULT NULL,
  `task_creation_date` datetime NOT NULL,
  `task_update_date` datetime DEFAULT NULL,
  `task_deletion_date` datetime DEFAULT NULL,
  `task_creation_author` int(11) UNSIGNED NOT null,
  `task_deletion_author` int(11) UNSIGNED DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `banishment` (
  `banishment_id` int(11) UNSIGNED ZEROFILL AUTO_INCREMENT PRIMARY KEY,
  `banishment_creation_date` datetime NOT NULL,
  `banishment_ip` varchar(50) NOT NULL,
  `banishment_browser` varchar(100) DEFAULT null,
  `banishment_deletion_author` int(11) DEFAULT NULL,
  `banishment_deletion_date` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `contact` (
  `contact_id` int(11) UNSIGNED ZEROFILL PRIMARY KEY,
  `contact_creation_date` datetime NOT NULL,
  `contact_email` varchar(25) NOT NULL,
  `contact_subject` varchar(50) NOT NULL,
  `contact_name` varchar(50) NOT NULL,
  `contact_text` varchar(1000) NOT NULL,
  `contact_response` varchar(1000) DEFAULT NULL,
  `contact_response_date` datetime DEFAULT NULL,
  `contact_response_author` int(11) UNSIGNED DEFAULT NULL,
  `contact_status` varchar(10),
  `contact_browser` varchar(100) NOT NULL,
  `contact_ip` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `notification` (
  `notification_id` bigint(20) UNSIGNED ZEROFILL AUTO_INCREMENT PRIMARY KEY,
  `notification_destination` bigint(20) UNSIGNED NOT NULL,
  `notification_type` varchar(10) NOT NULL,
  `notification_title` varchar(50) DEFAULT NULL,
  `notification_creation_date` datetime NOT NULL,
  `notification_reading_date` datetime DEFAULT NULL,
  `notification_content` varchar(500) NOT NULL,
  `notification_view_date` datetime DEFAULT NULL,
  `notification_deletion_date` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `log_in` (
  `log_in_id` bigint(20) UNSIGNED ZEROFILL AUTO_INCREMENT PRIMARY KEY,
  `log_in_browser` varchar(100) NOT NULL,
  `log_in_ip` varchar(50) NOT NULL,
  `log_in_creation_date` datetime NOT NULL,
  `log_in_expiration_date` datetime NOT NULL,
  `log_in_update_date` datetime DEFAULT NULL,
  `log_in_deletion_date` datetime DEFAULT NULL,
  `log_in_email` varchar(50) DEFAULT NULL,
  `log_in_pseudo` varchar(50) DEFAULT NULL,
  `log_in_token` varchar(255) NOT NULL,
  `log_in_roles` varchar(255) NOT NULL,
  `log_in_rights` varchar(255) NOT NULL,
  `log_in_creation_author` int(11) UNSIGNED DEFAULT NULL,
  `log_in_update_author` int(11) UNSIGNED DEFAULT NULL,
  `log_in_deletion_author` int(11) UNSIGNED DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `recovery` (
  `recovery_id` int(11) UNSIGNED ZEROFILL AUTO_INCREMENT PRIMARY KEY,
  `recovery_creation_date` datetime NOT NULL,
  `recovery_creation_author` int(11) UNSIGNED NOT NULL,
  `recovery_code_date` datetime NOT NULL,
  `recovery_validating_date` datetime DEFAULT NULL,
  `recovery_code_expiration_date` datetime NOT NULL,
  `recovery_deletion_date` datetime DEFAULT NULL,
  `recovery_email` varchar(25) DEFAULT NULL,
  `recovery_pseudo` varchar(25) NOT NULL,
  `recovery_code` varchar(6) NOT NULL,
  `recovery_browser` varchar(100) NOT NULL,
  `recovery_ip` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `sign_up` (
  `sign_up_id` int(11) UNSIGNED ZEROFILL AUTO_INCREMENT PRIMARY KEY,
  `sign_up_pseudo` varchar(25) NOT NULL,
  `sign_up_email` varchar(25) NOT NULL,
  `sign_up_pass` varchar(100) NOT NULL,
  `sign_up_creation_date` datetime NOT NULL,
  `sign_up_code_expiration_date` datetime NOT NULL,
  `sign_up_validating_date` datetime DEFAULT NULL,
  `sign_up_deletion_date` datetime DEFAULT NULL,
  `sign_up_cancelation_date` datetime DEFAULT NULL,
  `sign_up_browser` varchar(100) NOT NULL,
  `sign_up_ip` varchar(50) NOT NULL,
  `sign_up_code` varchar(6) NOT NULL,
  `sign_up_code_date` datetime NOT NULL,
  `sign_up_terms_ok` BIT(1) NOT NULL DEFAULT b'0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `doc` (
  `doc_id` int(11) UNSIGNED ZEROFILL AUTO_INCREMENT PRIMARY KEY,
  `doc_title` varchar(255) NOT NULL,
  `doc_creation_date` datetime NOT NULL,
  `doc_update_date` datetime DEFAULT NULL,
  `doc_deletion_date` datetime DEFAULT NULL,
  `doc_nature` varchar(10),
  `doc_creation_author` int(11) UNSIGNED NOT null,
  `doc_update_author` int(11) UNSIGNED DEFAULT null,
  `doc_deletion_author` int(11) UNSIGNED DEFAULT null
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `code` (
  `code_id` varchar(10) NOT NULL PRIMARY KEY,
  `code_creation_date` datetime NOT NULL
);

CREATE TABLE `section` (
  `section_id` int(11) UNSIGNED ZEROFILL AUTO_INCREMENT PRIMARY KEY,
  `section_title` varchar(255) DEFAULT NULL,
  `section_text` varchar(1000) DEFAULT NULL,
  `section_order` int(11) NOT NULL,
  `section_creation_date` datetime NOT NULL,
  `section_update_date` datetime DEFAULT NULL,
  `section_deletion_date` datetime DEFAULT NULL,
  `section_creation_author` int(11) UNSIGNED NOT null,
  `section_update_author` int(11) UNSIGNED DEFAULT null,
  `section_deletion_author` int(11) UNSIGNED DEFAULT null,
  `section_doc` int(11) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `media` (
  `media_id` int(11) UNSIGNED ZEROFILL AUTO_INCREMENT PRIMARY KEY,
  `media_title` varchar(255) DEFAULT NULL,
  `media_name` varchar(255) DEFAULT NULL,
  `media_folder` varchar(50) DEFAULT NULL,
  `media_type` varchar(6) DEFAULT NULL,
  `media_position` varchar(6) DEFAULT NULL,
  `media_creation_date` datetime NOT NULL,
  `media_update_date` datetime DEFAULT NULL,
  `media_deletion_date` datetime DEFAULT NULL,
  `media_creation_author` int(11) UNSIGNED NOT null,
  `media_update_author` int(11) UNSIGNED DEFAULT null,
  `media_deletion_author` int(11) UNSIGNED DEFAULT null,
  `media_section` int(11) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


ALTER TABLE `person`
  ADD CONSTRAINT `person_ibfk_2` FOREIGN KEY (`person_address`) REFERENCES `address` (`address_id`);
  /*ADD CONSTRAINT `person_ibfk_2` FOREIGN KEY (`person_update_author`) REFERENCES `user` (`user_id`);
  ADD CONSTRAINT `person_ibfk_3` FOREIGN KEY (`person_deletion_author`) REFERENCES `user` (`user_id`);*/


ALTER TABLE `user`
  ADD CONSTRAINT `user_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `person` (`person_id`),
  ADD CONSTRAINT `user_ibfk_2` FOREIGN KEY (`user_description`) REFERENCES `doc` (`doc_id`);
  /*ADD CONSTRAINT `user_ibfk_2` FOREIGN KEY (`user_banishment_author`) REFERENCES `user` (`user_id`);
  ADD CONSTRAINT `user_ibfk_5` FOREIGN KEY (`user_sign_up`) REFERENCES `sign_up` (`sign_up_id`);*/

ALTER TABLE `child`
  ADD CONSTRAINT `child_ibfk_1` FOREIGN KEY (`child_id`) REFERENCES `person` (`person_id`),
  ADD CONSTRAINT `child_ibfk_2` FOREIGN KEY (`child_family`) REFERENCES `family` (`family_id`),
  ADD CONSTRAINT `child_ibfk_3` FOREIGN KEY (`child_attachment_family`) REFERENCES `family` (`family_id`);


ALTER TABLE `recovery`
  ADD CONSTRAINT `recovery_ibfk_1` FOREIGN KEY (`recovery_creation_author`) REFERENCES `user` (`user_id`);
  /*
  ADD CONSTRAINT `address_ibfk_2` FOREIGN KEY (`address_update_author`) REFERENCES `user` (`user_id`);
  ADD CONSTRAINT `address_ibfk_3` FOREIGN KEY (`address_deletion_author`) REFERENCES `user` (`user_id`);
  */

ALTER TABLE `family`
  ADD CONSTRAINT `family_ibfk_1` FOREIGN KEY (`family_parent_one`) REFERENCES `user` (`user_id`),
  ADD CONSTRAINT `family_ibfk_3` FOREIGN KEY (`family_parent_two`) REFERENCES `user` (`user_id`),
  ADD CONSTRAINT `family_ibfk_4` FOREIGN KEY (`family_baby_sitter`) REFERENCES `baby_sitter` (`baby_sitter_id`);


ALTER TABLE `baby_sitter`
  ADD CONSTRAINT `baby_sitter_ibfk_3` FOREIGN KEY (`baby_sitter_user`) REFERENCES `user` (`user_id`),
  ADD CONSTRAINT `baby_sitter_ibfk_4` FOREIGN KEY (`baby_sitter_family`) REFERENCES `family` (`family_id`);
  /*ADD CONSTRAINT `baby_sitter_ibfk_5` FOREIGN KEY (`baby_sitter_deletion_author`) REFERENCES `user` (`user_id`);*/

ALTER TABLE `child_care`
  ADD CONSTRAINT `child_care_ibfk_1` FOREIGN KEY (`child_care_baby_sitter`) REFERENCES `baby_sitter` (`baby_sitter_id`);
  /*
  ADD CONSTRAINT `care_ibfk_2` FOREIGN KEY (`care_creation_author`) REFERENCES `responsible` (`responsible_user`),
  ADD CONSTRAINT `care_ibfk_3` FOREIGN KEY (`care_responsible`) REFERENCES `responsible` (`responsible_user`);*/
  /*ADD CONSTRAINT `child_care_ibfk_4` FOREIGN KEY (`child_care_update_author`) REFERENCES `user` (`user_id`);
  ADD CONSTRAINT `child_care_ibfk_5` FOREIGN KEY (`child_care_deletion_author`) REFERENCES `user` (`user_id`);*/



ALTER TABLE `child_care_task`
  ADD CONSTRAINT `child_care_task_ibfk_1` FOREIGN KEY (`child_care_task_child_care`) REFERENCES `child_care` (`child_care_id`);

  /*
  ADD CONSTRAINT `child_care_task_ibfk_2` FOREIGN KEY (`child_care_task_child`) REFERENCES `child_care` (`child_care_child`),
  ADD CONSTRAINT `child_care_task_ibfk_3` FOREIGN KEY (`child_care_task_task`) REFERENCES `task` (`task_id`);
  ADD CONSTRAINT `child_care_ibfk_4` FOREIGN KEY (`child_care_update_author`) REFERENCES `user` (`user_id`);
  ADD CONSTRAINT `child_care_ibfk_5` FOREIGN KEY (`child_care_deletion_author`) REFERENCES `user` (`user_id`);*/

ALTER TABLE `contact`
  ADD CONSTRAINT `contact_ibfk_1` FOREIGN KEY (`contact_response_author`) REFERENCES `user` (`user_id`);



ALTER TABLE `log_in`
  ADD CONSTRAINT `log_in_ibfk_1` FOREIGN KEY (`log_in_creation_author`) REFERENCES `user` (`user_id`);
  /*ADD CONSTRAINT `log_in_ibfk_2` FOREIGN KEY (`log_in_update_author`) REFERENCES `user` (`user_id`);
  ADD CONSTRAINT `log_in_ibfk_3` FOREIGN KEY (`log_in_deletion_author`) REFERENCES `user` (`user_id`);*/


ALTER TABLE `doc`
  ADD CONSTRAINT `doc_ibfk_1` FOREIGN KEY (`doc_creation_author`) REFERENCES `user` (`user_id`);
  /*ADD CONSTRAINT `doc_ibfk_2` FOREIGN KEY (`doc_update_author`) REFERENCES `user` (`user_id`);
  ADD CONSTRAINT `doc_ibfk_3` FOREIGN KEY (`doc_deletion_author`) REFERENCES `user` (`user_id`);*/

ALTER TABLE `section`
  /*ADD CONSTRAINT `section_ibfk_1` FOREIGN KEY (`section_creation_author`) REFERENCES `admin` (`admin_id`),*/
  /*ADD CONSTRAINT `section_ibfk_2` FOREIGN KEY (`section_update_author`) REFERENCES `user` (`user_id`),
  ADD CONSTRAINT `section_ibfk_3` FOREIGN KEY (`section_deletion_author`) REFERENCES `user` (`user_id`),*/
  ADD CONSTRAINT `section_ibfk_4` FOREIGN KEY (`section_doc`) REFERENCES `doc` (`doc_id`);

ALTER TABLE `media`
  /*ADD CONSTRAINT `media_ibfk_1` FOREIGN KEY (`media_creation_author`) REFERENCES `admin` (`admin_id`),*/
  /*ADD CONSTRAINT `media_ibfk_2` FOREIGN KEY (`media_update_author`) REFERENCES `user` (`user_id`),
  ADD CONSTRAINT `media_ibfk_3` FOREIGN KEY (`media_deletion_author`) REFERENCES `user` (`user_id`),*/
  ADD CONSTRAINT `media_ibfk_4` FOREIGN KEY (`media_section`) REFERENCES `section` (`section_id`);

COMMIT;
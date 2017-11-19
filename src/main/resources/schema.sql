-- MySQL Script generated by MySQL Workbench
-- Mon Nov  6 18:14:05 2017
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0;
SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0;
SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema kanban
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema kanban
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `kanban`
  DEFAULT CHARACTER SET utf8;
USE `kanban`;

-- -----------------------------------------------------
-- Table `kanban`.`project`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kanban`.`project` (
  `id`          INT(11)      NOT NULL AUTO_INCREMENT,
  `name`        VARCHAR(128) NOT NULL,
  `description` MEDIUMTEXT   NULL     DEFAULT NULL,
  `is_public`   TINYINT(1)   NOT NULL,
  `team_id`     INT(11)      NULL     DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `Relationship26` USING BTREE (`team_id` ASC),
  CONSTRAINT `project_team`
  FOREIGN KEY (`team_id`)
  REFERENCES `kanban`.`team` (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  ROW_FORMAT = DYNAMIC;

-- -----------------------------------------------------
-- Table `kanban`.`activity`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kanban`.`activity` (
  `project_id` INT(11)     NOT NULL,
  `user_id`    INT(11)     NOT NULL,
  `operation`  VARCHAR(64) NOT NULL,
  `obj_type`   VARCHAR(64) NOT NULL,
  `obj_id`     INT(11)     NOT NULL,
  `time`       TIME        NOT NULL,
  PRIMARY KEY (`project_id`, `user_id`),
  INDEX `activity_user` (`user_id` ASC),
  CONSTRAINT `activity_project`
  FOREIGN KEY (`project_id`)
  REFERENCES `kanban`.`project` (`id`),
  CONSTRAINT `activity_user`
  FOREIGN KEY (`user_id`)
  REFERENCES `kanban`.`user` (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `kanban`.`mcolumn`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kanban`.`mcolumn` (
  `id`            INT(11)      NOT NULL AUTO_INCREMENT,
  `name`          VARCHAR(128) NOT NULL,
  `description`   MEDIUMTEXT   NULL     DEFAULT NULL,
  `display_order` INT(11)      NOT NULL,
  `card_limit`    INT(11)      NULL     DEFAULT NULL,
  `project_id`    INT(11)      NULL     DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `IX_Relationship30` (`project_id` ASC),
  CONSTRAINT `project_mcolumn`
  FOREIGN KEY (`project_id`)
  REFERENCES `kanban`.`project` (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  ROW_FORMAT = DYNAMIC;

-- -----------------------------------------------------
-- Table `kanban`.`card`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kanban`.`card` (
  `id`            INT(11)      NOT NULL AUTO_INCREMENT,
  `name`          VARCHAR(128) NOT NULL,
  `content`       MEDIUMTEXT   NULL     DEFAULT NULL,
  `due_time`      TIME         NULL     DEFAULT NULL,
  `display_order` INT(11)      NOT NULL,
  `mcolumn_id`    INT(11)      NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `IX_Relationship28` (`mcolumn_id` ASC),
  CONSTRAINT `mcolumn_card`
  FOREIGN KEY (`mcolumn_id`)
  REFERENCES `kanban`.`mcolumn` (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  ROW_FORMAT = DYNAMIC;


-- -----------------------------------------------------
-- Table `kanban`.`attachment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kanban`.`attachment` (
  `card_id`   INT(11)      NOT NULL,
  `user_id`   INT(11)      NOT NULL,
  `file_name` VARCHAR(128) NOT NULL,
  `path`      VARCHAR(128) NOT NULL,
  `time`      TIME         NOT NULL,
  PRIMARY KEY (`card_id`, `user_id`),
  INDEX `attachment_user` (`user_id` ASC),
  CONSTRAINT `attachment_card`
  FOREIGN KEY (`card_id`)
  REFERENCES `kanban`.`card` (`id`),
  CONSTRAINT `attachment_user`
  FOREIGN KEY (`user_id`)
  REFERENCES `kanban`.`user` (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `kanban`.`card_assignment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kanban`.`card_assignment` (
  `card_id` INT(11) NOT NULL,
  `user_id` INT(11) NOT NULL,
  PRIMARY KEY (`card_id`, `user_id`),
  INDEX `card_assignment_user` (`user_id` ASC),
  CONSTRAINT `card_assignment_card`
  FOREIGN KEY (`card_id`)
  REFERENCES `kanban`.`card` (`id`),
  CONSTRAINT `card_assignment_user`
  FOREIGN KEY (`user_id`)
  REFERENCES `kanban`.`user` (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `kanban`.`card_subscription`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kanban`.`card_subscription` (
  `card_id` INT(11) NOT NULL,
  `user_id` INT(11) NOT NULL,
  PRIMARY KEY (`card_id`, `user_id`),
  INDEX `card_subscription_user` (`user_id` ASC),
  CONSTRAINT `card_subscription_card`
  FOREIGN KEY (`card_id`)
  REFERENCES `kanban`.`card` (`id`),
  CONSTRAINT `card_subscription_user`
  FOREIGN KEY (`user_id`)
  REFERENCES `kanban`.`user` (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `kanban`.`checklist`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kanban`.`checklist` (
  `id`      INT(11)      NOT NULL AUTO_INCREMENT,
  `name`    VARCHAR(128) NOT NULL,
  `card_id` INT(11)      NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `IX_Relationship31` (`card_id` ASC),
  CONSTRAINT `card_checcklist`
  FOREIGN KEY (`card_id`)
  REFERENCES `kanban`.`card` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  ROW_FORMAT = DYNAMIC;


-- -----------------------------------------------------
-- Table `kanban`.`checklist_option`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kanban`.`checklist_option` (
  `id`           INT(11)      NOT NULL AUTO_INCREMENT,
  `name`         VARCHAR(128) NOT NULL,
  `is_completed` TINYINT(1)   NOT NULL,
  `checklist_id` INT(11)      NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `IX_Relationship29` (`checklist_id` ASC),
  CONSTRAINT `checklist_checklist_option`
  FOREIGN KEY (`checklist_id`)
  REFERENCES `kanban`.`checklist` (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  ROW_FORMAT = DYNAMIC;


-- -----------------------------------------------------
-- Table `kanban`.`clientdetails`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kanban`.`clientdetails` (
  `appId`                  VARCHAR(256)  NOT NULL,
  `resourceIds`            VARCHAR(256)  NULL DEFAULT NULL,
  `appSecret`              VARCHAR(256)  NULL DEFAULT NULL,
  `scope`                  VARCHAR(256)  NULL DEFAULT NULL,
  `grantTypes`             VARCHAR(256)  NULL DEFAULT NULL,
  `redirectUrl`            VARCHAR(256)  NULL DEFAULT NULL,
  `authorities`            VARCHAR(256)  NULL DEFAULT NULL,
  `access_token_validity`  INT(11)       NULL DEFAULT NULL,
  `refresh_token_validity` INT(11)       NULL DEFAULT NULL,
  `additionalInformation`  VARCHAR(4096) NULL DEFAULT NULL,
  `autoApproveScopes`      VARCHAR(256)  NULL DEFAULT NULL,
  PRIMARY KEY (`appId`)
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  ROW_FORMAT = DYNAMIC;


-- -----------------------------------------------------
-- Table `kanban`.`comment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kanban`.`comment` (
  `card_id` INT(11)    NOT NULL,
  `user_id` INT(11)    NOT NULL,
  `content` MEDIUMTEXT NOT NULL,
  `time`    TIME       NOT NULL,
  PRIMARY KEY (`card_id`, `user_id`),
  INDEX `comment_user` (`user_id` ASC),
  CONSTRAINT `comment_card`
  FOREIGN KEY (`card_id`)
  REFERENCES `kanban`.`card` (`id`),
  CONSTRAINT `comment_user`
  FOREIGN KEY (`user_id`)
  REFERENCES `kanban`.`user` (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;

-- -----------------------------------------------------
-- Table `kanban`.`label`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kanban`.`label` (
  `id`         INT(11)      NOT NULL AUTO_INCREMENT,
  `name`       VARCHAR(128) NOT NULL,
  `color`      CHAR(6)      NOT NULL,
  `project_id` INT(11)      NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `IX_Relationship27` (`project_id` ASC),
  CONSTRAINT `project_label`
  FOREIGN KEY (`project_id`)
  REFERENCES `kanban`.`project` (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  ROW_FORMAT = DYNAMIC;

-- -----------------------------------------------------
-- Table `kanban`.`label_card`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kanban`.`label_card` (
  `label_id` INT(11) NOT NULL,
  `card_id`  INT(11) NOT NULL,
  PRIMARY KEY (`label_id`, `card_id`),
  INDEX `label_card_card` (`card_id` ASC),
  CONSTRAINT `label_card_card`
  FOREIGN KEY (`card_id`)
  REFERENCES `kanban`.`card` (`id`),
  CONSTRAINT `label_card_label`
  FOREIGN KEY (`label_id`)
  REFERENCES `kanban`.`label` (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;

-- -----------------------------------------------------
-- Table `kanban`.`oauth_access_token`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kanban`.`oauth_access_token` (
  `token_id`          VARCHAR(256) NULL DEFAULT NULL,
  `token`             BLOB         NULL DEFAULT NULL,
  `authentication_id` VARCHAR(256) NOT NULL,
  `user_name`         VARCHAR(256) NULL DEFAULT NULL,
  `client_id`         VARCHAR(256) NULL DEFAULT NULL,
  `authentication`    BLOB         NULL DEFAULT NULL,
  `refresh_token`     VARCHAR(256) NULL DEFAULT NULL,
  PRIMARY KEY (`authentication_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  ROW_FORMAT = DYNAMIC;


-- -----------------------------------------------------
-- Table `kanban`.`oauth_approvals`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kanban`.`oauth_approvals` (
  `userId`         VARCHAR(256) NULL     DEFAULT NULL,
  `clientId`       VARCHAR(256) NULL     DEFAULT NULL,
  `scope`          VARCHAR(256) NULL     DEFAULT NULL,
  `status`         VARCHAR(10)  NULL     DEFAULT NULL,
  `expiresAt`      TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `lastModifiedAt` TIMESTAMP    NOT NULL DEFAULT '0000-00-00 00:00:00'
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  ROW_FORMAT = DYNAMIC;


-- -----------------------------------------------------
-- Table `kanban`.`oauth_client_details`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kanban`.`oauth_client_details` (
  `client_id`               VARCHAR(256)  NOT NULL,
  `resource_ids`            VARCHAR(256)  NULL DEFAULT NULL,
  `client_secret`           VARCHAR(256)  NULL DEFAULT NULL,
  `scope`                   VARCHAR(256)  NULL DEFAULT NULL,
  `authorized_grant_types`  VARCHAR(256)  NULL DEFAULT NULL,
  `web_server_redirect_uri` VARCHAR(256)  NULL DEFAULT NULL,
  `authorities`             VARCHAR(256)  NULL DEFAULT NULL,
  `access_token_validity`   INT(11)       NULL DEFAULT NULL,
  `refresh_token_validity`  INT(11)       NULL DEFAULT NULL,
  `additional_information`  VARCHAR(4096) NULL DEFAULT NULL,
  `autoapprove`             VARCHAR(256)  NULL DEFAULT NULL,
  PRIMARY KEY (`client_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  ROW_FORMAT = DYNAMIC;


-- -----------------------------------------------------
-- Table `kanban`.`oauth_client_token`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kanban`.`oauth_client_token` (
  `token_id`          VARCHAR(256) NULL DEFAULT NULL,
  `token`             BLOB         NULL DEFAULT NULL,
  `authentication_id` VARCHAR(256) NOT NULL,
  `user_name`         VARCHAR(256) NULL DEFAULT NULL,
  `client_id`         VARCHAR(256) NULL DEFAULT NULL,
  PRIMARY KEY (`authentication_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  ROW_FORMAT = DYNAMIC;


-- -----------------------------------------------------
-- Table `kanban`.`oauth_code`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kanban`.`oauth_code` (
  `code`           VARCHAR(256) NULL DEFAULT NULL,
  `authentication` BLOB         NULL DEFAULT NULL
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  ROW_FORMAT = DYNAMIC;


-- -----------------------------------------------------
-- Table `kanban`.`oauth_refresh_token`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kanban`.`oauth_refresh_token` (
  `token_id`       VARCHAR(256) NULL DEFAULT NULL,
  `token`          BLOB         NULL DEFAULT NULL,
  `authentication` BLOB         NULL DEFAULT NULL
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  ROW_FORMAT = DYNAMIC;


-- -----------------------------------------------------
-- Table `kanban`.`project_member`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kanban`.`project_member` (
  `project_id` INT(11)    NOT NULL,
  `user_id`    INT(11)    NOT NULL,
  `isAdmin`    TINYINT(1) NOT NULL,
  PRIMARY KEY (`project_id`, `user_id`),
  INDEX `project_member_user` (`user_id` ASC),
  CONSTRAINT `project_member_project`
  FOREIGN KEY (`project_id`)
  REFERENCES `kanban`.`project` (`id`),
  CONSTRAINT `project_member_user`
  FOREIGN KEY (`user_id`)
  REFERENCES `kanban`.`user` (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `kanban`.`team_user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kanban`.`team_user` (
  `team_id`  INT(11)    NOT NULL,
  `user_id`  INT(11)    NOT NULL,
  `is_admin` TINYINT(1) NOT NULL,
  PRIMARY KEY (`team_id`, `user_id`),
  INDEX `Relationship4` USING BTREE (`user_id` ASC),
  CONSTRAINT `team_user_team`
  FOREIGN KEY (`team_id`)
  REFERENCES `kanban`.`team` (`id`),
  CONSTRAINT `team_user_user`
  FOREIGN KEY (`user_id`)
  REFERENCES `kanban`.`user` (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  ROW_FORMAT = DYNAMIC;


SET SQL_MODE = @OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS;
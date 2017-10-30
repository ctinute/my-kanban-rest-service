# -- CREATE SCHEMA IF NOT EXISTS `kanban` DEFAULT CHARACTER SET utf8 ;
# -- USE `kanban` ;
#
# -- -----------------------------------------------------
# -- Table `kanban`.`team`
# -- -----------------------------------------------------
# DROP TABLE IF EXISTS `kanban`.`team` ;
#
# CREATE TABLE IF NOT EXISTS `kanban`.`team` (
#   `id` INT(11) NOT NULL,
#   `name` VARCHAR(128) NOT NULL,
#   `description` MEDIUMTEXT NULL DEFAULT NULL,
#   `is_public` TINYINT(1) NOT NULL,
#   PRIMARY KEY (`id`))
#   ENGINE = InnoDB
#   DEFAULT CHARACTER SET = utf8;
#
#
# -- -----------------------------------------------------
# -- Table `kanban`.`project`
# -- -----------------------------------------------------
# DROP TABLE IF EXISTS `kanban`.`project` ;
#
# CREATE TABLE IF NOT EXISTS `kanban`.`project` (
#   `id` INT(11) NOT NULL,
#   `name` VARCHAR(128) NOT NULL,
#   `description` MEDIUMTEXT NULL DEFAULT NULL,
#   `is_public` TINYINT(1) NOT NULL,
#   `team_id` INT(11) NULL DEFAULT NULL,
#   PRIMARY KEY (`id`),
#   CONSTRAINT `Relationship26`
#   FOREIGN KEY (`team_id`)
#   REFERENCES `kanban`.`team` (`id`))
#   ENGINE = InnoDB
#   DEFAULT CHARACTER SET = utf8;
#
#
# -- -----------------------------------------------------
# -- Table `kanban`.`user`
# -- -----------------------------------------------------
# DROP TABLE IF EXISTS `kanban`.`user` ;
#
# CREATE TABLE IF NOT EXISTS `kanban`.`user` (
#   `id` INT(11) NOT NULL AUTO_INCREMENT,
#   `email` VARCHAR(128) NOT NULL,
#   `name` VARCHAR(128) NOT NULL,
#   `password` VARCHAR(60) NOT NULL,
#   `username` VARCHAR(32) NOT NULL,
#   PRIMARY KEY (`id`),
#   UNIQUE INDEX `email` (`email` ASC),
#   UNIQUE INDEX `username` (`username` ASC))
#   ENGINE = InnoDB
#   AUTO_INCREMENT = 4
#   DEFAULT CHARACTER SET = utf8;
#
#
# -- -----------------------------------------------------
# -- Table `kanban`.`activity`
# -- -----------------------------------------------------
# DROP TABLE IF EXISTS `kanban`.`activity` ;
#
# CREATE TABLE IF NOT EXISTS `kanban`.`activity` (
#   `id` INT(11) NOT NULL,
#   `operation` VARCHAR(64) NOT NULL,
#   `obj_type` VARCHAR(64) NOT NULL,
#   `obj_id` INT(11) NOT NULL,
#   `time` TIME NOT NULL,
#   `project_id` INT(11) NOT NULL,
#   `user_id` INT(11) NOT NULL,
#   PRIMARY KEY (`id`),
#   INDEX `fk_activity_user1_idx` (`user_id` ASC),
#   CONSTRAINT `Relationship24`
#   FOREIGN KEY (`project_id`)
#   REFERENCES `kanban`.`project` (`id`),
#   CONSTRAINT `fk_activity_user1`
#   FOREIGN KEY (`user_id`)
#   REFERENCES `kanban`.`user` (`id`)
#     ON DELETE NO ACTION
#     ON UPDATE NO ACTION)
#   ENGINE = InnoDB
#   DEFAULT CHARACTER SET = utf8;
#
#
# -- -----------------------------------------------------
# -- Table `kanban`.`mcolumn`
# -- -----------------------------------------------------
# DROP TABLE IF EXISTS `kanban`.`mcolumn` ;
#
# CREATE TABLE IF NOT EXISTS `kanban`.`mcolumn` (
#   `id` INT(11) NOT NULL,
#   `name` VARCHAR(128) NOT NULL,
#   `description` MEDIUMTEXT NULL DEFAULT NULL,
#   `display_order` INT(11) NOT NULL,
#   `card_limit` INT(11) NULL DEFAULT NULL,
#   `project_id` INT(11) NOT NULL,
#   PRIMARY KEY (`id`),
#   CONSTRAINT `FKigv4of6bbynbgipxqf9hot4nt`
#   FOREIGN KEY (`project_id`)
#   REFERENCES `kanban`.`project` (`id`))
#   ENGINE = InnoDB
#   DEFAULT CHARACTER SET = utf8;
#
#
# -- -----------------------------------------------------
# -- Table `kanban`.`card`
# -- -----------------------------------------------------
# DROP TABLE IF EXISTS `kanban`.`card` ;
#
# CREATE TABLE IF NOT EXISTS `kanban`.`card` (
#   `id` INT(11) NOT NULL,
#   `name` VARCHAR(128) NOT NULL,
#   `content` TEXT NULL DEFAULT NULL,
#   `due_time` TIME NULL DEFAULT NULL,
#   `mcolumn_id` INT(11) NOT NULL,
#   PRIMARY KEY (`id`),
#   CONSTRAINT `FK2aib89h0535nqnyqjdds4gc2i`
#   FOREIGN KEY (`mcolumn_id`)
#   REFERENCES `kanban`.`mcolumn` (`id`))
#   ENGINE = InnoDB
#   DEFAULT CHARACTER SET = utf8;
#
#
# -- -----------------------------------------------------
# -- Table `kanban`.`attachment`
# -- -----------------------------------------------------
# DROP TABLE IF EXISTS `kanban`.`attachment` ;
#
# CREATE TABLE IF NOT EXISTS `kanban`.`attachment` (
#   `id` INT(11) NOT NULL,
#   `name` VARCHAR(128) NOT NULL,
#   `path` VARCHAR(256) NOT NULL,
#   `time` TIME NULL DEFAULT NULL,
#   `card_id` INT(11) NULL DEFAULT NULL,
#   `user_id` INT(11) NULL DEFAULT NULL,
#   PRIMARY KEY (`id`),
#   INDEX `IX_Relationship28` (`user_id` ASC),
#   CONSTRAINT `Relationship22`
#   FOREIGN KEY (`card_id`)
#   REFERENCES `kanban`.`card` (`id`),
#   CONSTRAINT `Relationship28`
#   FOREIGN KEY (`user_id`)
#   REFERENCES `kanban`.`user` (`id`))
#   ENGINE = InnoDB
#   DEFAULT CHARACTER SET = utf8;
#
#
# -- -----------------------------------------------------
# -- Table `kanban`.`label`
# -- -----------------------------------------------------
# DROP TABLE IF EXISTS `kanban`.`label` ;
#
# CREATE TABLE IF NOT EXISTS `kanban`.`label` (
#   `id` INT(11) NOT NULL,
#   `name` VARCHAR(128) NOT NULL,
#   `color` CHAR(6) NOT NULL,
#   `project_id` INT(11) NULL DEFAULT NULL,
#   PRIMARY KEY (`id`),
#   CONSTRAINT `FK4nf623d0nwyd4aqje3d3cxx1b`
#   FOREIGN KEY (`project_id`)
#   REFERENCES `kanban`.`project` (`id`))
#   ENGINE = InnoDB
#   DEFAULT CHARACTER SET = utf8;
#
#
# -- -----------------------------------------------------
# -- Table `kanban`.`card_label`
# -- -----------------------------------------------------
# DROP TABLE IF EXISTS `kanban`.`card_label` ;
#
# CREATE TABLE IF NOT EXISTS `kanban`.`card_label` (
#   `card_id` INT(11) NOT NULL,
#   `label_id` INT(11) NOT NULL,
#   PRIMARY KEY (`card_id`, `label_id`),
#   INDEX `Relationship15` (`label_id` ASC),
#   CONSTRAINT `Relationship14`
#   FOREIGN KEY (`card_id`)
#   REFERENCES `kanban`.`card` (`id`),
#   CONSTRAINT `Relationship15`
#   FOREIGN KEY (`label_id`)
#   REFERENCES `kanban`.`label` (`id`))
#   ENGINE = InnoDB
#   DEFAULT CHARACTER SET = utf8;
#
#
# -- -----------------------------------------------------
# -- Table `kanban`.`checklist`
# -- -----------------------------------------------------
# DROP TABLE IF EXISTS `kanban`.`checklist` ;
#
# CREATE TABLE IF NOT EXISTS `kanban`.`checklist` (
#   `id` INT(11) NOT NULL,
#   `name` VARCHAR(128) NOT NULL,
#   `card_id` INT(11) NULL DEFAULT NULL,
#   PRIMARY KEY (`id`),
#   CONSTRAINT `Relationship21`
#   FOREIGN KEY (`card_id`)
#   REFERENCES `kanban`.`card` (`id`))
#   ENGINE = InnoDB
#   DEFAULT CHARACTER SET = utf8;
#
#
# -- -----------------------------------------------------
# -- Table `kanban`.`checklist_option`
# -- -----------------------------------------------------
# DROP TABLE IF EXISTS `kanban`.`checklist_option` ;
#
# CREATE TABLE IF NOT EXISTS `kanban`.`checklist_option` (
#   `id` INT(11) NOT NULL,
#   `name` VARCHAR(128) NOT NULL,
#   `is_completed` TINYINT(1) NOT NULL,
#   `checklist_id` INT(11) NOT NULL,
#   PRIMARY KEY (`id`),
#   CONSTRAINT `FKljj3tx9q14v2j67yubf68np6v`
#   FOREIGN KEY (`checklist_id`)
#   REFERENCES `kanban`.`checklist` (`id`))
#   ENGINE = InnoDB
#   DEFAULT CHARACTER SET = utf8;
#
#
# -- -----------------------------------------------------
# -- Table `kanban`.`comment`
# -- -----------------------------------------------------
# DROP TABLE IF EXISTS `kanban`.`comment` ;
#
# CREATE TABLE IF NOT EXISTS `kanban`.`comment` (
#   `id` INT(11) NOT NULL,
#   `content` MEDIUMTEXT NOT NULL,
#   `time` TIME NOT NULL,
#   `card_id` INT(11) NOT NULL,
#   `user_id` INT(11) NOT NULL,
#   PRIMARY KEY (`id`),
#   CONSTRAINT `Relationship23`
#   FOREIGN KEY (`card_id`)
#   REFERENCES `kanban`.`card` (`id`),
#   CONSTRAINT `Relationship27`
#   FOREIGN KEY (`user_id`)
#   REFERENCES `kanban`.`user` (`id`))
#   ENGINE = InnoDB
#   DEFAULT CHARACTER SET = utf8;
#
#
# -- -----------------------------------------------------
# -- Table `kanban`.`project_user`
# -- -----------------------------------------------------
# DROP TABLE IF EXISTS `kanban`.`project_user` ;
#
# CREATE TABLE IF NOT EXISTS `kanban`.`project_user` (
#   `project_id` INT(11) NOT NULL,
#   `user_id` INT(11) NOT NULL,
#   `is_admin` TINYINT(1) NOT NULL,
#   PRIMARY KEY (`project_id`, `user_id`),
#   INDEX `Relationship6` (`user_id` ASC),
#   CONSTRAINT `Relationship5`
#   FOREIGN KEY (`project_id`)
#   REFERENCES `kanban`.`project` (`id`),
#   CONSTRAINT `Relationship6`
#   FOREIGN KEY (`user_id`)
#   REFERENCES `kanban`.`user` (`id`))
#   ENGINE = InnoDB
#   DEFAULT CHARACTER SET = utf8;
#
#
# -- -----------------------------------------------------
# -- Table `kanban`.`team_user`
# -- -----------------------------------------------------
# DROP TABLE IF EXISTS `kanban`.`team_user` ;
#
# CREATE TABLE IF NOT EXISTS `kanban`.`team_user` (
#   `team_id` INT(11) NOT NULL,
#   `user_id` INT(11) NOT NULL,
#   `is_admin` TINYINT(1) NOT NULL,
#   PRIMARY KEY (`team_id`, `user_id`),
#   INDEX `Relationship4` (`user_id` ASC),
#   CONSTRAINT `Relationship3`
#   FOREIGN KEY (`team_id`)
#   REFERENCES `kanban`.`team` (`id`),
#   CONSTRAINT `Relationship4`
#   FOREIGN KEY (`user_id`)
#   REFERENCES `kanban`.`user` (`id`))
#   ENGINE = InnoDB
#   DEFAULT CHARACTER SET = utf8;
#
#
# -- -----------------------------------------------------
# -- Table `kanban`.`user_card_assignment`
# -- -----------------------------------------------------
# DROP TABLE IF EXISTS `kanban`.`user_card_assignment` ;
#
# CREATE TABLE IF NOT EXISTS `kanban`.`user_card_assignment` (
#   `user_id` INT(11) NOT NULL,
#   `card_id` INT(11) NOT NULL,
#   PRIMARY KEY (`user_id`, `card_id`),
#   INDEX `Relationship30` (`card_id` ASC),
#   CONSTRAINT `Relationship29`
#   FOREIGN KEY (`user_id`)
#   REFERENCES `kanban`.`user` (`id`),
#   CONSTRAINT `Relationship30`
#   FOREIGN KEY (`card_id`)
#   REFERENCES `kanban`.`card` (`id`))
#   ENGINE = InnoDB
#   DEFAULT CHARACTER SET = utf8;
#
#
# -- -----------------------------------------------------
# -- Table `kanban`.`user_card_subscription`
# -- -----------------------------------------------------
# DROP TABLE IF EXISTS `kanban`.`user_card_subscription` ;
#
# CREATE TABLE IF NOT EXISTS `kanban`.`user_card_subscription` (
#   `card_id` INT(11) NOT NULL,
#   `user_id` INT(11) NOT NULL,
#   PRIMARY KEY (`card_id`, `user_id`),
#   INDEX `Relationship18` (`user_id` ASC),
#   CONSTRAINT `Relationship17`
#   FOREIGN KEY (`card_id`)
#   REFERENCES `kanban`.`card` (`id`),
#   CONSTRAINT `Relationship18`
#   FOREIGN KEY (`user_id`)
#   REFERENCES `kanban`.`user` (`id`))
#   ENGINE = InnoDB
#   DEFAULT CHARACTER SET = utf8;
#
#

# SET SQL_MODE=@OLD_SQL_MODE;
# SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
# SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;


# -- ----------------------------------------------------
# -- Token manager
# -- ----------------------------------------------------

-- used in tests that use HSQL
CREATE TABLE IF NOT EXISTS oauth_client_details (
  client_id               VARCHAR(256) PRIMARY KEY,
  resource_ids            VARCHAR(256),
  client_secret           VARCHAR(256),
  scope                   VARCHAR(256),
  authorized_grant_types  VARCHAR(256),
  web_server_redirect_uri VARCHAR(256),
  authorities             VARCHAR(256),
  access_token_validity   INTEGER,
  refresh_token_validity  INTEGER,
  additional_information  VARCHAR(4096),
  autoapprove             VARCHAR(256)
);

CREATE TABLE IF NOT EXISTS oauth_client_token (
  token_id          VARCHAR(256),
  token             BLOB,
  authentication_id VARCHAR(256) PRIMARY KEY,
  user_name         VARCHAR(256),
  client_id         VARCHAR(256)
);

CREATE TABLE IF NOT EXISTS oauth_access_token (
  token_id          VARCHAR(256),
  token             BLOB,
  authentication_id VARCHAR(256) PRIMARY KEY,
  user_name         VARCHAR(256),
  client_id         VARCHAR(256),
  authentication    BLOB,
  refresh_token     VARCHAR(256)
);

CREATE TABLE IF NOT EXISTS oauth_refresh_token (
  token_id       VARCHAR(256),
  token          BLOB,
  authentication BLOB
);

CREATE TABLE IF NOT EXISTS oauth_code (
  code           VARCHAR(256),
  authentication BLOB
);

CREATE TABLE IF NOT EXISTS oauth_approvals (
  userId         VARCHAR(256),
  clientId       VARCHAR(256),
  scope          VARCHAR(256),
  status         VARCHAR(10),
  expiresAt      TIMESTAMP,
  lastModifiedAt TIMESTAMP
);

-- customized oauth_client_details table
CREATE TABLE IF NOT EXISTS ClientDetails (
  appId                  VARCHAR(256) PRIMARY KEY,
  resourceIds            VARCHAR(256),
  appSecret              VARCHAR(256),
  scope                  VARCHAR(256),
  grantTypes             VARCHAR(256),
  redirectUrl            VARCHAR(256),
  authorities            VARCHAR(256),
  access_token_validity  INTEGER,
  refresh_token_validity INTEGER,
  additionalInformation  VARCHAR(4096),
  autoApproveScopes      VARCHAR(256)
);
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema payments
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema payments
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `payments` DEFAULT CHARACTER SET utf8 ;
USE `payments` ;

-- -----------------------------------------------------
-- Table `payments`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `payments`.`user` (
  `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `login` VARCHAR(50) NOT NULL,
  `role` ENUM('ADMIN', 'CLIENT') NOT NULL,
  `status` ENUM('ACTIVE', 'BLOCKED') NOT NULL,
  `first_name` VARCHAR(50) NOT NULL,
  `second_name` VARCHAR(50) NOT NULL,
  `phone_number` VARCHAR(50) NULL,
  `email` VARCHAR(50) NULL,
  `create_time` TIMESTAMP NULL,
  `password` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `status_id_IDX` (`status` ASC) VISIBLE,
  INDEX `role_id_IDX` (`role` ASC) VISIBLE);


-- -----------------------------------------------------
-- Table `payments`.`account`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `payments`.`account` (
  `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT(20) UNSIGNED NOT NULL,
  `name` VARCHAR(50) NOT NULL,
  `status` ENUM('ACTIVE', 'BLOCKED', 'WAITING_FOR_UNLOCK') NOT NULL,
  `balance` DECIMAL(10,2) NOT NULL,
  `create_time` TIMESTAMP NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `FK_account_user_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `FK_account_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `payments`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE);


-- -----------------------------------------------------
-- Table `payments`.`bank_card`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `payments`.`bank_card` (
  `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `account_id` BIGINT(20) UNSIGNED NOT NULL,
  `card_number` VARCHAR(19) NOT NULL,
  `type` ENUM('VISA', 'MASTERCARD', 'AMERICAN_EXPRESS') NOT NULL COMMENT 'e.g.: MasterCard, Visa etc.',
  `currency` ENUM('UKRAINE_HRYVNIA', 'USA_DOLLAR', 'EURO') NOT NULL,
  `expiration_date` DATE NOT NULL,
  `cardholder_name` VARCHAR(100) NOT NULL,
  `balance` DECIMAL(10,2) NOT NULL DEFAULT 0,
  `cvv` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `currency_id_IDX` (`currency` ASC) VISIBLE,
  UNIQUE INDEX `account_id_UNIQUE` (`account_id` ASC) VISIBLE,
  CONSTRAINT `FK_credit_card_account`
    FOREIGN KEY (`id`)
    REFERENCES `payments`.`account` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


-- -----------------------------------------------------
-- Table `payments`.`payment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `payments`.`payment` (
  `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `sender_account_id` BIGINT(20) UNSIGNED NOT NULL,
  `receiver_account_id` BIGINT(20) UNSIGNED NOT NULL,
  `amount` DECIMAL(10,2) UNSIGNED NOT NULL,
  `currency` ENUM('UKRAINE_HRYVNIA', 'USA_DOLLAR', 'EURO') NOT NULL,
  `status` ENUM('PREPARED', 'SENT') NOT NULL,
  `create_time` TIMESTAMP NOT NULL,
  `send_time` TIMESTAMP NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK_payment_sender_account_idx` (`sender_account_id` ASC) VISIBLE,
  INDEX `FK_payment_receiver_account_idx` (`receiver_account_id` ASC) VISIBLE,
  CONSTRAINT `FK_payment_sender_account`
    FOREIGN KEY (`sender_account_id`)
    REFERENCES `payments`.`account` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_payment_receiver_account`
    FOREIGN KEY (`receiver_account_id`)
    REFERENCES `payments`.`account` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

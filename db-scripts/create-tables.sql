CREATE TABLE IF NOT EXISTS `golem`.`note` (
  `id` INT NOT NULL,
  `title` VARCHAR(45) NULL,
  `description` VARCHAR(45) NULL,
  `level` VARCHAR(15) NULL,
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP)
ENGINE = InnoDB;

-- Woking Copy with INT
CREATE TABLE IF NOT EXISTS `golem`.`note` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(45) NULL,
  `description` VARCHAR(45) NULL,
  `level` INT NULL,
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;
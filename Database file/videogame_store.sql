-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               8.0.28 - MySQL Community Server - GPL
-- Server OS:                    Win64
-- HeidiSQL Version:             11.3.0.6295
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for videogame_store
DROP DATABASE IF EXISTS `videogame_store`;
CREATE DATABASE IF NOT EXISTS `videogame_store` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `videogame_store`;

-- Dumping structure for table videogame_store.customer
DROP TABLE IF EXISTS `customer`;
CREATE TABLE IF NOT EXISTS `customer` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `first_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `last_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `country` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `city` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `balance` float unsigned NOT NULL DEFAULT '10000',
  `street_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `zip_code` int NOT NULL,
  `registration_date` date NOT NULL,
  `registration_time` time NOT NULL,
  `role` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `enabled` tinyint NOT NULL DEFAULT '1',
  `reset_password_token` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `email_verification_code` varchar(64) DEFAULT NULL,
  `auth_provider` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Data exporting was unselected.

-- Dumping structure for table videogame_store.customer_games
DROP TABLE IF EXISTS `customer_games`;
CREATE TABLE IF NOT EXISTS `customer_games` (
  `customer_id` int NOT NULL,
  `customer_videogame` int NOT NULL,
  KEY `FK__customer` (`customer_id`),
  KEY `FK__videogame` (`customer_videogame`),
  CONSTRAINT `FK__customer` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `FK__videogame` FOREIGN KEY (`customer_videogame`) REFERENCES `videogame` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Data exporting was unselected.

-- Dumping structure for table videogame_store.customer_money_history
DROP TABLE IF EXISTS `customer_money_history`;
CREATE TABLE IF NOT EXISTS `customer_money_history` (
  `id` int NOT NULL AUTO_INCREMENT,
  `order_id` int NOT NULL,
  `money_before_order` float NOT NULL,
  `money_after_order` float NOT NULL,
  PRIMARY KEY (`id`),
  KEY `order_id_fkey` (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Data exporting was unselected.

-- Dumping structure for table videogame_store.customer_order
DROP TABLE IF EXISTS `customer_order`;
CREATE TABLE IF NOT EXISTS `customer_order` (
  `order_id` int NOT NULL AUTO_INCREMENT,
  `customer_id` int NOT NULL,
  `videogame_id` int NOT NULL,
  `quantity` int NOT NULL,
  `order_date` date NOT NULL,
  `order_time` time NOT NULL,
  `order_transaction` varchar(64) NOT NULL,
  PRIMARY KEY (`order_id`) USING BTREE,
  KEY `FK_order_customer` (`customer_id`) USING BTREE,
  KEY `game_key` (`videogame_id`) USING BTREE,
  CONSTRAINT `FK_order_customer` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`),
  CONSTRAINT `game_key` FOREIGN KEY (`videogame_id`) REFERENCES `videogame` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Data exporting was unselected.

-- Dumping structure for table videogame_store.persistent_logins
DROP TABLE IF EXISTS `persistent_logins`;
CREATE TABLE IF NOT EXISTS `persistent_logins` (
  `series` varchar(64) NOT NULL,
  `username` varchar(50) NOT NULL,
  `token` varchar(50) NOT NULL,
  `last_used` timestamp NOT NULL,
  PRIMARY KEY (`series`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Data exporting was unselected.

-- Dumping structure for table videogame_store.platforms
DROP TABLE IF EXISTS `platforms`;
CREATE TABLE IF NOT EXISTS `platforms` (
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Data exporting was unselected.

-- Dumping structure for table videogame_store.roles
DROP TABLE IF EXISTS `roles`;
CREATE TABLE IF NOT EXISTS `roles` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Data exporting was unselected.

-- Dumping structure for table videogame_store.user_roles
DROP TABLE IF EXISTS `user_roles`;
CREATE TABLE IF NOT EXISTS `user_roles` (
  `user_id` int NOT NULL,
  `role_id` int NOT NULL,
  KEY `user_id_fkey` (`user_id`),
  CONSTRAINT `user_id_fkey` FOREIGN KEY (`user_id`) REFERENCES `customer` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Data exporting was unselected.

-- Dumping structure for table videogame_store.videogame
DROP TABLE IF EXISTS `videogame`;
CREATE TABLE IF NOT EXISTS `videogame` (
  `id` int NOT NULL AUTO_INCREMENT,
  `game_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `platform` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `price` float unsigned NOT NULL,
  `quantity` int unsigned NOT NULL,
  `release_date` date NOT NULL,
  `publisher` varchar(50) NOT NULL,
  `developer` varchar(50) NOT NULL,
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `game_photo` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `FK_videogame_platforms` (`platform`),
  CONSTRAINT `FK_videogame_platforms` FOREIGN KEY (`platform`) REFERENCES `platforms` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Data exporting was unselected.

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;

CREATE DATABASE `daaexample`;

CREATE TABLE `daaexample`.`people` (
	`id` int NOT NULL AUTO_INCREMENT,
	`name` varchar(50) NOT NULL,
	`surname` varchar(100) NOT NULL,
	PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `daaexample`.`users` (
	`login` varchar(100) NOT NULL,
	`password` varchar(64) NOT NULL,
	`role` varchar(10) NOT NULL,
	PRIMARY KEY (`login`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `daaexample`.`pet` (
	`id` int NOT NULL AUTO_INCREMENT,
	`name` varchar(50) NOT NULL,
	`owner` int NOT NULL,
	PRIMARY KEY (`id`),
	FOREIGN KEY (owner) REFERENCES people(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE USER 'daa'@'localhost' IDENTIFIED WITH mysql_native_password BY 'daa';
GRANT ALL ON `daaexample`.* TO 'daa'@'localhost';

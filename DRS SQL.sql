-- Create schema

DROP DATABASE IF EXISTS `disaster_response` ;

CREATE DATABASE IF NOT EXISTS `disaster_response`;

USE `disaster_response` ;

-- Create tables

CREATE TABLE IF NOT EXISTS `user_roles` (
  `role_id` int NOT NULL AUTO_INCREMENT,
  `role_name` varchar(100) NOT NULL,
  PRIMARY KEY (`role_id`)
);

CREATE TABLE IF NOT EXISTS `departments` (
  `department_id` int NOT NULL AUTO_INCREMENT,
  `department_name` varchar(100) NOT NULL,
  `description` text,
  PRIMARY KEY (`department_id`)
);

CREATE TABLE IF NOT EXISTS `users` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `user_role` int DEFAULT NULL,
  `department_id` int DEFAULT NULL,
  `created_by` int DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username` (`username`),
  KEY `user_role` (`user_role`),
  KEY `department_id` (`department_id`),
  KEY `created_by` (`created_by`),
  CONSTRAINT `users_ibfk_1` FOREIGN KEY (`user_role`) REFERENCES `user_roles` (`role_id`),
  CONSTRAINT `users_ibfk_2` FOREIGN KEY (`department_id`) REFERENCES `departments` (`department_id`),
  CONSTRAINT `users_ibfk_3` FOREIGN KEY (`created_by`) REFERENCES `users` (`user_id`) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS `disasters` (
  `id` int NOT NULL AUTO_INCREMENT,
  `type` varchar(100) DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `severity` int DEFAULT NULL,
  `description` text,
  `status` enum('Open','In Progress','Closed') DEFAULT 'Open',
  `reported_by` int DEFAULT NULL,
  `reported_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `reported_by` (`reported_by`),
  CONSTRAINT `disasters_ibfk_1` FOREIGN KEY (`reported_by`) REFERENCES `users` (`user_id`) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS `disaster_messages` (
  `id` int NOT NULL AUTO_INCREMENT,
  `disasterId` int NOT NULL,
  `departmentId` int NOT NULL,
  `message` text NOT NULL,
  `approval_status` tinyint(1) DEFAULT NULL,
  `messageTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `messagedBy` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `disasterId` (`disasterId`),
  KEY `departmentId` (`departmentId`),
  KEY `messagedBy` (`messagedBy`),
  CONSTRAINT `disaster_messages_ibfk_1` FOREIGN KEY (`disasterId`) REFERENCES `disasters` (`id`),
  CONSTRAINT `disaster_messages_ibfk_2` FOREIGN KEY (`departmentId`) REFERENCES `departments` (`department_id`),
  CONSTRAINT `disaster_messages_ibfk_3` FOREIGN KEY (`messagedBy`) REFERENCES `users` (`user_id`)
);

-- Insert data into tables

INSERT INTO 
		user_roles (role_name) 
VALUES 	
		('System Administrator'),
		('Public User'),
		('Department Administrator'),
		('Responder');

INSERT INTO 
		departments (department_name, description) 
VALUES 
		('Fire Department', 'Responsible for handling fire-related emergencies'),
        ('Police Department', 'Responsible for maintaining law and order during disasters'),
        ('Medical Services', 'Responsible for providing medical assistance and emergency care'),
        ('Public Works', 'Responsible for ensuring roadways, utilities, and infrastructure are managed during disasters'),
        ('Rescue Operations', 'Responsible for handling search and rescue during major disasters');
        
INSERT INTO 
		users (username, first_name, last_name, password, user_role, department_id, created_by)
VALUES 
		('admin', 'Pathum', 'Anthony', 'password', 1, NULL, NULL),
        ('public', 'James', 'Smith', 'abc', 2, NULL, 1),
        ('depadmin', 'Kevin', 'Silva', '123', 3, 1, 1),
        ('responder', 'Tracy', 'Doe', 'a1b2', 4, 1, 1);


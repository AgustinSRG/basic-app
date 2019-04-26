-- This script creates the tables for the Application database

-- Drop tables

DROP TABLE IF EXISTS `user_star`;
DROP TABLE IF EXISTS `session`;
DROP TABLE IF EXISTS `comment`;
DROP TABLE IF EXISTS `text_file`;
DROP TABLE IF EXISTS `user`;

-- Create tables

CREATE TABLE `user` (
    `uuid` varchar(255) NOT NULL PRIMARY KEY,
    `name` varchar(255),
    `password_hash` varchar(255),
    `password_salt` varchar(255),
    `password_algo` varchar(255),
    `register_date` datetime
);

CREATE TABLE `session` (
    `token` varchar(255) NOT NULL PRIMARY KEY,
    `user` varchar(255) NOT NULL,
    `expires` datetime NOT NULL
);

CREATE TABLE `text_file` (
    `identifier` varchar(255) NOT NULL PRIMARY KEY,
    `user` varchar(255) NOT NULL,
    `title` varchar(255) NOT NULL UNIQUE,
    `content` text NOT NULL,
    `creation_date` datetime NOT NULL,
    `last_edit_date` datetime
);

CREATE TABLE `comment` ( 
    `identifier` varchar(255) NOT NULL PRIMARY KEY,
    `text_file` varchar(255) NOT NULL,
    `user` varchar(255) NOT NULL,
    `date` datetime NOT NULL,
    `content` text NOT NULL
);

CREATE TABLE `user_star` (
    `user` varchar(255) NOT NULL,
    `text_file` varchar(255) NOT NULL,
    `star` datetime NOT NULL
);

-- Restrictions

ALTER TABLE `user_star` ADD UNIQUE `unique_user_star_index`(`user`, `text_file`);

ALTER TABLE `session`
    ADD CONSTRAINT `fk_session_user` FOREIGN KEY (`user`) REFERENCES `user` (`uuid`);

ALTER TABLE `text_file`
    ADD CONSTRAINT `text_file_user` FOREIGN KEY (`user`) REFERENCES `user` (`uuid`);

ALTER TABLE `comment`
    ADD CONSTRAINT `comment_user` FOREIGN KEY (`user`) REFERENCES `user` (`uuid`),
    ADD CONSTRAINT `comment_text_file` FOREIGN KEY (`text_file`) REFERENCES `text_file` (`identifier`);

ALTER TABLE `user_star`
    ADD CONSTRAINT `user_star_user` FOREIGN KEY (`user`) REFERENCES `user` (`uuid`),
    ADD CONSTRAINT `user_star_text_file` FOREIGN KEY (`text_file`) REFERENCES `text_file` (`identifier`);


drop database if exists ares;
create database ares;

use ares

drop table if exists service;
create table service(
	topic_struct	varchar(63)	NOT NULL,
	ID 		smallint	NOT NULL,
	name 		varchar(255)	NOT NULL,
	begin_date 	date		NOT NULL,
	end_date 	date		NOT NULL,
	budget 		numeric(12,2),
	comments 	varchar(255),
	PRIMARY KEY (ID)
	);

drop table if exists project;
create table project (
	ID		mediumint	NOT NULL	AUTO_INCREMENT,
	service_ID	smallint	NOT NULL,
	name		varchar(255)	NOT NULL,
	sponsor		varchar(255)	NOT NULL,
	defined		bit		NOT NULL	DEFAULT FALSE,
	approved	bit		NOT NULL	DEFAULT FALSE,
	visible		bit		NOT NULL	DEFAULT TRUE,
	PRIMARY KEY (ID)
	);

drop table if exists logistic;
create table logistic (
	ID 		mediumint	NOT NULL	AUTO_INCREMENT,
	service_ID	smallint	NOT NULL,
	name		varchar(255)	NOT NULL,
	sponsor		varchar(255)	NOT NULL,
	defined		bit		NOT NULL	DEFAULT FALSE,
	approved	bit		NOT NULL	DEFAULT FALSE,
	visible		bit		NOT NULL	DEFAULT TRUE,
	PRIMARY KEY (ID)
	);

drop table if exists material;
create table material (
	ID 		mediumint	NOT NULL	AUTO_INCREMENT,
	service_ID	smallint	NOT NULL,
	name		varchar(255)	NOT NULL,
	sponsor		varchar(255)	NOT NULL,
	requested	bit		NOT NULL	DEFAULT FALSE,
	inloco		bit		NOT NULL	DEFAULT FALSE,
	available	bit		NOT NULL	DEFAULT FALSE,
	visible		bit		NOT NULL	DEFAULT TRUE,
	PRIMARY KEY (ID)	 
	);

drop table if exists workman;
create table workman (
	ID		mediumint	NOT NULL	AUTO_INCREMENT,
	service_ID	smallint	NOT NULL,
	name		varchar(255)	NOT NULL,
	sponsor		varchar(255)	NOT NULL,
	available	bit		NOT NULL	DEFAULT FALSE,
	engaged		bit		NOT NULL	DEFAULT FALSE,
	visible		bit		NOT NULL	DEFAULT TRUE,
	PRIMARY KEY (ID)
	);


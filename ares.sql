drop database if exists ares;
create database ares;

/*use vernacula02;*/
use ares;

drop table if exists service;
create table service (
	topic_struct	varchar(63)		NOT NULL,
	ID 				int				NOT NULL,
	name 			varchar(255)	NOT NULL,
	begin_date 		date			NOT NULL,
	end_date 		date			NOT NULL,
	start_date		date,
	compl_date 		date,
	stop_date		date,
	budget 			numeric(12,2),
	comments 		varchar(255),
	started			bit				NOT NULL	DEFAULT FALSE,
	stopped			bit				NOT	NULL	DEFAULT FALSE,
	completed		bit				NOT NULL	DEFAULT FALSE,
	PRIMARY KEY (ID)
	);

drop table if exists restrictions;
create table restrictions (
	ID			int 				NOT NULL	AUTO_INCREMENT,
	service_ID	int					NOT	NULL,
	visible		bit					NOT NULL	DEFAULT TRUE,
	r_type		varchar(10),
	name		varchar(255) 		NOT NULL 	DEFAULT 'nome',
	sponsor		varchar(255) 		NOT NULL 	DEFAULT 'responsavel',
	defined		bit					NOT NULL	DEFAULT FALSE,
	approved	bit					NOT	NULL	DEFAULT FALSE,
	);

drop table if exists project;
create table project (
	ID		mediumint	NOT NULL	AUTO_INCREMENT,
	service_ID	int		NOT NULL,
	visible		bit		NOT NULL	DEFAULT TRUE,
	name		varchar(255)	NOT NULL,
	sponsor		varchar(255)	NOT NULL,
	defined		bit		NOT NULL	DEFAULT FALSE,
	approved	bit		NOT NULL	DEFAULT FALSE,
	PRIMARY KEY (ID)
	);

drop table if exists logistic;
create table logistic (
	ID 		mediumint	NOT NULL	AUTO_INCREMENT,
	service_ID	int		NOT NULL,
	visible		bit		NOT NULL	DEFAULT TRUE,
	name		varchar(255)	NOT NULL,
	sponsor		varchar(255)	NOT NULL,
	defined		bit		NOT NULL	DEFAULT FALSE,
	approved	bit		NOT NULL	DEFAULT FALSE,
	PRIMARY KEY (ID)
	);

drop table if exists material;
create table material (
	ID 		mediumint	NOT NULL	AUTO_INCREMENT,
	service_ID	int		NOT NULL,
	visible		bit		NOT NULL	DEFAULT TRUE,
	name		varchar(255)	NOT NULL,
	sponsor		varchar(255)	NOT NULL,
	requested	bit		NOT NULL	DEFAULT FALSE,
	inloco		bit		NOT NULL	DEFAULT FALSE,
	available	bit		NOT NULL	DEFAULT FALSE,
	PRIMARY KEY (ID)	 
	);

drop table if exists workman;
create table workman (
	ID		mediumint	NOT NULL	AUTO_INCREMENT,
	service_ID	int		NOT NULL,
	visible		bit		NOT NULL	DEFAULT TRUE,
	name		varchar(255)	NOT NULL,
	sponsor		varchar(255)	NOT NULL,
	available	bit		NOT NULL	DEFAULT FALSE,
	engaged		bit		NOT NULL	DEFAULT FALSE,
	PRIMARY KEY (ID)
	);


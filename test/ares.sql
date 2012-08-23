drop database if exists ares;
create database ares;

/*use vernacula02;*/
use ares;

drop table if exists service;

create table _user (
	ID 			int 		NOT NULL	AUTO_INCREMENT,
	username	varchar(31)	NOT NULL,
	password	varchar(15) NOT NULL,
	firstname	varchar(31) NOT NULL,
	lastname	varchar(31),
	email		varchar(63)	NOT NULL,
	worklist	
	PRIMARY KEY (ID)
);

create table _work (
	ID			int			NOT NULL	AUTO_INCREMENT,
	sponsor		int			NOT NULL,
	userlist	(?)			NOT NULL,
	office_ID	int			NOT NULL,
	PRIMARY KEY (ID),
	INDEX (office_ID),
	FOREIGN KEY (office_ID)
		REFERENCES office(ID)
		ON UPDATE CASCADE
		ON DELETE RESTRICT
);

create table office (
	ID			int 		NOT NULL	AUTO_INCREMENT,
	name		varchar(31)	NOT NULL,
	PRIMARY KEY (ID)
);

create table service (
	ID 			int		NOT NULL,
	topic_struct	varchar(63)	NOT NULL,
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
	service_ID	int		NOT NULL,
	name		varchar(255)	NOT NULL,
	sponsor		varchar(255)	NOT NULL,
	defined		bit		NOT NULL	DEFAULT FALSE,
	approved	bit		NOT NULL	DEFAULT FALSE,
	visible		bit		NOT NULL	DEFAULT TRUE,
	PRIMARY KEY (ID),
	INDEX (service_ID),
	FOREIGN KEY (service_ID)
		REFERENCES service(ID)
		ON UPDATE CASCADE
		ON DELETE RESTRICT
);

drop table if exists logistic;
create table logistic (
	ID 		mediumint	NOT NULL	AUTO_INCREMENT,
	service_ID	int		NOT NULL,
	name		varchar(255)	NOT NULL,
	sponsor		varchar(255)	NOT NULL,
	defined		bit		NOT NULL	DEFAULT FALSE,
	approved	bit		NOT NULL	DEFAULT FALSE,
	visible		bit		NOT NULL	DEFAULT TRUE,
	PRIMARY KEY (ID),
	INDEX (service_ID),
	FOREIGN KEY (service_ID)
		REFERENCES service(ID)
		ON UPDATE CASCADE
		ON DELETE RESTRICT
);

drop table if exists material;
create table material (
	ID 		mediumint	NOT NULL	AUTO_INCREMENT,
	service_ID	int		NOT NULL,
	name		varchar(255)	NOT NULL,
	sponsor		varchar(255)	NOT NULL,
	requested	bit		NOT NULL	DEFAULT FALSE,
	inloco		bit		NOT NULL	DEFAULT FALSE,
	available	bit		NOT NULL	DEFAULT FALSE,
	visible		bit		NOT NULL	DEFAULT TRUE,
	PRIMARY KEY (ID),
	INDEX (service_ID),
	FOREIGN KEY (service_ID)
		REFERENCES service(ID)
		ON UPDATE CASCADE
		ON DELETE RESTRICT
);

drop table if exists workman;
create table workman (
	ID		mediumint	NOT NULL	AUTO_INCREMENT,
	service_ID	int		NOT NULL,
	name		varchar(255)	NOT NULL,
	sponsor		varchar(255)	NOT NULL,
	available	bit		NOT NULL	DEFAULT FALSE,
	engaged		bit		NOT NULL	DEFAULT FALSE,
	visible		bit		NOT NULL	DEFAULT TRUE,
	PRIMARY KEY (ID),
	INDEX (service_ID),
	FOREIGN KEY (service_ID)
		REFERENCES service(ID)
		ON UPDATE CASCADE
		ON DELETE RESTRICT
);


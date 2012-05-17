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
	PRIMARY KEY (ID),
	FOREIGN KEY (service_ID)
	  REFERENCES service(ID)
	  	ON DELETE NO ACTION
	  	ON UPDATE NO ACTION
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
	PRIMARY KEY (ID),
	FOREIGN KEY (service_ID)
	  REFERENCES service(ID)
	  	ON DELETE NO ACTION
	  	ON UPDATE NO ACTION
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
	PRIMARY KEY (ID),
	FOREIGN KEY (service_ID)
	  REFERENCES service(ID)
	  	ON DELETE NO ACTION
	  	ON UPDATE NO ACTION	 
	);

drop table if exists workmanship;
create table workmanship (
	ID		mediumint	NOT NULL	AUTO_INCREMENT,
	service_ID	smallint	NOT NULL,
	name		varchar(255)	NOT NULL,
	sponsor		varchar(255)	NOT NULL,
	available	bit		NOT NULL	DEFAULT FALSE,
	engaged		bit		NOT NULL	DEFAULT FALSE,
	visible		bit		NOT NULL	DEFAULT TRUE,
	PRIMARY KEY (ID),
	FOREIGN KEY (service_ID)
	  REFERENCES service(ID)
	  	ON DELETE NO ACTION
	  	ON UPDATE NO ACTION
	);

INSERT INTO material (service_ID, name, sponsor)
	VALUES	(219,'material 1', 'Joao'),
		(12, 'material 2', 'Joao'),
		(12, 'material 3', 'Jose'),
		(12, 'material 4', 'Paulo'),
		(12, 'material 5', 'Antonio'),
		(219,'material 6', 'Jose'),
		(219,'material 7', 'Jose'),
		(219,'material 8', 'Antonio'),
		(219,'material 9', 'Antonio');

INSERT INTO workmanship (service_ID, name, sponsor)
	VALUES	(12, 'mão-de-obra 1', 'Joao'),
		(12, 'mão-de-obra 2', 'Joao'),
		(12, 'mão de obra 3', 'Joao'),
		(219,'mão de obra 4', 'Jose'),
		(219,'mão de obra 5', 'Antonio');

INSERT INTO project (service_ID, name, sponsor)
	VALUES	(12, 'projeto 1', 'Adriano'),
		(219,'projeto 2', 'Adriano');
	
INSERT INTO logisitc (service_ID, name, sponsor)
	VALUES	(12, 'Linha de base', 'Luis Henrique'),
		(219,'Linha de base', 'Luis Henrique');




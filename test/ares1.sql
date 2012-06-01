drop database if exists ares1;
create database ares1;

use ares1

drop table if exists service;
create table service(
service_ID 		smallint	NOT NULL,
service_name 		varchar(255)	NOT NULL,
/*
begin_date 		date		NOT NULL,
end_date 		date		NOT NULL,
*/
budget 			numeric(12,2),
comments 		blob,

PRIMARY KEY (service_ID)
);

drop table if exists project;
create table project (
project_ID		mediumint	NOT NULL	AUTO_INCREMENT,
service_ID		smallint	NOT NULL,
project_name		varchar(255)	NOT NULL,
project_sponsor		varchar(255)	NOT NULL,
project_defined		enum('y','n')	NOT NULL	DEFAULT 'n',
project_approved	enum('y','n')	NOT NULL	DEFAULT 'n',
project_visible		enum('y','n')	NOT NULL	DEFAULT 'y',
PRIMARY KEY (project_ID)
);

drop table if exists logistics;
create table logistics (
logistic_ID 		mediumint	NOT NULL	AUTO_INCREMENT,
service_ID		smallint	NOT NULL,
logistic_name		varchar(255)	NOT NULL,
logistic_sponsor	varchar(255)	NOT NULL,
logistic_defined	enum('y','n')	NOT NULL	DEFAULT 'n',
logistic_approved	enum('y','n')	NOT NULL	DEFAULT 'n',
logistic_visible	enum('y','n')	NOT NULL	DEFAULT 'y',
PRIMARY KEY (logistic_ID)
);

drop table if exists material;
create table material (
material_ID 		mediumint	NOT NULL	AUTO_INCREMENT,
service_ID		smallint	NOT NULL,
material_name		varchar(255)	NOT NULL,
material_sponsor	varchar(255)	NOT NULL,
material_requested	enum('y','n')	NOT NULL	DEFAULT 'n',
material_inloco		enum('y','n')	NOT NULL	DEFAULT 'n',
material_available	enum('y','n')	NOT NULL	DEFAULT 'n',
material_visible	enum('y','n')	NOT NULL	DEFAULT 'y',
PRIMARY KEY (material_ID)	 
);

drop table if exists workmanship;
create table workmanship (
manpower_ID		mediumint	NOT NULL	AUTO_INCREMENT,
service_ID		smallint	NOT NULL,
manpower_name		varchar(255)	NOT NULL,
manpower_sponsor	varchar(255)	NOT NULL,
manpower_available	enum('y','n')	NOT NULL	DEFAULT 'n',
manpower_engaged	enum('y','n')	NOT NULL	DEFAULT 'n',
manpower__visible	enum('y','n')	NOT NULL	DEFAULT 'y',
PRIMARY KEY (manpower_ID)
);

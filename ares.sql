drop database if exists ares;
create database ares;

/*use vernacula02;*/
use ares;

create table usuario(
	ID			int 		NOT NULL 	AUTO_INCREMENT,
	name			varchar(60) 	NOT NULL,
	u_type			int 		NOT NULL 	DEFAULT 1,
	builds_access		varchar(255)	NOT NULL,
	company_ID		int		NOT NULL,
	email			varchar(40)	NOT NULL,
	PRIMARY KEY (ID)
	);

create table company(
	ID			int		NOT NULL	AUTO_INCREMENT,
	name			varchar(30)	NOT NULL,
	address			varchar(255),
	phone			char(13),
	email			varchar(40)	NOT NULL,
	city			varchar(30)	NOT NULL,
	state			char(2)		NOT NULL,
	PRIMARY KEY (ID)
	);
}

create table buid(
	ID                      int             NOT NULL        AUTO_INCREMENT,
        name                    varchar(30)     NOT NULL,
	respons_ID		int		NOT NULL,
        address                 varchar(255),
        city                    varchar(30)     NOT NULL,
        state                   char(2)         NOT NULL,
	user_access		varchar(255)	NOT NULL,
        PRIMARY KEY (ID)
        );


drop table if exists service;
create table service (
	topic_struct		varchar(63)		NOT NULL,
	ID 			int			NOT NULL,
	name 			varchar(255)		NOT NULL,
	base_begin		date			NOT NULL,
	base_end		date			NOT NULL,
	begin_date 		date			NOT NULL,
	end_date 		date			NOT NULL,
	real_begin		date,
	real_end		date,
	start_date		date,
	compl_date 		date,
	stop_date		date,
	budget 			numeric(12,2),
	comments 		varchar(255),
	started			bit			NOT NULL	DEFAULT FALSE,
	stopped			bit			NOT NULL	DEFAULT FALSE,
	completed		bit			NOT NULL	DEFAULT FALSE,
	PRIMARY KEY (ID)
	);

drop table if exists restrictions;
create table restrictions (
	ID		int	 			NOT NULL	AUTO_INCREMENT,
	service_ID	int				NOT NULL,
	visible		bit				NOT NULL	DEFAULT TRUE,
	r_type		varchar(10),
	name		varchar(255) 			NOT NULL 	DEFAULT 'nome',
	sponsor		varchar(255) 			NOT NULL 	DEFAULT 'responsavel',
	defined		bit				NOT NULL	DEFAULT FALSE,
	approved	bit				NOT	NULL	DEFAULT FALSE,
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


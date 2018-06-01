# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table tb_function (
  id                        integer auto_increment not null,
  name                      varchar(255),
  type_name                 varchar(255),
  url                       TEXT,
  definition                LONGTEXT,
  regist_datetime           datetime,
  constraint pk_tb_function primary key (id))
;

create table tb_task (
  id                        integer auto_increment not null,
  regist_datetime           datetime,
  workflow_id               integer,
  constraint pk_tb_task primary key (id))
;

create table tb_upload_file (
  id                        integer auto_increment not null,
  disp_name                 varchar(255),
  type_name                 varchar(255),
  file_name                 varchar(255),
  data_text                 LONGTEXT,
  regist_datetime           datetime,
  constraint pk_tb_upload_file primary key (id))
;

create table tb_work (
  id                        integer auto_increment not null,
  workflow_id               integer,
  task                      varchar(255),
  status                    varchar(255),
  type                      varchar(255),
  sort_no                   integer,
  parent_id                 integer,
  start_datetime            datetime,
  end_datetime              datetime,
  constraint pk_tb_work primary key (id))
;

create table tb_work_param (
  id                        integer auto_increment not null,
  work_id                   integer,
  param_no                  integer,
  param_str                 varchar(255),
  param_text                LONGTEXT,
  constraint pk_tb_work_param primary key (id))
;

create table tb_workflow (
  id                        integer auto_increment not null,
  status                    varchar(255),
  start_datetime            datetime,
  end_datetime              datetime,
  workflow_xml              text not null,
  last_update               datetime not null,
  constraint pk_tb_workflow primary key (id))
;




# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table tb_function;

drop table tb_task;

drop table tb_upload_file;

drop table tb_work;

drop table tb_work_param;

drop table tb_workflow;

SET FOREIGN_KEY_CHECKS=1;


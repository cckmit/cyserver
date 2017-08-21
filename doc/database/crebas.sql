/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2017/1/6 16:06:22                            */
/*==============================================================*/


drop table if exists CDIO_EXPERIMENT_MEMBER;

/*==============================================================*/
/* Table: CDIO_EXPERIMENT_MEMBER                                */
/*==============================================================*/
create table CDIO_EXPERIMENT_MEMBER
(
   ID                   varchar(36) not null comment 'ID',
   TASK_ID              varchar(36) comment '任务ID',
   MEMBER_ID            varchar(36) comment '成员ID',
   MEMBER_NAME          varchar(100) comment '成员姓名',
   DEPARTMENT_NAME      varchar(200) comment '院系',
   CLASS_NAME           varchar(200) comment '班级',
   USER_CODE            varchar(36) comment '学号',
   primary key (ID)
);

alter table CDIO_EXPERIMENT_MEMBER comment '实验任务成员';


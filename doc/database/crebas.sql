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
   TASK_ID              varchar(36) comment '����ID',
   MEMBER_ID            varchar(36) comment '��ԱID',
   MEMBER_NAME          varchar(100) comment '��Ա����',
   DEPARTMENT_NAME      varchar(200) comment 'Ժϵ',
   CLASS_NAME           varchar(200) comment '�༶',
   USER_CODE            varchar(36) comment 'ѧ��',
   primary key (ID)
);

alter table CDIO_EXPERIMENT_MEMBER comment 'ʵ�������Ա';


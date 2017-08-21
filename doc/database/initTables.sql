/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2016/9/30 16:14:51                           */
/*==============================================================*/


drop table if exists CDIO_ATTACHMENT;

DROP TABLE IF EXISTS `CDIO_BASIC_EXPERI_MEMBER`;

DROP TABLE IF EXISTS `CDIO_BASIC_EXPERI`;

drop table if exists CDIO_DATA_AUTHORITY;

drop table if exists CDIO_DATA_DIRECTORY;

drop table if exists CDIO_DATA_FILE;

drop table if exists CDIO_DEPARTMENT;

drop table if exists CDIO_PROJECT;

drop table if exists CDIO_PROJECT_MEMBER;

drop table if exists CDIO_PROJECT_TEACHER;

drop table if exists CDIO_TASK;

drop table if exists CDIO_TASK_MEMBER;

DROP TABLE IF EXISTS CDIO_TASK_SCORE;

drop table if exists CDIO_TASK_TEACHER;

drop table if exists CDIO_TASK_TEMPLATE;

drop table if exists CDIO_TASK_TEMPLATE_TYPE;

drop table if exists PUB_ACCOUNT;

drop table if exists PUB_CLASS;

drop table if exists PUB_CODE_GENERATE;

drop table if exists PUB_CODE_GENERATE_DETAIL;

drop table if exists PUB_CONFIG;

drop table if exists PUB_DEPARTMENT;

drop table if exists PUB_EXCEL_IMPORT_COLUMN;

drop table if exists PUB_EXCEL_IMPORT_TEMPLATE;

drop table if exists PUB_FILE;

drop table if exists PUB_MAJOR;

drop table if exists PUB_MEMBER;

drop table if exists PUB_MENU;

drop table if exists PUB_MENU_R_ROLE;

drop table if exists PUB_OPERATION;

drop table if exists PUB_ROLE;

drop table if exists PUB_ROLE_T_OPERATION;

drop table if exists PUB_THIRD_AUTH;

drop table if exists PUB_USER;

drop table if exists PUB_USERLOG;

drop table if exists PUB_USERLOG_CONFIG;

drop table if exists PUB_USER_R_ROLE;

/*==============================================================*/
/* Table: CDIO_ATTACHMENT                                       */
/*==============================================================*/
create table CDIO_ATTACHMENT
(
   ATTACHMENT_ID        varchar(36) not null comment '附件ID',
   TASK_ID              varchar(36) comment '任务ID',
   OWNER                varchar(36) comment '上传人',
   UPLOAD_TIME          datetime comment '上传时间',
   primary key (ATTACHMENT_ID)
);

alter table CDIO_ATTACHMENT comment '附件';

CREATE TABLE `CDIO_BASIC_EXPERI_MEMBER` (
   `ID` varchar(36) NOT NULL COMMENT 'ID',
   `BASIC_EXPERI_ID` varchar(36) DEFAULT NULL COMMENT '基础实验ID',
   `MEMBER_ID` varchar(36) DEFAULT NULL COMMENT '成员ID',
   `MEMBER_NAME` varchar(100) DEFAULT NULL COMMENT '成员姓名',
   `DEPARTMENT` varchar(200) DEFAULT NULL COMMENT '院系',
   `CLASS_NAME` varchar(100) DEFAULT NULL COMMENT '班级',
   PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='基础实验任务成员';


CREATE TABLE `CDIO_BASIC_EXPERI` (
   `BASIC_EXPERI_ID` varchar(36) NOT NULL COMMENT '基础实验ID',
   `BE_RESOURCE_CENTER_ID` varchar(36) DEFAULT NULL COMMENT '基础实验资源中心ID',
   `COURSE_NAME` varchar(255) DEFAULT NULL COMMENT '课程名称',
   `EXPERI_NAME` varchar(255) DEFAULT NULL COMMENT '实验名称',
   `EXPERI_CODE` varchar(255) DEFAULT NULL,
   `PUBLISH_TIME` datetime DEFAULT NULL COMMENT '发布时间',
   `DESCRIBE` varchar(255) DEFAULT NULL COMMENT '描述',
   `STATE` int(4) DEFAULT NULL,
   `COUNSELOR_ID` varchar(36) DEFAULT NULL,
   PRIMARY KEY (`BASIC_EXPERI_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


/*==============================================================*/
/* Table: CDIO_DATA_AUTHORITY                                   */
/*==============================================================*/
create table CDIO_DATA_AUTHORITY
(
   DATA_AUTHORITY_ID    varchar(36) not null comment '权限ID',
   STATUS               numeric(5,0) comment '状态',
   CREATE_TIME          datetime comment '创建时间',
   UPDATE_TIME          datetime comment '修改时间',
   SORT_NO              numeric(10,0) comment '排序',
   FILE_ID              varchar(36) comment '资料ID',
   FILE_NAME            varchar(200) comment '资料名称',
   primary key (DATA_AUTHORITY_ID)
);

alter table CDIO_DATA_AUTHORITY comment 'CDIO资料权限';

/*==============================================================*/
/* Table: CDIO_DATA_DIRECTORY                                   */
/*==============================================================*/
create table CDIO_DATA_DIRECTORY
(
   DIRECTORY_ID         varchar(36) not null comment '目录ID',
   STATUS               numeric(5,0) comment '状态',
   CREATE_TIME          datetime comment '创建时间',
   UPDATE_TIME          datetime comment '修改时间',
   SORT_NO              numeric(10,0) comment '排序',
   DIRECTORY_NAME       varchar(200) comment '目录名称',
   CREATER              varchar(36) comment '创建人',
   DIRECTORY_CODE       varchar(500) comment '目录编码',
   PDIRECTORY_ID        varchar(36) comment '父级节点',
   ISLEAF               numeric(5,0) comment '是否叶子节点',
   primary key (DIRECTORY_ID)
);

alter table CDIO_DATA_DIRECTORY comment 'CDIO资料目录';

/*==============================================================*/
/* Table: CDIO_DATA_FILE                                        */
/*==============================================================*/
create table CDIO_DATA_FILE
(
   FILE_ID              varchar(36) not null comment '资料ID',
   DIRECTORY_ID         varchar(36) comment '目录ID',
   STATUS               numeric(5,0) comment '状态',
   CREATE_TIME          datetime comment '创建时间',
   UPDATE_TIME          datetime comment '修改时间',
   SORT_NO              numeric(10,0) comment '排序',
   ATTACHMENT_ID        varchar(36) comment '附件ID',
   FILE_NAME            varchar(200) comment '资料名称',
   FILE_TYPE            numeric(5,0) comment '资料类型',
   CREATER              varchar(36) comment '创建人',
   MODIFIER             varchar(36) comment '修改人',
   REMARK               varchar(500) comment '备注',
   primary key (FILE_ID)
);

alter table CDIO_DATA_FILE comment 'CDIO资料';

/*==============================================================*/
/* Table: CDIO_DEPARTMENT                                       */
/*==============================================================*/
create table CDIO_DEPARTMENT
(
   DEPARTMENT_ID        varchar(36) not null comment '日志配置ID',
   STATUS               numeric(5,0) comment '状态',
   CREATE_TIME          datetime comment '创建时间',
   UPDATE_TIME          datetime comment '修改时间',
   SORT_NO              numeric(10,0) comment '排序',
   DEPARTMENT_NAME      varchar(200) comment '日志配置标志',
   REMARK               varchar(500) comment '描述',
   primary key (DEPARTMENT_ID)
);

alter table CDIO_DEPARTMENT comment '院系';

/*==============================================================*/
/* Table: CDIO_PROJECT                                          */
/*==============================================================*/
create table CDIO_PROJECT
(
   PROJECT_ID           varchar(36) not null comment '项目ID',
   TASK_TEMPLATE_TYPE_ID varchar(36) comment '任务模版ID',
   PROJECT_NAME         varchar(200) comment '项目名称',
   PROJECT_CODE         varchar(100) comment '项目编码',
   PUBLISH_DATE         datetime comment '发布日期',
   STATE                numeric(2,0) comment '状态',
   AIM                  varchar(600) comment '项目目标',
   DESCRIPTION          varchar(1000) comment '项目描述',
   REMARK               varchar(1000) comment '备注',
   primary key (PROJECT_ID)
);

alter table CDIO_PROJECT comment 'CDIO项目';

/*==============================================================*/
/* Table: CDIO_PROJECT_MEMBER                                   */
/*==============================================================*/
create table CDIO_PROJECT_MEMBER
(
   ID                   varchar(36) not null comment 'ID',
   PROJECT_ID           varchar(36) comment '项目ID',
   MEMBER_ID            varchar(36) comment '成员ID',
   MEMBER_NAME          varchar(100) comment '成员姓名',
   DEPARTMENT           varchar(200) comment '院系',
   CLASS_NAME           varchar(100) comment '班级',
   primary key (ID)
);

alter table CDIO_PROJECT_MEMBER comment '项目成员';

/*==============================================================*/
/* Table: CDIO_PROJECT_TEACHER                                  */
/*==============================================================*/
create table CDIO_PROJECT_TEACHER
(
   ID                   varchar(36) not null comment 'ID',
   PROJECT_ID           varchar(36) comment '项目ID',
   TEACHER_ID           varchar(36) comment '老师ID',
   TEACHER_NAME         varchar(100) comment '指导老师姓名',
   DEPARTMENT           varchar(200) comment '院系',
   primary key (ID)
);

alter table CDIO_PROJECT_TEACHER comment '项目指导老师';

/*==============================================================*/
/* Table: CDIO_TASK                                             */
/*==============================================================*/
create table CDIO_TASK
(
   TASK_ID              varchar(36) not null comment '任务ID',
   TEMPLATE_ID          varchar(36) comment '模版ID',
   CDI_TASK_ID          varchar(36) comment '任务ID',
   PROJECT_ID           varchar(36) comment '项目ID',
   TASK_NAME            varchar(200) comment '任务名称',
   TASK_CODE            varchar(100) comment '任务编号',
   TASK_DES             varchar(1000) comment '任务描述',
   PUBLISH_DATE         datetime comment '发布日期',
   LEADER               varchar(36) comment '组长',
   LEADER_NAME          varchar(100) comment '组长姓名',
   HAS_CHILDREN         numeric(2,0) comment '是否包含子任务',
   PARENT_ID            varchar(36) comment '父任务ID',
   STATUS               numeric(10,0) comment '状态',
   REMARK               varchar(1000) comment '备注',
   SORT_NO              numeric(10,0) comment '排序',
   primary key (TASK_ID)
);

alter table CDIO_TASK comment 'CDIO任务';

/*==============================================================*/
/* Table: CDIO_TASK_MEMBER                                      */
/*==============================================================*/
create table CDIO_TASK_MEMBER
(
   ID                   varchar(36) not null comment 'ID',
   TASK_ID              varchar(36) comment '任务ID',
   MEMBER_ID            varchar(36) comment '成员ID',
   MEMBER_NAME          varchar(100) comment '成员姓名',
   DEPARTMENT           varchar(200) comment '院系',
   CLASS_NAME           varchar(100) comment '班级',
   primary key (ID)
);

alter table CDIO_TASK_MEMBER comment '任务成员';

/*==============================================================*/
/* Table: CDIO_TASK_SCORE                                     */
/*==============================================================*/
CREATE TABLE `CDIO_TASK_SCORE` (
   `ID` varchar(36) not null comment 'ID',
   `TASK_ID` varchar(36) comment '任务ID',
   `JUDGES_ID` varchar(36) comment '评委ID',
   `MEMBER_ID` varchar(36) comment '成员ID',
   `SCORE` decimal(4,0) comment '得分',
   `UPDATE_TIME` datetime comment '更新时间',
   PRIMARY KEY (`ID`)
);

alter table CDIO_TASK_SCORE comment '任务得分';

/*==============================================================*/
/* Table: CDIO_TASK_TEACHER                                     */
/*==============================================================*/
create table CDIO_TASK_TEACHER
(
   ID                   varchar(36) not null comment 'ID',
   TASK_ID              varchar(36) comment '任务ID',
   TEACHER_ID           varchar(36) comment '老师ID',
   TEACHER_NAME         varchar(100) comment '指导老师姓名',
   DEPARTMENT           varchar(200) comment '院系',
   primary key (ID)
);

alter table CDIO_TASK_TEACHER comment '任务指导老师';

/*==============================================================*/
/* Table: CDIO_TASK_TEMPLATE                                    */
/*==============================================================*/
create table CDIO_TASK_TEMPLATE
(
   TEMPLATE_ID          varchar(36) not null comment '模版ID',
   TASK_TEMPLATE_TYPE_ID varchar(36) comment '任务模版ID',
   TASK_NAME            varchar(200) comment '任务名称',
   TASK_DES             varchar(1000) comment '任务描述',
   HAS_CHILDREN         numeric(2,0) comment '是否包含子任务',
   REMARK               varchar(1000) comment '备注',
   SORT_NO              numeric(10,0) comment '排序',
   primary key (TEMPLATE_ID)
);

alter table CDIO_TASK_TEMPLATE comment 'CDIO任务模版';

/*==============================================================*/
/* Table: CDIO_TASK_TEMPLATE_TYPE                               */
/*==============================================================*/
create table CDIO_TASK_TEMPLATE_TYPE
(
   TASK_TEMPLATE_TYPE_ID varchar(36) not null comment '任务模版ID',
   TASK_TEMPLATE_TYPE_NAME varchar(100) comment '任务模版名称',
   REMARK               varchar(500) comment '备注',
   primary key (TASK_TEMPLATE_TYPE_ID)
);

alter table CDIO_TASK_TEMPLATE_TYPE comment '任务模版类型';

/*==============================================================*/
/* Table: PUB_ACCOUNT                                           */
/*==============================================================*/
create table PUB_ACCOUNT
(
   USER_ACCOUNT         varchar(200) not null comment '账号',
   USER_ID              varchar(36) comment '用户ID',
   STATUS               numeric(5,0) comment '状态',
   CREATE_TIME          datetime comment '创建时间',
   UPDATE_TIME          datetime comment '修改时间',
   SORT_NO              numeric(10,0) comment '排序',
   PWD                  varchar(64) comment '密码',
   primary key (USER_ACCOUNT)
);

alter table PUB_ACCOUNT comment '用户账号信息';

/*==============================================================*/
/* Table: PUB_CLASS                                             */
/*==============================================================*/
create table PUB_CLASS
(
   MAJOR_ID             varchar(36) not null comment '日志ID',
   CDI_DEPARTMENT_ID    varchar(36) comment '日志配置ID',
   STATUS               numeric(5,0) comment '状态',
   CREATE_TIME          datetime comment '创建时间',
   UPDATE_TIME          datetime comment '修改时间',
   SORT_NO              numeric(10,0) comment '排序',
   MAJOR_NAME           varchar(100) comment '操作',
   REMARK               varchar(500) comment '日志内容',
   DEPARTMENT_ID        varchar(36) comment '操作用户ID',
   primary key (MAJOR_ID)
);

alter table PUB_CLASS comment '班级';

/*==============================================================*/
/* Table: PUB_CODE_GENERATE                                     */
/*==============================================================*/
create table PUB_CODE_GENERATE
(
   ID                   varchar(36) not null comment '配置项ID',
   BUSINESS_KEY         varchar(50) comment '业务类型',
   GENERATE_TYPE        numeric(5,0) comment '生成策略
            1：系统内共享
            2：机构内共享
            3：用户共享
            4：系统内按年共享
            5：系统内按月共享
            6：系统内按周共享
            7：系统内按日共享
            8：机构内按年共享
            9：机构内按月共享
            10：机构内按日共享
            11：用户按年共享
            12：用户按月共享
            13：用户按周共享
            14：用户按日共享',
   LAST_GENERATE_TIME   datetime comment '最后编码生成时间',
   RULE                 varchar(100) comment '序号规则',
   LENGTH               numeric(10,0) comment '序号长度',
   NO                   numeric(10,0) comment '当前序号',
   primary key (ID)
);

alter table PUB_CODE_GENERATE comment '编码生成组件';

/*==============================================================*/
/* Table: PUB_CODE_GENERATE_DETAIL                              */
/*==============================================================*/
create table PUB_CODE_GENERATE_DETAIL
(
   ID                   varchar(36) not null comment '配置项ID',
   PUB_ID               varchar(36) comment '配置项ID',
   BUSINESS_ID          varchar(50) comment '业务表主键',
   NO                   numeric(10,0) comment '当前序号',
   LAST_GENERATE_TIME   datetime comment '最后编码生成时间',
   primary key (ID)
);

alter table PUB_CODE_GENERATE_DETAIL comment '编码生成组件明细规则';

/*==============================================================*/
/* Table: PUB_CONFIG                                            */
/*==============================================================*/
create table PUB_CONFIG
(
   ID                   varchar(36) not null comment '配置项ID',
   STATUS               numeric(5,0) comment '状态',
   CREATE_TIME          datetime comment '创建时间',
   UPDATE_TIME          datetime comment '修改时间',
   COM_SORT_NO          numeric(10,0) comment '排序',
   PARENT_ID            varchar(36) comment '父节点ID',
   CODE                 varchar(50) comment '配置项编码',
   NAME                 varchar(100) comment '配置项名称',
   FULL_CODE            varchar(200) comment '配置项全编码',
   DESCRIPTION          varchar(500) comment '配置描述',
   SORT_NO              numeric(10,0) comment '排序',
   FULL_NAME            varchar(500) comment '配置项全名称',
   CONFIG_KEY           varchar(200) comment '配置项键',
   CONFIG_VALUE         varchar(100) comment '配置项值',
   LEAF_MARK            numeric(1,0) comment '是否叶子节点',
   primary key (ID)
);

alter table PUB_CONFIG comment '配置管理';

/*==============================================================*/
/* Table: PUB_DEPARTMENT                                        */
/*==============================================================*/
create table PUB_DEPARTMENT
(
   DEPARTMENT_ID        varchar(36) not null comment '部门ID',
   STATUS               numeric(5,0) comment '状态',
   CREATE_TIME          datetime comment '创建时间',
   UPDATE_TIME          datetime comment '修改时间',
   SORT_NO              numeric(10,0) comment '排序',
   PARENT_ID            varchar(36) comment '父级ID',
   DEPARTMENT_NAME      varchar(200) comment '部门名称',
   FULL_NAME            varchar(1000) comment '部门全名',
   CODE                 varchar(50) comment '部门CODE',
   FULL_CODE            varchar(400) comment '部门全CODE',
   LEVEL                numeric(10,0) comment '层级',
   TYPE                 varchar(20) comment '用户类型',
   REMARK               varchar(500) comment '功能说明',
   primary key (DEPARTMENT_ID)
);

alter table PUB_DEPARTMENT comment '部门';

/*==============================================================*/
/* Table: PUB_EXCEL_IMPORT_COLUMN                               */
/*==============================================================*/
create table PUB_EXCEL_IMPORT_COLUMN
(
   EXCEL_IMPORT_COLUMN_ID varchar(36) not null comment 'EXCEL导入模板列ID',
   EXCEL_IMPORT_TEMPLATE_ID varchar(36) comment 'EXCEL导入模板ID',
   COLUMN_NAME          varchar(50) comment '列名',
   FIELD_NAME           varchar(50) comment '字段名',
   RELATION_FIELD_NAME  varchar(50) comment '关联字段名',
   TYPE                 varchar(10) comment '类型',
   LENGTH               numeric(10,0) comment '序号长度',
   primary key (EXCEL_IMPORT_COLUMN_ID)
);

alter table PUB_EXCEL_IMPORT_COLUMN comment 'EXCEL导入详细信息表';

/*==============================================================*/
/* Table: PUB_EXCEL_IMPORT_TEMPLATE                             */
/*==============================================================*/
create table PUB_EXCEL_IMPORT_TEMPLATE
(
   EXCEL_IMPORT_TEMPLATE_ID varchar(36) not null comment 'EXCEL导入模板ID',
   TABLE_NAME           varchar(50) comment '表名',
   TEMPLATE_NAME        varchar(50) comment '模板名',
   CREATE_TIME          datetime comment '创建时间',
   RULE                 varchar(100) comment '序号规则',
   primary key (EXCEL_IMPORT_TEMPLATE_ID)
);

alter table PUB_EXCEL_IMPORT_TEMPLATE comment 'EXCEL导入';

/*==============================================================*/
/* Table: PUB_FILE                                              */
/*==============================================================*/
create table PUB_FILE
(
   FILE_ID              varchar(36) not null comment '日志配置ID',
   STATUS               numeric(5,0) comment '状态',
   CREATE_TIME          datetime comment '创建时间',
   UPDATE_TIME          datetime comment '修改时间',
   SORT_NO              numeric(10,0) comment '排序',
   FILE_NAME            varchar(200) comment '日志配置标志',
   FILE_TYPE            varchar(50) comment '操作',
   FILE_SIZE            numeric(20,0) comment '描述',
   DISC_PATH            varchar(1000) comment '日志配置状态',
   NET_PATH             varchar(1000) comment '系统标识',
   CREATER              varchar(36) comment '成功信息',
   primary key (FILE_ID)
);

alter table PUB_FILE comment '上传文件';

/*==============================================================*/
/* Table: PUB_MAJOR                                             */
/*==============================================================*/
create table PUB_MAJOR
(
   MAJOR_ID             varchar(36) not null comment '日志ID',
   STATUS               numeric(5,0) comment '状态',
   CREATE_TIME          datetime comment '创建时间',
   UPDATE_TIME          datetime comment '修改时间',
   SORT_NO              numeric(10,0) comment '排序',
   MAJOR_NAME           varchar(100) comment '操作',
   REMARK               varchar(500) comment '日志内容',
   primary key (MAJOR_ID)
);

alter table PUB_MAJOR comment '专业';

/*==============================================================*/
/* Table: PUB_MEMBER                                            */
/*==============================================================*/
create table PUB_MEMBER
(
   MEMBER_ID            varchar(36) not null comment '成员信息ID',
   PUB_USER_ID          varchar(36) not null comment '用户ID',
   STATUS               numeric(5,0) comment '状态',
   CREATE_TIME          datetime comment '创建时间',
   UPDATE_TIME          datetime comment '修改时间',
   SORT_NO              numeric(10,0) comment '排序',
   USER_ID              varchar(36) comment '用户ID',
   DEPT_ID              varchar(36) comment '院系ID',
   CLASS_ID             varchar(36) comment '班级ID',
   SUBJECT_ID           varchar(36) comment '专业ID',
   ACADEMIC_YEAR        varchar(10) comment '学年',
   primary key (MEMBER_ID)
);

alter table PUB_MEMBER comment '成员信息';

/*==============================================================*/
/* Table: PUB_MENU                                              */
/*==============================================================*/
create table PUB_MENU
(
   MENU_ID              varchar(36) not null comment '菜单ID',
   STATUS               numeric(5,0) comment '状态',
   CREATE_TIME          datetime comment '创建时间',
   UPDATE_TIME          datetime comment '修改时间',
   SORT_NO              numeric(10,0) comment '排序',
   MENU_NAME            varchar(200) comment '菜单名称',
   P_MENU_ID            varchar(36) comment '父级ID',
   URL                  varchar(500) comment 'URL',
   IS_DIR               numeric(1,0) comment '是否目录',
   primary key (MENU_ID)
);

alter table PUB_MENU comment '菜单';

/*==============================================================*/
/* Table: PUB_MENU_R_ROLE                                       */
/*==============================================================*/
create table PUB_MENU_R_ROLE
(
   ROLE_ID              varchar(36) not null comment '角色ID',
   MENU_ID              varchar(36) not null comment '菜单ID',
   primary key (ROLE_ID, MENU_ID)
);

alter table PUB_MENU_R_ROLE comment '菜单角色关系';

/*==============================================================*/
/* Table: PUB_OPERATION                                         */
/*==============================================================*/
create table PUB_OPERATION
(
   OPERATION_ID         varchar(36) not null comment '功能ID',
   MENU_ID              varchar(36) comment '菜单ID',
   STATUS               numeric(5,0) comment '状态',
   CREATE_TIME          datetime comment '创建时间',
   UPDATE_TIME          datetime comment '修改时间',
   SORT_NO              numeric(10,0) comment '排序',
   OPERATION_NAME       varchar(200) comment '功能名称',
   OPERATION_CODE       varchar(100) comment '功能编码',
   REMARK               varchar(500) comment '功能说明',
   primary key (OPERATION_ID)
);

alter table PUB_OPERATION comment '菜单功能表';

/*==============================================================*/
/* Table: PUB_ROLE                                              */
/*==============================================================*/
create table PUB_ROLE
(
   ROLE_ID              varchar(36) not null comment '角色ID',
   STATUS               numeric(5,0) comment '状态',
   CREATE_TIME          datetime comment '创建时间',
   UPDATE_TIME          datetime comment '修改时间',
   SORT_NO              numeric(10,0) comment '排序',
   ROLE_NAME            varchar(200) comment '角色名',
   REMARK               varchar(500) comment '角色描述',
   primary key (ROLE_ID)
);

alter table PUB_ROLE comment '角色信息';

/*==============================================================*/
/* Table: PUB_ROLE_T_OPERATION                                  */
/*==============================================================*/
create table PUB_ROLE_T_OPERATION
(
   ROLE_ID              varchar(36) not null comment '角色ID',
   OPERATION_ID         varchar(36) not null comment '功能ID',
   primary key (ROLE_ID, OPERATION_ID)
);

alter table PUB_ROLE_T_OPERATION comment '角色菜单功能关系';

/*==============================================================*/
/* Table: PUB_THIRD_AUTH                                        */
/*==============================================================*/
create table PUB_THIRD_AUTH
(
   THIRD_AUTH_ID        varchar(36) not null comment '认证ID',
   PUB_USER_ID          varchar(36) not null comment '用户ID',
   STATUS               numeric(5,0) comment '状态',
   CREATE_TIME          datetime comment '创建时间',
   UPDATE_TIME          datetime comment '修改时间',
   SORT_NO              numeric(10,0) comment '排序',
   AUTH_TYPE            varchar(20) comment '认证类型 QQ WX 等',
   USER_ID              varchar(36) comment '用户ID',
   OPEN_ID              varchar(50) comment '认证ID',
   primary key (THIRD_AUTH_ID)
);

alter table PUB_THIRD_AUTH comment '第三方认证';

/*==============================================================*/
/* Table: PUB_USER                                              */
/*==============================================================*/
create table PUB_USER
(
   USER_ID              varchar(36) not null comment '用户ID',
   MEMBER_ID            varchar(36) comment '成员信息ID',
   USER_ACCOUNT         varchar(200) comment '账号',
   DEPARTMENT_ID        varchar(36) comment '部门ID',
   STATUS               numeric(5,0) comment '状态',
   CREATE_TIME          datetime comment '创建时间',
   UPDATE_TIME          datetime comment '修改时间',
   SORT_NO              numeric(10,0) comment '排序',
   NICK_NAME            varchar(100) comment '用户帐号',
   USER_NAME            varchar(100) comment '用户姓名',
   TYPE                 varchar(20) comment '用户类型',
   GENDER               int comment '用户性别',
   PHONE                varchar(20) comment '用户手机号',
   EMAIL                varchar(50) comment '用户邮箱',
   DESCRIPTION          varchar(500) comment '用户描述',
   primary key (USER_ID)
);

alter table PUB_USER comment '用户信息';

/*==============================================================*/
/* Table: PUB_USERLOG                                           */
/*==============================================================*/
create table PUB_USERLOG
(
   LOG_ID               varchar(36) comment '日志ID',
   LOG_OPER             varchar(100) comment '操作',
   LOG_CONTENT          varchar(2000) comment '日志内容',
   LOG_ADDRESS          varchar(100) comment 'IP地址',
   LOG_USERID           varchar(36) comment '操作用户ID',
   LOG_USER             varchar(250) comment '操作用户名称',
   LOG_TIME             date comment '发生时间',
   LOG_STATUS           numeric(2,0) comment '日志状态',
   SYSTEM_MARK          varchar(100) comment '系统标识',
   LOG_ORG_ID           varchar(36) comment '所属组织',
   LOG_DOMAIN_ID        varchar(36) comment '所属管理范围',
   STATUS               numeric(5,0) comment '状态',
   CREATE_TIME          datetime comment '创建时间',
   UPDATE_TIME          datetime comment '修改时间',
   SORT_NO              numeric(10,0) comment '排序'
);

alter table PUB_USERLOG comment '操作日志';

/*==============================================================*/
/* Table: PUB_USERLOG_CONFIG                                    */
/*==============================================================*/
create table PUB_USERLOG_CONFIG
(
   LOGCFG_ID            varchar(36) not null comment '日志配置ID',
   STATUS               numeric(5,0) comment '状态',
   CREATE_TIME          datetime comment '创建时间',
   UPDATE_TIME          datetime comment '修改时间',
   SORT_NO              numeric(10,0) comment '排序',
   LOGCFG_MARK          varchar(200) comment '日志配置标志',
   LOGCFG_OPER          varchar(100) comment '操作',
   LOGCFG_DESC          varchar(2000) comment '描述',
   LOGCFG_STATUS        numeric(2,0) comment '日志配置状态',
   SYSTEM_MARK          varchar(100) comment '系统标识',
   LOGCFG_SUCCESS       varchar(2000) comment '成功信息',
   LOGCFG_FAILED        varchar(2000) comment '失败信息',
   primary key (LOGCFG_ID)
);

alter table PUB_USERLOG_CONFIG comment '操作日志配置';

/*==============================================================*/
/* Table: PUB_USER_R_ROLE                                       */
/*==============================================================*/
create table PUB_USER_R_ROLE
(
   USER_ID              varchar(36) not null comment '用户ID',
   ROLE_ID              varchar(36) not null comment '角色ID',
   primary key (USER_ID, ROLE_ID)
);

alter table PUB_USER_R_ROLE comment '用户角色关系';


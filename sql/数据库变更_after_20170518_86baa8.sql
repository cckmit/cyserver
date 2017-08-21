
/*==============================================================*/
/* Table: cy_system_setting 系统配置表                          */
/* 修改人 刘振     修改时间 2017-5-31                           */
/*==============================================================*/
ALTER TABLE `cy_system_setting`
  ADD COLUMN `is_audit_personal_activity` CHAR(2) NULL  COMMENT '是否审核个人活动（0：否；1：是）' AFTER `smsBirthdayTemplate`;



/*==============================================================*/
/* Table: cy_enterprise_team   企业表字段变更                   */
/* 修改人 刘振     修改时间 2017-5-31                           */
/*==============================================================*/
ALTER TABLE `cy_enterprise`
  ADD COLUMN `city` VARCHAR(255) NULL  COMMENT '企业所在城市' AFTER `address`,
  ADD COLUMN `longitude` VARCHAR(255) NULL  COMMENT '经度' AFTER `city`,
  ADD COLUMN `latitude` VARCHAR(255) NULL  COMMENT '纬度' AFTER `longitude`,
  ADD COLUMN `location_desc` VARCHAR(255) NULL  COMMENT '位置描述' AFTER `latitude`,
  ADD COLUMN `recruit_email` VARCHAR(255) NULL  COMMENT '招聘邮箱' AFTER `location_desc`,
  ADD COLUMN `financing_stage` VARCHAR(255) NULL  COMMENT '融资阶段' AFTER `recruit_email`;

/*==============================================================*/
/* Table: cy_enterprise_team   企业团队成员表                   */
/* 修改人 刘振     修改时间 2017-5-31                           */
/*==============================================================*/
drop table if exists cy_enterprise_team;
create table cy_enterprise_team
(
   id                   varchar(64) not null comment '编号',
   full_name            varchar(255) comment '姓名',
   pic                  varchar(255) comment '头像',
   position             varchar(255) comment '职位',
   enterprise_id        varchar(255) comment '企业编号',
   is_alumni            varchar(10) comment '是否为校友',
   classinfo            varchar(255) comment '班级信息',
   create_by            varchar(64) comment '创建者',
   create_date          datetime not null comment '创建时间',
   update_by            varchar(64) comment '更新者',
   update_date          datetime not null comment '更新时间',
   remarks              varchar(255) comment '备注信息',
   del_flag             char(1) not null default '0' comment '删除标记',
   primary key (id)
);

alter table cy_enterprise_team comment '企业团队成员表';

/*==============================================================*/
/* Table: cy_enterprise_job    招聘岗位                         */
/* 修改人 刘振     修改时间 2017-5-31                           */
/*==============================================================*/
drop table if exists cy_enterprise_job;
create table cy_enterprise_job
(
   id                   varchar(64) not null comment '编号',
   enterprise_id        varchar(64) comment '企业编号',
   name                 varchar(255) comment '岗位名称',
   city                 varchar(255) comment '招聘城市',
   longitude            varchar(100) comment '经度',
   latitude             varchar(100) comment '纬度',
   location_desc        varchar(255) comment '位置描述',
   experience_max       varchar(255) comment '经验上限(0为不限)',
   experience_min       varchar(255) comment '经验下限(0为不限)',
   salary_max           varchar(255) comment '薪水上限(0为不限)',
   salary_min           varchar(255) comment '薪水下限(0为不限)',
   education            varchar(255) comment '学历要求(0为不限)',
   recruiters_num       varchar(255) comment '招聘人数(0为不限)',
   description          text comment '职位描述',
   demand               text comment '职位要求',
   status               varchar(10) comment '状态(10:开启；20关闭；)',
   audit_status         varchar(10) comment '审核状态(0通过；1不通过；默认0)',
   auditor              varchar(255) comment '审核人编号',
   create_by            varchar(64) comment '创建者',
   create_date          datetime not null comment '创建时间',
   update_by            varchar(64) comment '更新者',
   update_date          datetime not null comment '更新时间',
   remarks              varchar(255) comment '备注信息',
   del_flag             char(1) not null default '0' comment '删除标记',
   primary key (id)
);

alter table cy_enterprise_job comment '招聘岗位';

/******************************** 抽奖活动 start ****************************************/

/*==============================================================*/
/* Table: act_activity      活动表                              */
/* 修改人 刘振     修改时间 2017-6-5                            */
/*==============================================================*/
drop table if exists act_activity;
create table act_activity
(
   id                   varchar(64) not null comment '编号',
   name                 varchar(255) comment '活动名称',
   type                 varchar(100) comment '活动类型（10：抽奖活动）',
   start_time           varchar(100) comment '开始时间',
   end_time             varchar(100) comment '活动结束时间',
   sign_up_end_time     varchar(100) comment '报名截止时间（默认为活动开始时间）',
   sign_up_start_time   varchar(100) comment '报名开始时间（暂时不用，默认为空任意时间）',
   introduction         text comment '活动介绍',
   pic                  varchar(255) comment '活动图标',
   organizer            varchar(255) comment '主办单位',
   sort                 varchar(100) comment '排序',
   del_flag             char(2) comment '删除标记（0：正常；1：删除）',
   create_by            varchar(64) comment '创建人',
   create_date          timestamp comment '创建时间',
   update_by            varchar(64) comment '修改人',
   update_date          timestamp comment '修改时间',
   remarks              varchar(255) comment '备注',
   primary key (id)
);

alter table act_activity comment '活动表';

/*==============================================================*/
/* Table: act_activity_prize     活动奖项表                     */
/* 修改人 刘振     修改时间 2017-6-5                            */
/*==============================================================*/
drop table if exists act_activity_prize;
create table act_activity_prize
(
   id                   varchar(64) not null comment '编号',
   activity_id          varchar(64) comment '活动编号',
   name                 varchar(255) comment '奖项名称',
   num                  varchar(100) comment '奖项数量',
   prize_name           varchar(255) comment '奖品名称',
   prize_src            varchar(255) comment '奖品图片路径（相对路径）',
   is_repeat            char(2) comment '是否允许中间人与其他奖项重复（0：否；1：是）',
   surplus_num          varchar(100) comment '剩余数量（默认为奖品数量，为0表示奖品已抽完）',
   sort                 varchar(100) comment '排序',
   del_flag             char(2) comment '删除标记（0：正常；1：删除）',
   create_by            varchar(64) comment '创建人',
   create_date          timestamp comment '创建时间',
   update_by            varchar(64) comment '修改人',
   update_date          timestamp comment '修改时间',
   remarks              varchar(255) comment '备注',
   primary key (id)
);

alter table act_activity_prize comment '活动奖项表';

/*==============================================================*/
/* Table: act_activity_music                                    */
/* 修改人 刘振     修改时间 2017-6-5                            */
/*==============================================================*/
drop table if exists act_activity_music;
create table act_activity_music
(
   id                   varchar(64) not null comment '编号',
   activity_id          varchar(64) comment '活动编号',
   type                 varchar(100) comment '类型（10：活动开始前音乐；20：活动进行中音乐；23：中奖音乐；30：活动结束后音乐）',
   is_repeat_play       char(2) comment '是否重复播放（0：否；1：是）',
   file_path            varchar(255) comment '音乐文件路径（相对路径）',
   del_flag             char(2) comment '删除标记（0：正常；1：删除）',
   create_by            varchar(64) comment '创建人',
   create_date          timestamp comment '创建时间',
   update_by            varchar(64) comment '修改人',
   update_date          timestamp comment '修改时间',
   remarks              varchar(255) comment '备注',
   primary key (id)
);

alter table act_activity_music comment '活动音乐表';

/*==============================================================*/
/* Table: act_activity_applicant     活动报名人表               */
/* 修改人 刘振     修改时间 2017-6-5                            */
/*==============================================================*/
drop table if exists act_activity_applicant;
create table act_activity_applicant
(
   id                   varchar(64) not null comment '编号',
   activity_id          varchar(64) comment '活动编号',
   weixin_app_id        varchar(100) comment '微信公众号APPID',
   open_id              varchar(100) comment '微信OpenId',
   head_src             varchar(255) comment '报名人头像（相对路径）',
   name                 varchar(255) comment '报名人姓名',
   telephone            varchar(20) comment '报名人手机号',
   is_winning           char(2) comment '是否已中奖（0：否；1：是）',
   del_flag             char(2) comment '删除标记（0：正常；1：删除）',
   create_by            varchar(64) comment '创建人',
   create_date          timestamp comment '创建时间',
   update_by            varchar(64) comment '修改人',
   update_date          timestamp comment '修改时间',
   remarks              varchar(255) comment '备注',
   primary key (id)
);

alter table act_activity_applicant comment '活动报名人表';

/*=====================================================================*/
/* Table: act_activity_winning 活动中奖信息表（为奖品与报名人中间表）  */
/* 修改人 刘振     修改时间 2017-6-5                                   */
/*=====================================================================*/
drop table if exists act_activity_winning;
create table act_activity_winning
(
   id                   varchar(64) not null comment '编号',
   activity_id          varchar(64) comment '活动编号',
   applicant_id         varchar(64) comment '报名人编号',
   awards_id            varchar(64) comment '奖项编号',
   sort                 varchar(100) comment '排序',
   del_flag             char(2) comment '删除标记（0：正常；1：删除）',
   create_by            varchar(64) comment '创建人',
   create_date          timestamp comment '创建时间',
   update_by            varchar(64) comment '修改人',
   update_date          timestamp comment '修改时间',
   remarks              varchar(255) comment '备注',
   primary key (id)
);

alter table act_activity_winning comment '活动中奖信息表（为奖品与报名人中间表）';

/******************************** 抽奖活动 end   ****************************************/

/*=====================================================================*/
/* Table: cloud_entrepreneur 校友企业家表                                */
/* 修改人 刘振     修改时间 2017-8-11                                     */
/*=====================================================================*/
drop table if exists cloud_entrepreneur;

CREATE TABLE `cloud_entrepreneur` (
	`id` VARCHAR (64) NOT NULL COMMENT '编号',
	`enterprise_id` VARCHAR (64) DEFAULT NULL COMMENT '企业编号',
	`team_id` VARCHAR (64) DEFAULT NULL COMMENT '团队成员编号',
	`telephone` VARCHAR (20) DEFAULT NULL COMMENT '联系电话',
	`college` VARCHAR (255) DEFAULT NULL COMMENT '学院',
	`grade` VARCHAR (255) DEFAULT NULL COMMENT '年级',
	`clbum` VARCHAR (255) DEFAULT NULL COMMENT '班级',
	`profession` VARCHAR (255) DEFAULT NULL COMMENT '专业',
	`sys_name` VARCHAR (255) DEFAULT NULL COMMENT '管理员名称',
	`sys_phone` VARCHAR (255) DEFAULT NULL COMMENT '管理员手机号',
	`accountNum` VARCHAR (255) DEFAULT NULL COMMENT '用户编号',
	`sync_status` VARCHAR (10) DEFAULT '10' COMMENT '同步状态（10:来自云平台同步；20:审核处理同步到云平台；）',
	`type` VARCHAR (255) DEFAULT NULL COMMENT '企业家类型(10:正式校友；20:名誉校友)',
	`status` VARCHAR (10) DEFAULT '10' COMMENT '校企状态（10 待审核；20:认证成功，正式校友；25：认证通过，名誉校友；30: 未通过审核）',
	`opinion` VARCHAR (255) DEFAULT NULL COMMENT '审核意见',
	`del_flag` CHAR (1) DEFAULT NULL COMMENT '删除标记',
	`create_by` VARCHAR (64) DEFAULT NULL COMMENT '创建者',
	`create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
	`update_by` VARCHAR (64) DEFAULT NULL COMMENT '修改者',
	`update_time` DATETIME DEFAULT NULL COMMENT '修改时间',
	`remark` VARCHAR (255) DEFAULT NULL COMMENT '备注',
	PRIMARY KEY (`id`)
) ;

alter table cloud_entrepreneur comment '企业家表';
ALTER TABLE `cloud_enterprise_team` ADD COLUMN `sex` varchar(10) COMMENT '性别（0:男；1:女）' AFTER `user_id`;

ALTER TABLE `cloud_entrepreneur`
CHANGE COLUMN `create_time` `create_date`  datetime NULL DEFAULT NULL COMMENT '创建时间' AFTER `create_by`,
CHANGE COLUMN `update_time` `update_date`  datetime NULL DEFAULT NULL COMMENT '修改时间' AFTER `update_by`;
CHANGE COLUMN `remark` `remarks`  varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '备注' AFTER `update_date`;
/******************************** 校友企业家表 end   ****************************************/



/********************************云平台校友企业 start********************************************/
/*=====================================================================*/
/* Table: cloud_enterprise 校友企业表                                */
/* 修改人 杨牛牛    修改时间 2017-8-18                                     */
/*=====================================================================*/
ALTER TABLE `cloud_enterprise`
ADD COLUMN `province`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '所在省' AFTER `address`;
/********************************云平台校友企业 start********************************************/
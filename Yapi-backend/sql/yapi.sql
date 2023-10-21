-- 接口信息
create table if not exists yapi.`interface_info`
(
    `id` int not null auto_increment comment '用户名' primary key,
    `name` varchar(256) not null comment '接口名称',
    `description` varchar(256) null comment '接口描述',
    `url` varchar(512) not null comment '接口地址',
    `requestHeader` text not null comment '请求头',
    `responseHeader` varchar(512) null comment '响应头',
    `status` varchar(256) default '0' not null comment '状态',
    `method` varchar(256) not null comment '请求类型',
    `userId` bigint not null comment '创建人',
    `create_time` datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    `update_time` datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    `is_deleted` tinyint default 0 not null comment '是否删除(0-未删, 1-已删)'
    ) comment '接口信息';

insert into yapi.`interface_info` (`id`, `name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values (4099, '程擎苍', 'EB', 'www.joella-gerhold.name', 'mtUox', 'CF', '0', 'TEKW4', 1997);
insert into yapi.`interface_info` (`id`, `name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values (3144416034, '傅彬', 'ubA', 'www.merrilee-harvey.org', 'wR', 'TiC', '0', 'CS', 73);
insert into yapi.`interface_info` (`id`, `name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values (348457, '高伟宸', '1p', 'www.randell-gerhold.org', 'kVnX', 'n83QA', '0', 'gtK', 5603237625);
insert into yapi.`interface_info` (`id`, `name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values (8488423, '许炫明', 'Qb', 'www.samuel-walter.org', 'ly', 'Gx', '0', 'Qs', 9770);
insert into yapi.`interface_info` (`id`, `name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values (5718524071, '谭伟泽', 'ZCW5', 'www.karina-stehr.com', 'Kwk0', 'jc', '0', 'kzEd', 868681);
insert into yapi.`interface_info` (`id`, `name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values (9543576170, '蔡子涵', 'A6', 'www.nancee-tromp.io', 'WSiYQ', 'l7X0', '0', 'O56v', 25487);
insert into yapi.`interface_info` (`id`, `name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values (376, '万烨霖', 'U9Qc', 'www.rodger-bernhard.io', 'Hx', 'b19e', '0', '7g0', 2062076);
insert into yapi.`interface_info` (`id`, `name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values (783, '赵雨泽', '3e5S', 'www.issac-bosco.biz', 'MHxU8', 'GoA', '0', 'DFPq', 68759517);
insert into yapi.`interface_info` (`id`, `name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values (10601, '薛智辉', 'qLmx', 'www.anne-lebsack.co', 'mG', 'va3hj', '0', 'Nta', 5622216);
insert into yapi.`interface_info` (`id`, `name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values (462359875, '唐鹏煊', 'mNn', 'www.chase-stiedemann.co', 'c8Pj', 'FJad', '0', 'AL', 5);
insert into yapi.`interface_info` (`id`, `name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values (104, '崔浩', 'Ef', 'www.madaline-halvorson.name', 'wrv3', 'vTt', '0', 'lK', 1710997);
insert into yapi.`interface_info` (`id`, `name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values (73, '顾伟宸', 'UCZ', 'www.malcom-mcglynn.name', 'jSXnv', '9k', '0', '8zot0', 2659528);
insert into yapi.`interface_info` (`id`, `name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values (60, '江健雄', 'Efy', 'www.janis-nitzsche.info', 'Psih', 'l6mI', '0', 'NEp07', 353);
insert into yapi.`interface_info` (`id`, `name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values (45465081, '卢修洁', 'aMLgq', 'www.alexis-baumbach.com', 'GE', 'ruy', '0', 'J49L', 9221);
insert into yapi.`interface_info` (`id`, `name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values (24, '段哲瀚', 'wQJ', 'www.darci-spencer.org', 'A27e', 'JX16', '0', 'wX', 9268941751);
insert into yapi.`interface_info` (`id`, `name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values (794645415, '袁昊强', 'g3M', 'www.franklyn-nikolaus.io', 'cns', 'xElbb', '0', 'KiuND', 1);
insert into yapi.`interface_info` (`id`, `name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values (20624504, '秦伟泽', 'jH3NT', 'www.gerald-bernier.biz', 'dD', 'Ssj0p', '0', 'RpJkB', 298959);
insert into yapi.`interface_info` (`id`, `name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values (126, '高智渊', 'NPP', 'www.amee-nitzsche.net', 'E3J', 'DdYjE', '0', 'prG', 334);
insert into yapi.`interface_info` (`id`, `name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values (656377, '顾立辉', 'QE', 'www.lesa-stracke.name', 'wdft', '7XU', '0', '9y', 6);
insert into yapi.`interface_info` (`id`, `name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values (29, '吴琪', 'eBs', 'www.miyoko-bartell.info', 'a29f', 'H1Ho', '0', 'zVN4v', 1573833325);


create table if not exists yapi.`user_interface_info`
(
    `id` int not null auto_increment comment '用户名' primary key,
    `userId` bigint not null comment '用户Id',
    `interfaceId` bigint not null comment '接口Id',
    `totalNum` int not null default 0 comment '使用总数',
    `leftNum` int not null default 0 comment '剩余次数',
    `status` varchar(256) default '0' not null comment '状态',
    `create_time` datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    `update_time` datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    `is_deleted` tinyint default 0 not null comment '是否删除(0-未删, 1-已删)'
    ) comment '接口用户信息';

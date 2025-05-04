create table r_submit
(
    id           bigint auto_increment comment 'id'
        primary key,
    user_id      bigint                                     not null comment 'user id',
    question_id  bigint                                     not null comment 'question id',
    status       tinyint unsigned default '0'               not null comment 'status: 0->waiting, 1->running, 10->fail, 11->ac',
    lang         tinyint unsigned default '0'               not null comment 'lang: 0->java',
    code         text                                       not null comment 'code',
    judge_result json                                       null comment 'judge result',
    is_deleted   tinyint(1)       default 0                 null comment 'is deleted',
    create_time  datetime         default CURRENT_TIMESTAMP null comment 'create time',
    update_time  datetime         default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment 'update time'
)
    comment 'submit relation record';

create index idx_question_id
    on r_submit (question_id);

create index idx_user_id
    on r_submit (user_id);

create table t_question
(
    id           bigint                               not null comment 'random id'
        primary key,
    title        varchar(255)                         not null comment 'title',
    description  text                                 null comment 'description',
    tags         json                                 null comment 'tags',
    judge_case   json                                 not null comment 'judge case: inputs, outputs',
    judge_config json                                 not null comment 'judge config: time and memory limit',
    submit_cnt   int        default 0                 null comment 'submit count',
    accepted_cnt int        default 0                 null comment 'ac count',
    is_deleted   tinyint(1) default 0                 null comment 'is deleted',
    create_time  datetime   default CURRENT_TIMESTAMP null comment 'create time',
    update_time  datetime   default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment 'update time'
)
    comment 'question table';

create table t_user
(
    id          bigint auto_increment comment 'id'
        primary key,
    username    varchar(64)                                not null comment 'username',
    password    varchar(128)                               not null comment 'password',
    role        tinyint unsigned default '0'               not null comment 'user role: 0->user, 1->admin',
    create_time datetime         default CURRENT_TIMESTAMP null comment 'create time',
    update_time datetime         default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment 'update time',
    is_deleted  tinyint(1)       default 0                 null comment 'is deleted: 0->no, 1->yes',
    tags        json                                       null comment 'user question tags'
)
    comment 'user table';


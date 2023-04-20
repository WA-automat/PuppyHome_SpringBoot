create table user
(
    id             int auto_increment
        primary key,
    openId         varchar(1000) not null,
    nickName       varchar(100)  not null,
    avatar         varchar(1000) null,
    realName       varchar(100)  not null,
    age            double        not null,
    gender         int           not null,
    telephone      varchar(100)  not null,
    authentication int default 0 not null,
    constraint user_id_uindex
        unique (id),
    constraint user_openId_uindex
        unique (openId)
);
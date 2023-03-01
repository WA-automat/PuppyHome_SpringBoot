# PuppyHome_springboot

## 技术栈

1. SpringBoot
2. MySQL
3. MyBatis与MyBatis-plus

## 数据库架构

UserInfo

1. OpenId
2. NickName
3. Avatar
4. RealName
5. Age
6. Gender
7. Telephone
8. authentication

建表语句：

```mysql
create table user
(
    id        int auto_increment,
    openId    varchar(1000) not null,
    nickName  varchar(100)  not null,
    avatar    varchar(1000) null,
    realName  varchar(100)  not null,
    age       double        not null,
    gender    int           not null,
    telephone varchar(100)  not null,
    authentication int default 0 not null,
    constraint user_pk
        primary key (id)
);

create unique index user_id_uindex
    on user (id);

create unique index user_openId_uindex
    on user (openId);

```

---

DogInfo

1. id
2. DogName
3. Photo
4. Gender
5. Age
6. Type
7. ownerId
8. state

```mysql
create table dog
(
    id      int auto_increment,
    dogName varchar(100)  not null,
    photo   varchar(1000) not null,
    gender  int           not null,
    age     double        not null,
    type    varchar(100)  not null,
    ownerId int           not null,
    state int default 0   not null,
    constraint dog_pk
        primary key (id)
);

create unique index dog_id_uindex
    on dog (id);

```

## 项目日志

2023-2-28：

1. 创建项目
2. 集成MyBatis与MyBatis-plus
3. 创建用户信息数据库以及用户实体类、映射类
4. 实现登录接口

2023-3-1

1. 完成用户信息接口 
2. 创建小狗信息数据库以及小狗实体类、映射类
2. 实现获取用户与小狗信息的接口

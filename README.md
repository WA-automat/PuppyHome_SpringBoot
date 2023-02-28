# PuppyHome_springboot



## 数据库架构

UserInfo

1. OpenId
2. NickName
3. Avatar
4. RealName
5. Age
6. Gender
7. Telephone

建表语句：

```mysql
create table user
(
    id        int,
    openId    varchar(1000) not null,
    nickName  varchar(100)  not null,
    avatar    varchar(1000) null,
    realName  varchar(100)  not null,
    age       double        not null,
    gender    int           not null,
    telephone varchar(100)  not null
);

create unique index user_id_uindex
    on user (id);

create unique index user_openId_uindex
    on user (openId);

alter table user
    modify id int auto_increment;

```

---

DogInfo

1. id
2. DogName
3. Photo
4. Gender
5. Age
6. Type

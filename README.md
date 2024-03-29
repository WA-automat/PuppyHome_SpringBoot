# PuppyHome_springboot

## 技术栈

1. SpringBoot
2. MySQL
3. MyBatis与MyBatis-plus
4. Redis
5. RestTemplate 结合 wx server api
6. Jwt
7. Swagger2
7. Flask
7. Resnet-18卷积神经网络

## 主要数据库架构

### 用户数据库

User

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

### 小狗数据库

Dog

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

2023-3-1：

1. 完成用户信息接口 
2. 创建小狗信息数据库以及小狗实体类、映射类
2. 实现获取用户与小狗信息的接口

2023-3-2：

1. 编写小狗与主人信息查询接口
2. 完成message数据库建立与实体类、映射类
3. 实现sql代码，用以查询收发信息的用户与小狗及其收养状态
3. 实现申请数据以及申请的各类接口

2023-3-3：

1. 完善申请接口
2. 创建收藏数据库表，并实现实体类与映射类
3. 创建管理员信息接口

2023-3-4~2023-3-5：

1. 创建article数据库，并实现实体类与映射类
2. 完成创建文章的API

2023-3-6~2023-3-7：

1. 完成文章其他相关API
2. 创建Type数据库及其实体类与映射类
3. 完成返回所有小狗类别的API

2023-3-8：

1. 完成收藏的增删改查API
2. 调试CORS模块配置

2023-3-9~2023-3-10：

1. 完成收藏部分代码调试
2. 创建收养信息数据库，并实现实体类与映射类

2023-3-13~2023-3-14：

1. 完成收养信息部分API（包括：发送，删除，接收，展示）
2. debug 收养接口
3. 完成搜索接口
4. 完善“我的发布”接口，提升细粒度
5. 创建异步线程池，将部分方法改为异步方法，提升效率

2023-3-15：

1. 设计分类模型
2. 创建预测类别接口并实现

2023-3-18：
1. 完善模型
2. 创建flask项目并实现预测接口
3. 结合SpringBoot与flask项目的预测接口
4. 增加getUserInfo接口信息

2023-3-21：

1. 预测接口修改为异步方法

2023-4-12：

1. 模型调试完毕，项目完结

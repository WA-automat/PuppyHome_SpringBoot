create table article
(
    id          int auto_increment
        primary key,
    userId      int           not null,
    dogId       int           not null,
    title       varchar(100)  not null,
    publishTime timestamp     not null,
    description varchar(1000) not null,
    constraint article_id_uindex
        unique (id)
);
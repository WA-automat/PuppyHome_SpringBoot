create table collect
(
    id        int auto_increment
        primary key,
    userId    int not null,
    articleId int not null,
    constraint collect_id_uindex
        unique (id)
);
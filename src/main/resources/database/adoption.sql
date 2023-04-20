create table adoption
(
    id        int auto_increment
        primary key,
    userId    int not null,
    articleId int not null,
    constraint adoption_id_uindex
        unique (id)
);
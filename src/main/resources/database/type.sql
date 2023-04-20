create table type
(
    id   int auto_increment
        primary key,
    type varchar(100) not null,
    constraint type_id_uindex
        unique (id)
);
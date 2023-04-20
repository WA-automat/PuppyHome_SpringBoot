create table application
(
    id          int auto_increment
        primary key,
    userId      int           not null,
    name        varchar(100)  not null,
    telephone   varchar(100)  not null,
    description varchar(1000) not null,
    constraint application_id_uindex
        unique (id)
);
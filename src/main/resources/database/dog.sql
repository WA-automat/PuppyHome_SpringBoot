create table dog
(
    id      int auto_increment
        primary key,
    dogName varchar(100)  not null,
    photo   varchar(1000) not null,
    gender  int           not null,
    age     double        null,
    type    varchar(100)  not null,
    ownerId int           not null,
    state   int default 0 null,
    constraint dog_id_uindex
        unique (id)
);
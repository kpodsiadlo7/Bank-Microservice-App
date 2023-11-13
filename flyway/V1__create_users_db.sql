create table users
(
    id                bigint GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    username          varchar(100),
    real_name         varchar(100),
    password          varchar(255)
);

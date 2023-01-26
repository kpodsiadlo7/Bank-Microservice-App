create table users_db
(
    id                bigint GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    username          varchar(100),
    real_name         varchar(100),
    password          varchar(255),
    plain_password    varchar(255)
);

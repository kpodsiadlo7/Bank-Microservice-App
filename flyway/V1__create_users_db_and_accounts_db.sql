create table accounts_db
(
    id              bigint GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    account_name    varchar(50),
    number          varchar(25),
    currency        varchar(7),
    currency_symbol varchar(7),
    balance         decimal,
    user_entity_id  bigint not null
);

create table users_db
(
    id                bigint GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    username          varchar(100),
    real_name         varchar(100),
    password          varchar(255),
    plain_password    varchar(255),
    account_entity_id bigint not null,
    foreign key (account_entity_id) references accounts_db (id)
);

alter table accounts_db
    add foreign key (user_entity_id) references users_db (id);

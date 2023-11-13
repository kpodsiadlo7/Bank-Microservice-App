create table accounts
(
    id              bigint GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    account_name    varchar(50),
    number          varchar(25),
    currency        varchar(7),
    currency_symbol varchar(7),
    balance         decimal,
    user_id  bigint not null
);
create type credit_kind as enum (
    'CASH',
    'MORTGAGE',
    'DEBIT'
    );

create table credits_db
(
    id              bigint GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id         bigint,
    account_id      bigint,
    proposal_number varchar(25),
    credit_kind     credit_kind
);
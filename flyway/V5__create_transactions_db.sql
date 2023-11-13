create table transactions
(
    id               bigint GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id          bigint null,
    description      varchar(255),
    kind_transaction    varchar(8),
    transaction_date date
)
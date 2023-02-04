create type status_application as enum ('ACCEPT','REQUEST','REJECTED');

create table proposals_db
(
    id                 bigint GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id            bigint,
    account_id         bigint,
    amount_of_credit   float,
    month              int,
    salary             float,
    interest           double precision,
    commission         double precision,
    monthly_fee        decimal,
    currency           varchar(7),
    currency_symbol    varchar(7),
    username           varchar(255),
    purpose            varchar(255),
    application_number varchar(20),
    application_date   date,
    status_application status_application
)
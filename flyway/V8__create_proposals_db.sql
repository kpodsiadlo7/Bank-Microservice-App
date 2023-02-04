create type status_proposal as enum (
    'ACCEPT',
    'REQUEST',
    'REJECTED');
create type description_rejected as enum (
    'ACCEPT',
    'CREDIT_EXCEEDS_YOUR_FINANCIAL_CAPACITY',
    'ALREADY_HAVE_CREDIT_ON_THIS_ACCOUNT');

create table proposals_db
(
    id                   bigint GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id              bigint,
    account_id           bigint,
    amount_of_credit     float,
    month                int,
    salary               float,
    interest             double precision,
    commission           double precision,
    monthly_fee          decimal,
    currency             varchar(7),
    currency_symbol      varchar(7),
    username             varchar(255),
    purpose              varchar(255),
    proposal_number      varchar(20),
    application_date     date,
    accept_statement     boolean,
    status_proposal      status_proposal,
    description_rejected description_rejected
)
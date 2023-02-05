create type status_proposal as enum (
    'ACCEPT',
    'OPEN',
    'REJECTED');
create type description_rejected as enum (
    'OPEN',
    'ACCEPT',
    'CREDIT_EXCEEDS_YOUR_FINANCIAL_CAPACITY',
    'ALREADY_HAVE_CREDIT_ON_THIS_ACCOUNT');

create type credit_kind as enum (
    'CASH',
    'MORTGAGE',
    'DEBIT'
    );

create table proposals_db
(
    id                   bigint GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id              bigint,
    account_id           bigint,
    amount_of_credit     double precision,
    month                int,
    salary               double precision,
    interest             double precision,
    commission           double precision,
    monthly_fee          double precision,
    currency             varchar(7),
    currency_symbol      varchar(7),
    username             varchar(255),
    purpose              varchar(255),
    proposal_number      varchar(25),
    date_from            date,
    date_to              date,
    accept_statement     boolean,
    status_proposal      status_proposal,
    description_rejected description_rejected,
    credit_kind          credit_kind
)
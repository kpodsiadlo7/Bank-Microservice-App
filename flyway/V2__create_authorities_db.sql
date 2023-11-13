create table authorities
(
    id        bigint GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id   bigint null,
    authority varchar(255)
)
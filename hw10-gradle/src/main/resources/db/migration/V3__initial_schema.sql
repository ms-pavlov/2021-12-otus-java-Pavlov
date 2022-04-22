create sequence phone_sequence start with 1 increment by 1;

create table phone
(
    phone_id bigint not null primary key,
    phone_number varchar(150),
    phone_client_id bigint
);
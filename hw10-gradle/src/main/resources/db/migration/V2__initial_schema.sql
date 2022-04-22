create sequence address_sequence start with 1 increment by 1;

create table address
(
    address_id bigint not null primary key,
    address_street varchar(150),
    address_client_id bigint
);

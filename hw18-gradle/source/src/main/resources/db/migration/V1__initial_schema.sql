create table client
(
    id bigserial not null primary key,
    order_column int not null,
    name varchar(50) not null
);
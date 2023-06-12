drop table if exists withdrawal;
drop table if exists product;
drop table if exists customer;
drop table if exists address;
drop table if exists product_type;
drop table if exists withdrawal_status;

create table address
(
    id          int          not null primary key,
    line_one    varchar(255),
    street_name varchar(255) not null,
    town        varchar(255) not null,
    province    varchar(255) not null,
    postal_code varchar(255) not null,
    country     varchar(255) not null
);

create table customer
(
    id         int          not null primary key,
    name       varchar(255) not null,
    phone_no   varchar(255) not null,
    email      varchar(255) not null,
    dob        date         not null,
    address_id int          not null,

    foreign key (address_id) references address (id)
);

create table product_type
(
    id          int          not null primary key,
    description varchar(255) not null,
    name        varchar(255) not null
);

create table product
(
    id              int     not null primary key,
    balance         numeric(38, 2),
    account_number  varchar not null,
    customer_id     int     not null,
    product_type_id int     not null,

    foreign key (customer_id) references customer (id),
    foreign key (product_type_id) references product_type (id)
);

create table withdrawal
(
    id              int            not null primary key,
    amount          numeric(38, 2) not null,
    withdrawal_date timestamp      not null,
    product_id      int            not null,
    trx_id          bigint         not null,

    foreign key (product_id) references product (id)
);

create table withdrawal_status
(
    id          int          not null primary key,
    status      smallint     not null,
    description varchar(255) not null,
    trx_id      bigint       not null
);

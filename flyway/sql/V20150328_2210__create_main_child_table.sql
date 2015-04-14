create table app.main(
    id integer not null generated always as identity,
    name varchar(20) not null,
    value clob,
    status varchar(10),
    primary key (id)
);

create table app.child(
    id integer not null generated always as identity,
    name varchar(20) not null,
    value clob,
    type varchar(10),
    main_id integer not null,
    primary key (id)
);
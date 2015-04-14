create table app.async(
    id integer not null generated always as identity,
    step integer not null,
    maximum integer not null,
    speed integer not null,
    primary key (id)
);
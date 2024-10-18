create table customer(
    id serial primary key,
    name text not null,
    email text not null unique,
    age int not null
)
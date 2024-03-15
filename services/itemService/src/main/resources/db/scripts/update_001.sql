create table person (
    person_id serial primary key NOT NULL,
    person_login varchar(2000) NOT NULL unique,
    person_password varchar(2000) NOT NULL
);

insert into person (person_login, person_password) values ('Qwe', '123');
insert into person (person_login, person_password) values ('Qwe2', '123');
insert into person (person_login, person_password) values ('Qwe3', '123');
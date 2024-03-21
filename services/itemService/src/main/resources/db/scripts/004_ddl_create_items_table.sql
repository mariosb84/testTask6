create table items (
    person_id serial primary key NOT NULL,
    person_login varchar(2000) NOT NULL unique,
    person_password varchar(2000) NOT NULL
);
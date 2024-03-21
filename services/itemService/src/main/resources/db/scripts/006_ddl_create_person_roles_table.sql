CREATE TABLE person_roles (
   person_roles_id SERIAL PRIMARY KEY ,
   role_id int references person_role(role_id) ,
   person_role_id int references person(person_id)
);
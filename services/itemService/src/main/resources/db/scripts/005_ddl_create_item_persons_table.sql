CREATE TABLE item_persons (
   item_persons_id SERIAL PRIMARY KEY ,
   person_id int references items(item_person_id),
   person_role_id int references person(person_id)
);
CREATE TABLE IF NOT EXISTS item_persons (
   item_persons_id SERIAL PRIMARY KEY ,
   person_id INT REFERENCES person(person_id),
   item_person_id INT REFERENCES items(item_id)
);
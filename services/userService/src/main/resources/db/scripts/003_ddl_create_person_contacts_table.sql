CREATE TABLE IF NOT EXISTS person_contacts (
    person_contacts_id SERIAL PRIMARY KEY NOT NULL,
    person_id BIGINT UNIQUE,
    person_email TEXT  NOT NULL,
    person_phone TEXT  NOT NULL,
    FOREIGN KEY (person_id) REFERENCES person(person_id) ON DELETE CASCADE
);
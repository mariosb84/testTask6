CREATE TABLE IF NOT EXISTS person (
    person_id SERIAL PRIMARY KEY NOT NULL,
    person_lastname TEXT  NOT NULL,
    person_login TEXT NOT NULL,
    person_middlename TEXT,
    person_userbirthdate timestamp NOT NULL,
    person_password TEXT NOT NULL
);
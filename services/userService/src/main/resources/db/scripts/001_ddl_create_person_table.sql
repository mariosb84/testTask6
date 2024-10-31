CREATE TABLE IF NOT EXISTS person (
    person_id SERIAL PRIMARY KEY NOT NULL,
    person_lastName TEXT  NOT NULL,
    person_login TEXT NOT NULL,
    person_middleName TEXT,
    person_userBirthDate timestamp NOT NULL,
    person_password TEXT NOT NULL
);
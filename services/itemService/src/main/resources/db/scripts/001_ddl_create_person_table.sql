CREATE TABLE IF NOT EXISTS person (
    person_id SERIAL PRIMARY KEY NOT NULL,
    person_login TEXT NOT NULL UNIQUE,
    person_password TEXT NOT NULL,
    person_email TEXT,
    person_phone TEXT
);
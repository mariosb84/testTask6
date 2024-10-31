CREATE TABLE IF NOT EXISTS person_photo (
    person_photo_id SERIAL PRIMARY KEY NOT NULL,
    person_id BIGINT UNIQUE,
    person_photo BYTEA,
    FOREIGN KEY (person_id) REFERENCES person(person_id) ON DELETE CASCADE
);
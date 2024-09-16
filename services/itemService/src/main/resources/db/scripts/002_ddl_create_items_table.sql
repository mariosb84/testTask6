CREATE TABLE IF NOT EXISTS items (
    item_id serial PRIMARY KEY NOT NULL,
    item_name TEXT,
    item_Text TEXT,
    item_created TIMESTAMP NOT NULL,
    item_status TEXT
);
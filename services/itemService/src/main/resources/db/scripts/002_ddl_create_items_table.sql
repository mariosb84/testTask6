create table items (
    item_id serial PRIMARY KEY NOT NULL,
    item_name TEXT,
    item_itemText TEXT,
    item_created timestamp NOT NULL,
    item_status TEXT
);
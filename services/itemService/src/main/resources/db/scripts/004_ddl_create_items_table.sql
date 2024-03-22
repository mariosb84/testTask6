create table items (
    item_id serial PRIMARY KEY NOT NULL,
    item_name TEXT,
    item_itemText TEXT,
    item_created timestamp NOT NULL,
    status_id int references item_status(status_id)
);
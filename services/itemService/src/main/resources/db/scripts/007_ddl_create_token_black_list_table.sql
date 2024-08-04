create table token_black_list (
    token_id SERIAL PRIMARY KEY NOT NULL,
    token_name TEXT NOT NULL unique
);
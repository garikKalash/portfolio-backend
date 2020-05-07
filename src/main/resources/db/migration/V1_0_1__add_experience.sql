CREATE TABLE experience (
    id serial primary key,
    company VARCHAR(36) NOT NULL,
    role VARCHAR(36) NOT NULL,
    "from"  INTEGER NOT NULL,
    "to"    INTEGER NULL,
    description CHARACTER VARYING NULL,
    present BOOLEAN DEFAULT FALSE
);
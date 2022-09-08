CREATE TABLE "user" (
    id      serial primary key,
    name        VARCHAR(36) NOT NULL,
    surname     VARCHAR(36) NOT NULL,
    password    character varying NOT NULL,
    role    VARCHAR(16) NOT NULL,
    email   VARCHAR(32) NOT NULL,
    phone   VARCHAR(32) NOT NULL,
    address VARCHAR(32) NOT NULL,
    skype   VARCHAR(32) NOT NULL,
    CHECK ( role in ('technical_guy', 'hr', 'me') )
);

CREATE TABLE education (
    id serial primary key,
    name VARCHAR(36) NOT NULL,
    "from"  INTEGER NOT NULL,
    "to"    INTEGER NULL,
    present BOOLEAN DEFAULT FALSE,
    level   VARCHAR(36) NOT NULL
);

CREATE TABLE skill (
    id                  serial primary key,
    name                VARCHAR(36) NOT NULL,
    experience_in_year  DECIMAL NOT NULL
);

CREATE TABLE language (
    id serial primary key,
    name    VARCHAR(36) NOT NULL,
    level   VARCHAR(36) NOT NULL
);

CREATE TABLE project (
    id   serial primary key ,
    name VARCHAR(36) NOT NULL,
    description CHARACTER VARYING NOT NULL
);




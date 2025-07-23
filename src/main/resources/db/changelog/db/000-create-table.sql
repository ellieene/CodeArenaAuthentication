--liquibase formatted sql

-- changeset ellieene:create-base-users

CREATE TABLE users (
                       id UUID PRIMARY KEY NOT NULL,
                       username VARCHAR(50) NOT NULL UNIQUE,
                       password VARCHAR(100) NOT NULL,
                       email VARCHAR(100) NOT NULL UNIQUE,
                       role VARCHAR(10) NOT NULL
);

-- rollback DROP TABLE users;
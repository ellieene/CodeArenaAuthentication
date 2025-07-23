--liquibase formatted sql

-- changeset ellieene:rename-pointer-to-points
ALTER TABLE users RENAME COLUMN pointer TO points;

-- rollback ALTER TABLE users RENAME COLUMN points TO pointer;
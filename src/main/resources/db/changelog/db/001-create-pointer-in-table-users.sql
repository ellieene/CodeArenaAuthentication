--liquibase formatted sql

-- changeset ellieene:add-pointer-column
ALTER TABLE users ADD COLUMN pointer INT;

-- rollback ALTER TABLE users DROP COLUMN pointer;
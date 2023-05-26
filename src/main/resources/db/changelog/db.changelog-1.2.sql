--liquibase formatted sql

--changeset aojona:1
ALTER TABLE users
ADD COLUMN password VARCHAR(128) DEFAULT '{noop}00';
--rollback ALTER TABLE users DROP COLUMN password

--changeset aojona:2
ALTER TABLE users
ADD COLUMN role VARCHAR(32);
--rollback ALTER TABLE users DROP COLUMN role
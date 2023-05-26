--liquibase formatted sql

--changeset aojona:1
ALTER TABLE users
ADD COLUMN created_by VARCHAR(64);
--rollback ALTER TABLE users DROP COLUMN created_by

--changeset aojona:2
ALTER TABLE users
ADD COLUMN modified_by VARCHAR(64);
--rollback ALTER TABLE users DROP COLUMN modified_by

--changeset aojona:3
ALTER TABLE hotel
ADD COLUMN created_by VARCHAR(64);
--rollback ALTER TABLE hotel DROP COLUMN created_by

--changeset aojona:4
ALTER TABLE hotel
ADD COLUMN modified_by VARCHAR(64);
--rollback ALTER TABLE hotel DROP COLUMN modified_by

--changeset aojona:5
ALTER TABLE room
ADD COLUMN created_by VARCHAR(64);
--rollback ALTER TABLE room DROP COLUMN created_by

--changeset aojona:6
ALTER TABLE room
ADD COLUMN modified_by VARCHAR(64);
--rollback ALTER TABLE room DROP COLUMN modified_by

--changeset aojona:7
ALTER TABLE reservation
ADD COLUMN created_by VARCHAR(64);
--rollback ALTER TABLE reservation DROP COLUMN created_by

--changeset aojona:8
ALTER TABLE reservation
ADD COLUMN modified_by VARCHAR(64);
--rollback ALTER TABLE reservation DROP COLUMN modified_by
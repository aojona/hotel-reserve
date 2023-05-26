--liquibase formatted sql

--changeset aojona:1
ALTER TABLE users
ADD COLUMN created_at TIMESTAMP;
--rollback ALTER TABLE users DROP COLUMN created_at

--changeset aojona:2
ALTER TABLE users
ADD COLUMN modified_at TIMESTAMP;
--rollback ALTER TABLE users DROP COLUMN modified_at

--changeset aojona:3
ALTER TABLE hotel
ADD COLUMN created_at TIMESTAMP;
--rollback ALTER TABLE hotel DROP COLUMN created_at

--changeset aojona:4
ALTER TABLE hotel
ADD COLUMN modified_at TIMESTAMP;
--rollback ALTER TABLE hotel DROP COLUMN modified_at

--changeset aojona:5
ALTER TABLE room
ADD COLUMN created_at TIMESTAMP;
--rollback ALTER TABLE room DROP COLUMN created_at

--changeset aojona:6
ALTER TABLE room
ADD COLUMN modified_at TIMESTAMP;
--rollback ALTER TABLE room DROP COLUMN modified_at

--changeset aojona:7
ALTER TABLE reservation
ADD COLUMN created_at TIMESTAMP;
--rollback ALTER TABLE reservation DROP COLUMN created_at

--changeset aojona:8
ALTER TABLE reservation
ADD COLUMN modified_at TIMESTAMP;
--rollback ALTER TABLE reservation DROP COLUMN modified_at
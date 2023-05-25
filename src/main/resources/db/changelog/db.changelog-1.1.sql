--liquibase formatted sql

--changeset aojona:1
ALTER TABLE users
ADD COLUMN created_at TIMESTAMP;

--changeset aojona:2
ALTER TABLE users
ADD COLUMN modified_at TIMESTAMP;

--changeset aojona:3
ALTER TABLE hotel
ADD COLUMN created_at TIMESTAMP;

--changeset aojona:4
ALTER TABLE hotel
ADD COLUMN modified_at TIMESTAMP;

--changeset aojona:5
ALTER TABLE room
ADD COLUMN created_at TIMESTAMP;

--changeset aojona:6
ALTER TABLE room
ADD COLUMN modified_at TIMESTAMP;

--changeset aojona:7
ALTER TABLE reservation
ADD COLUMN created_at TIMESTAMP;

--changeset aojona:8
ALTER TABLE reservation
ADD COLUMN modified_at TIMESTAMP;
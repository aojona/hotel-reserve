--liquibase formatted sql

--changeset aojona:1
CREATE TABLE IF NOT EXISTS users
(
    id          BIGSERIAL PRIMARY KEY,
    email       VARCHAR(64) UNIQUE,
    first_name  VARCHAR(32),
    last_name   VARCHAR(32)
);
--rollback DROP TABLE users

--changeset aojona:2
CREATE TABLE IF NOT EXISTS hotel
(
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(64) UNIQUE
);
--rollback DROP TABLE hotel

--changeset aojona:3
CREATE TABLE IF NOT EXISTS room
(
    id          BIGSERIAL PRIMARY KEY,
    available   BOOLEAN NOT NULL,
    number      INTEGER,
    hotel_id    BIGINT NOT NULL REFERENCES hotel(id) ON DELETE CASCADE,
    UNIQUE (number, hotel_id)
);
--rollback DROP TABLE room

--changeset aojona:4
CREATE TABLE IF NOT EXISTS reservation
(
    id          BIGSERIAL PRIMARY KEY,
    room_id     BIGINT NOT NULL REFERENCES room(id) ON DELETE CASCADE,
    user_id     BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    hotel_id    BIGINT NOT NULL REFERENCES hotel(id) ON DELETE CASCADE,
    UNIQUE (room_id, hotel_id)
);
--rollback DROP TABLE reservation
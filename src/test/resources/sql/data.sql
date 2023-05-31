-- INSERT INTO users(id, email, first_name, last_name, password, role)
-- VALUES (1, 'admin@gmail.com', 'Jonathan', 'Swift', 'noop{admin}', 'ADMIN'),
--        (2, 'user@gmail.com', 'Luc', 'Besson', 'noop{user}', 'USER')

INSERT INTO hotel(id, name)
VALUES (1, 'Plaza');

SELECT SETVAL('hotel_id_seq', (SELECT MAX(id) FROM hotel));

INSERT INTO users(id, email, first_name, last_name, password, role)
VALUES (1, 'swift@gmail.com', 'Jonathan', 'Swift', '{noop}1234', 'ADMIN'),
       (2, 'tesla@gmail.com', 'Nikola', 'Tesla', '{noop}0000', 'USER'),
       (3, 'besson@gmail.com', 'Luc', 'Besson', '{noop}lucy', 'USER');

SELECT SETVAL('users_id_seq', (SELECT MAX(id) FROM users));
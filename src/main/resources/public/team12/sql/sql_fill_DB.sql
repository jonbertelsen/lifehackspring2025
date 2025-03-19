INSERT INTO team12_users (username, password_hash)
VALUES ('alice123', 'hashedpassword1'),
       ('bob_sleeper', 'hashedpassword2'),
       ('charlie_nightowl', 'hashedpassword3'),
       ('diana_dreamer', 'hashedpassword4');


INSERT INTO team12_sleep_records (user_id, sleep_date, sleep_start, sleep_end)
VALUES (1, '2024-03-15', '2024-03-15 23:00:00', '2024-03-16 07:00:00'),
       (1, '2024-03-16', '2024-03-16 23:30:00', '2024-03-17 06:45:00'),
       (2, '2024-03-15', '2024-03-15 22:30:00', '2024-03-16 06:00:00'),
       (2, '2024-03-16', '2024-03-16 23:00:00', '2024-03-17 07:15:00'),
       (3, '2024-03-15', '2024-03-15 02:00:00', '2024-03-15 10:00:00'),
       (3, '2024-03-16', '2024-03-16 03:30:00', '2024-03-16 11:45:00'),
       (4, '2024-03-15', '2024-03-15 23:15:00', '2024-03-16 06:30:00'),
       (4, '2024-03-16', '2024-03-16 22:45:00', '2024-03-17 07:00:00');


INSERT INTO team12_sleep_analytics (user_id, sleep_date, total_sleep)
VALUES (1, '2024-03-15', 8.00),
       (1, '2024-03-16', 7.25),
       (2, '2024-03-15', 7.50),
       (2, '2024-03-16', 8.25),
       (3, '2024-03-15', 8.00),
       (3, '2024-03-16', 8.25),
       (4, '2024-03-15', 7.25),
       (4, '2024-03-16', 8.25);

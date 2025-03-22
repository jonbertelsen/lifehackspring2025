INSERT INTO team12_users (username, password_hash)
VALUES
    ('john_doe', 'hashed_password_123'),
    ('jane_smith', 'hashed_password_456'),
    ('alex_lee', 'hashed_password_789');


INSERT INTO team12_sleep_records (user_id, sleep_start, sleep_end)
VALUES
    (1, '2025-03-18 22:00:00', '2025-03-19 06:00:00'),
    (2, '2025-03-18 23:00:00', '2025-03-19 07:00:00'),
    (3, '2025-03-18 21:30:00', '2025-03-19 05:30:00'),
    (1, '2025-03-19 22:00:00', '2025-03-20 06:30:00'),
    (2, '2025-03-19 23:30:00', '2025-03-20 07:30:00');



INSERT INTO team12_sleep_analytics (user_id, total_sleep)
VALUES
    (1, 16.5),  -- 8 hours + 8.5 hours
    (2, 16),    -- 8 hours + 8 hours
    (3, 16);    -- 8 hours + 8 hours

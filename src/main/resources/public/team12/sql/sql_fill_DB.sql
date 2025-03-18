-- Insert example users
INSERT INTO Users (username, password_hash)
VALUES
('AndersJensen', 'hashed_password_1'),
('SofieNielsen', 'hashed_password_2'),
('FrederikLarsen', 'hashed_password_3');

-- Insert example sleep records
INSERT INTO SleepRecords (user_id, sleep_date, sleep_start, sleep_end)
VALUES
(1, '2025-03-15', '2025-03-14 23:00:00', '2025-03-15 07:00:00'),
(1, '2025-03-16', '2025-03-15 23:30:00', '2025-03-16 06:30:00'),
(2, '2025-03-15', '2025-03-14 22:45:00', '2025-03-15 06:15:00'),
(2, '2025-03-16', '2025-03-15 23:15:00', '2025-03-16 07:45:00'),
(3, '2025-03-15', '2025-03-14 23:30:00', '2025-03-15 05:45:00'),
(3, '2025-03-16', '2025-03-15 22:00:00', '2025-03-16 06:00:00');

-- Insert example sleep analytics
INSERT INTO SleepAnalytics (user_id, sleep_date, total_sleep)
VALUES
(1, '2025-03-15', 8.00),
(1, '2025-03-16', 7.00),
(2, '2025-03-15', 7.50),
(2, '2025-03-16', 8.50),
(3, '2025-03-15', 6.25),
(3, '2025-03-16', 8.00);
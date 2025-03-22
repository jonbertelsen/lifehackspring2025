-- Users table
CREATE TABLE team12_users (
                              user_id SERIAL PRIMARY KEY,
                              username VARCHAR(50) UNIQUE NOT NULL,
                              password_hash VARCHAR(255) NOT NULL,
                              created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Sleep Records table
CREATE TABLE team12_sleep_records (
                                      record_id SERIAL PRIMARY KEY,
                                      user_id INT NOT NULL,
                                      sleep_start TIMESTAMP NOT NULL,
                                      sleep_end TIMESTAMP NOT NULL,
                                      sleep_duration NUMERIC(5,2) GENERATED ALWAYS AS (EXTRACT(EPOCH FROM (sleep_end - sleep_start)) / 3600.0) STORED,
                                      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                      FOREIGN KEY (user_id) REFERENCES team12_users(user_id) ON DELETE CASCADE
);

-- Sleep Analytics Table
CREATE TABLE team12_sleep_analytics (
                                        analytics_id SERIAL PRIMARY KEY,
                                        user_id INT NOT NULL,
                                        total_sleep NUMERIC(5,2) NOT NULL,
                                        last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                        FOREIGN KEY (user_id) REFERENCES team12_users(user_id) ON DELETE CASCADE
);
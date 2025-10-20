CREATE TABLE alert (
  id SERIAL PRIMARY KEY,
  title VARCHAR(255) NOT NULL,
  message TEXT NOT NULL,
  severity severity NOT NULL,
  start_time TIMESTAMP,
  expiry_time TIMESTAMP,
  reminder_frequency_hours INT NOT NULL,
  audience_type audiencetype NOT NULL,
  audience_id VARCHAR(255),
  active BOOLEAN DEFAULT TRUE
);
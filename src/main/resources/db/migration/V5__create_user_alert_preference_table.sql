CREATE TABLE user_alert_preference (
  id SERIAL PRIMARY KEY,
  user_id INT REFERENCES "user"(id),
  alert_id INT REFERENCES alert(id),
  status status DEFAULT 'UNREAD',
  snoozed_day DATE
);
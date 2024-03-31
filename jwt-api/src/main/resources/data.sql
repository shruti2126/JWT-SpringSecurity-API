
CREATE TABLE IF NOT EXISTS users (
  id BIGSERIAL PRIMARY KEY,
  username VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL,
  authority VARCHAR(255) NOT NULL
);

INSERT INTO users (username, password, authority) VALUES
  ('admin', '$2a$12$GvvW0YLvpRGGRUupi/YMhe.ueIwvMmhV/6jWXbemwNqOvOpIJ6LVS', 'ROLE_ADMIN'),
  ('user', '$2a$12$w.pPHNnpSeP7KvStJ7f0weJdPReIfUDjMNitiOeXndgpsd6eNSA3K', 'ROLE_USER');
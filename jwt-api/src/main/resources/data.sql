
CREATE TABLE IF NOT EXISTS users (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL
);

INSERT INTO users (username, password) VALUES
  ('user1', '$2a$12$M4ZJDIWSYAPWwCW2dgAsa.xJFT3NzdWSOiotyjK9AHclR/9UgkSya'),
  ('user2', '$2a$12$aUndtL29bMOT48k6I.htV.7MxpopQjMFL7UfcjNBY5fSDYnnLUETa');
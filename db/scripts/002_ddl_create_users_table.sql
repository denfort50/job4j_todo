CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY UNIQUE NOT NULL,
    username VARCHAR NOT NULL,
    login VARCHAR NOT NULL UNIQUE,
    password VARCHAR NOT NULL
);

COMMENT ON TABLE users IS 'Пользователи';
COMMENT ON COLUMN users.id IS 'Идентификатор пользователя';
COMMENT ON COLUMN users.username IS 'Имя пользователя';
COMMENT ON COLUMN users.login IS 'Логин пользователя';
COMMENT ON COLUMN users.password IS 'Пароль пользователя';
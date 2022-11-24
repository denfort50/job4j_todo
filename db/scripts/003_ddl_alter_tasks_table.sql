ALTER TABLE tasks ADD COLUMN user_id INT REFERENCES users(id);

COMMENT ON COLUMN tasks.user_id IS 'Идентификатор пользователя, которому принадлежат задачи';
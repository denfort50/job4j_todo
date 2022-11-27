CREATE TABLE IF NOT EXISTS tasks (
    id SERIAL PRIMARY KEY UNIQUE NOT NULL,
    title VARCHAR NOT NULL,
    description VARCHAR NOT NULL,
    created TIMESTAMP,
    done BOOLEAN
);

COMMENT ON TABLE tasks IS 'Задачи';
COMMENT ON COLUMN tasks.id IS 'Идентификатор задачи';
COMMENT ON COLUMN tasks.title IS 'Название задачи';
COMMENT ON COLUMN tasks.description IS 'Подробное описание задачи';
COMMENT ON COLUMN tasks.created IS 'Дата создания задачи';
COMMENT ON COLUMN tasks.done IS 'Флаг выполнения задачи';
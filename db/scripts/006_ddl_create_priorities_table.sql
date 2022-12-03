CREATE TABLE priorities (
    id SERIAL PRIMARY KEY,
    name VARCHAR UNIQUE NOT NULL,
    position INT
);
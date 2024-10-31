CREATE TABLE book
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(255)     NOT NULL UNIQUE,
    price       DOUBLE PRECISION NOT NULL,
    amount_sold INTEGER DEFAULT 0
);
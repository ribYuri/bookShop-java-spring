CREATE TABLE orders
(
    order_id      SERIAL PRIMARY KEY,
    status        VARCHAR(50)      NOT NULL,
    order_total   DOUBLE PRECISION NOT NULL,
    user_document VARCHAR(255)     NOT NULL,
    books         TEXT             NOT NULL,
    card_number   VARCHAR(50)      NOT NULL,
    card_cvv      VARCHAR(4)       NOT NULL
);
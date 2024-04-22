CREATE TABLE users
(
    id      BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name    VARCHAR(255),
    surname VARCHAR(255),
    role    SMALLINT,
    CONSTRAINT pk_users PRIMARY KEY (id)
);
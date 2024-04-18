CREATE TABLE applications
(
    id                    BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    user_id               BIGINT,
    monthly_income        DECIMAL                                 NOT NULL,
    financial_obligations DECIMAL                                 NOT NULL,
    car_id                BIGINT,
    manufacture_date      INTEGER                                 NOT NULL,
    free_text_explanation VARCHAR(255),
    is_submitted          BOOLEAN                                 NOT NULL,
    status                VARCHAR(255)                            NOT NULL,
    start_date            date,
    end_date              date,
    CONSTRAINT pk_applications PRIMARY KEY (id)
);

ALTER TABLE applications
    ADD CONSTRAINT FK_APPLICATIONS_ON_CAR FOREIGN KEY (car_id) REFERENCES cars (id);

ALTER TABLE applications
    ADD CONSTRAINT FK_APPLICATIONS_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);
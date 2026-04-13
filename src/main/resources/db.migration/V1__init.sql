CREATE TABLE patients
(
    uuid              VARCHAR(255) NOT NULL,
    identity_document VARCHAR(255) NOT NULL,
    first_name        VARCHAR(255) NOT NULL,
    last_name         VARCHAR(255) NOT NULL,
    email             VARCHAR(255),
    phone_number      VARCHAR(255),
    date_of_birth     date,
    created_at        TIMESTAMP WITHOUT TIME ZONE,
    updated_at        TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_patients PRIMARY KEY (uuid)
);

ALTER TABLE patients
    ADD CONSTRAINT uc_patients_email UNIQUE (email);

ALTER TABLE patients
    ADD CONSTRAINT uc_patients_identity_document UNIQUE (identity_document);

ALTER TABLE patients
    ADD CONSTRAINT uc_patients_phone_number UNIQUE (phone_number);

CREATE UNIQUE INDEX idx_email ON patients (email);

CREATE UNIQUE INDEX idx_identity_document ON patients (identity_document);

CREATE INDEX idx_lastname_firstname ON patients (last_name, first_name);
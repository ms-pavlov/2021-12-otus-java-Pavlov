CREATE SEQUENCE departments_sequence
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

CREATE TABLE departments
(
    department_id bigint NOT NULL,
    department_name varchar(255),
    department_description varchar(1000),
    department_active boolean DEFAULT TRUE,
    CONSTRAINT departments_pkey PRIMARY KEY (department_id)
);
CREATE SEQUENCE buildings_sequence
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

CREATE TABLE buildings
(
    building_id bigint NOT NULL,
    building_name varchar(255),
    building_description varchar(1000),
    building_active boolean DEFAULT TRUE,
    CONSTRAINT building_pkey PRIMARY KEY (building_id)
);
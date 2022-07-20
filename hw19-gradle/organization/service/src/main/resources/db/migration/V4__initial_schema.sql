CREATE SEQUENCE contacts_sequence
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

CREATE TABLE contacts
(
    contact_id bigint NOT NULL,
    contact_name varchar(255),
    contact_phone varchar(255),
    contact_placements_id bigint,
    contact_active boolean DEFAULT TRUE,
    CONSTRAINT contact_pkey PRIMARY KEY (contact_id),
    CONSTRAINT contact_in_placement FOREIGN KEY (contact_placements_id)
        REFERENCES placements (placement_id)
);
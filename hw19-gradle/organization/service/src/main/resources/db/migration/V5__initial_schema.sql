CREATE SEQUENCE rooms_sequence
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

CREATE TABLE rooms
(
    rooms_id bigint NOT NULL,
    rooms_name varchar(255),
    rooms_description varchar(1000),
    rooms_placements_id bigint,
    rooms_active boolean DEFAULT TRUE,
    CONSTRAINT rooms_pkey PRIMARY KEY (rooms_id),
    CONSTRAINT room_in_placements FOREIGN KEY (rooms_placements_id)
        REFERENCES placements (placement_id)
);
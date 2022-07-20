CREATE SEQUENCE placements_sequence
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

CREATE TABLE placements
(
    placement_id bigint NOT NULL,
    placement_building_id bigint,
    placement_department_id bigint,
    placement_active boolean DEFAULT TRUE,
    CONSTRAINT placement_pkey PRIMARY KEY (placement_id),
    CONSTRAINT placement_in_building FOREIGN KEY (placement_building_id)
        REFERENCES buildings (building_id),
    CONSTRAINT placement_in_department FOREIGN KEY (placement_department_id)
        REFERENCES departments (department_id)
);
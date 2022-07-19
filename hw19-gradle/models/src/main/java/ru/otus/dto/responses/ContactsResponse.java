package ru.otus.dto.responses;

import lombok.Data;

import java.io.Serializable;

@Data
public class ContactsResponse implements Serializable {
    private Long id;
    private String name;
    private String phone;
    private boolean active;
    private Long placementId;
}

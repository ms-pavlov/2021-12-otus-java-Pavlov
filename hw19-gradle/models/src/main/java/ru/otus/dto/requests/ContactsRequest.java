package ru.otus.dto.requests;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class ContactsRequest implements Serializable {
    @Size(max = 255, message = "{contacts.name.tooLong}")
    @NotEmpty(message = "{contacts.name.notNull}")
    private String name;
    @Size(max = 255, message = "{contacts.phone.tooLong}")
    private String phone;
    private boolean active;
    @NotNull(message = "{contacts.placementId.notNull}")
    @Min(value = 1L, message = "{contacts.placementId.tooLow}")
    private Long placementId;
}

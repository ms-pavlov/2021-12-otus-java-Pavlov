package ru.otus.dto.requests;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class DepartmentsRequest implements Serializable {
    @Size(max = 255, message = "{departments.name.tooLong}")
    @NotEmpty(message = "{departments.name.notNull}")
    private String name;
    @Size(max = 1000, message = "{departments.description.tooLong}")
    private String description;
    private boolean active;
}

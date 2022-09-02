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
@ToString
public class BuildingsRequest implements Serializable {
    @Size(max = 255, message = "{buildings.name.tooLong}")
    @NotEmpty(message = "{buildings.name.notNull}")
    private String name;
    @Size(max = 1000, message = "{buildings.description.tooLong}")
    private String description;
    private boolean active;
}

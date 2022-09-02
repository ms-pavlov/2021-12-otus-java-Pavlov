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
@ToString
public class RoomsRequest implements Serializable {
    @Size(max = 255, message = "{rooms.name.tooLong}")
    @NotEmpty(message = "{rooms.name.notNull}")
    private String name;
    @Size(max = 1000, message = "{rooms.description.tooLong}")
    private String description;
    private boolean active;
    @NotNull(message = "{rooms.placementId.notNull}")
    @Min(value = 1L, message = "{rooms.placementId.tooLow}")
    private Long placementId;
}

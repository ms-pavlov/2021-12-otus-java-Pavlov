package ru.otus.dto.requests;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class PlacementsRequest implements Serializable {
    @NotNull(message = "{placements.buildingId.notNull}")
    @Min(value = 1L, message = "{placements.buildingId.tooLow}")
    private Long buildingId;
    @NotNull(message = "{placements.departmentId.notNull}")
    @Min(value = 1L, message = "{placements.departmentId.tooLow}")
    private Long departmentId;
    private boolean active;
}

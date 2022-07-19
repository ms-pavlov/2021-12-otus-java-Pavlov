package ru.otus.messages;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.otus.dto.responses.PlacementsResponse;
import ru.otus.models.organization.PlacementsModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class PlacementsMessage implements Serializable {
    private Long id;
    private String name;
    private boolean active;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date timestamp;
    private List<RoomsMessage> rooms;

    public PlacementsMessage(PlacementsModel placementsModel) {
        Optional.ofNullable(placementsModel).ifPresent(model -> {
            this.id = model.getId();
            this.name = model.getPlacementsName();
            this.active = model.isActive();
            this.rooms = Optional.ofNullable(model.getRooms())
                    .map(roomsModels -> roomsModels.stream().map(RoomsMessage::new).toList())
                    .orElse(new ArrayList<>());
            this.timestamp = new Date();
        });
    }

    public PlacementsMessage(PlacementsResponse placement) {
        Optional.ofNullable(placement).ifPresent(model -> {
            this.id = model.getId();
            this.name = model.getPlacementsName();
            this.active = model.isActive();
            this.rooms = Optional.ofNullable(model.getRooms())
                    .map(roomsModels -> roomsModels.stream().map(RoomsMessage::new).toList())
                    .orElse(new ArrayList<>());
            this.timestamp = new Date();
        });
    }
}

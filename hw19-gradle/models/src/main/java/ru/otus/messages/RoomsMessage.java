package ru.otus.messages;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.otus.dto.responses.RoomsResponse;
import ru.otus.models.organization.RoomsModel;

import java.io.Serializable;
import java.util.Date;
import java.util.Optional;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RoomsMessage implements Serializable {
    private Long id;
    private String name;
    private boolean active;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date timestamp;

    public RoomsMessage(RoomsModel roomsModel) {
        Optional.ofNullable(roomsModel).ifPresent(model -> {
            this.id = model.getId();
            this.name = model.getName();
            this.active = model.isActive();

            this.timestamp = new Date();
        });
    }

    public RoomsMessage(RoomsResponse room) {
        Optional.ofNullable(room).ifPresent(model -> {
            this.id = model.getId();
            this.name = model.getName();
            this.active = model.isActive();

            this.timestamp = new Date();
        });
    }
}

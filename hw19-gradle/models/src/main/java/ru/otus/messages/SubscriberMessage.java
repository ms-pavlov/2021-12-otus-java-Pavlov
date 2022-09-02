package ru.otus.messages;

import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class SubscriberMessage {
    private String clientID;
    private String routingKey;
}

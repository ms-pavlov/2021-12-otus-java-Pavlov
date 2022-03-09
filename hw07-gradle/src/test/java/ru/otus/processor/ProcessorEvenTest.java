package ru.otus.processor;

import org.junit.jupiter.api.Test;
import ru.otus.model.Message;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ProcessorEvenTest {

    @Test
    void process() {
        var even = new ProcessorEven();

        var message = new Message.Builder(1L)
                .build();
        try {
            even.process(message);
            if ((even.getSnapshotDate()&1) == 1) {
                System.out.println("Нечет");
            }
        } catch (RuntimeException ex) {
            if ((even.getSnapshotDate()&1) == 0) {
                System.out.println("Чет");
                assertThat(ex.getMessage()).isEqualTo("Четная секунда");
            }
        }
    }

    @Test
    void getId() {
        var even = new ProcessorEven();

        var message = new Message.Builder(1L)
                .build();
        try {
            even.process(message);
            if ((even.getSnapshotDate()&1) == 1) {
                System.out.println("Нечет");
                assertThat(even.getId()).isEqualTo(message.getId());
            }
        } catch (RuntimeException ex) {
            if ((even.getSnapshotDate()&1) == 0) {
                System.out.println("Чет");
            }
        }
    }
}
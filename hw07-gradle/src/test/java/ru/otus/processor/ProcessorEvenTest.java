package ru.otus.processor;

import org.junit.jupiter.api.Test;
import ru.otus.model.Message;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProcessorEvenTest {

    @Test
    void process() {
        TimeStrategy timeStrategy = mock(TimeStrategy.class);
        when(timeStrategy.getCurrentTime()).thenReturn(2L);
        var even = new ProcessorEven(timeStrategy);

        var message = new Message.Builder(1L)
                .build();

        try {
            even.process(message);
        } catch (RuntimeException ex) {
            if ((even.getSnapshotDate() & 1) == 0) {
                System.out.println("Чет");
                assertThat(ex.getMessage()).isEqualTo("Четная секунда");
            }
        }
    }

    @Test
    void getId() {
        TimeStrategy timeStrategy = mock(TimeStrategy.class);
        when(timeStrategy.getCurrentTime()).thenReturn(1L);
        var even = new ProcessorEven(timeStrategy);

        var message = new Message.Builder(1L)
                .build();
        even.process(message);
        if ((even.getSnapshotDate() & 1) == 1) {
            System.out.println("Нечет");
            assertThat(even.getId()).isEqualTo(message.getId());
        }

    }
}
package ru.otus.dataprocessor;

import ru.otus.model.Measurement;

import java.util.*;
import java.util.stream.Collectors;

public class ProcessorAggregator implements Processor {
    /**
     * Группирует входящий data список по полю name в Map<String, Double>, при этом суммирует поля value. Результат сортируется по ключу.
     *
     * @param data входящий список
     * @return результат группировки в виде Map<String, Double>
     */
    @Override
    public Map<String, Double> process(List<Measurement> data) {
        return new TreeMap<>(data.stream()
                .collect(Collectors.groupingBy(Measurement::getName, Collectors.summingDouble(Measurement::getValue))));
    }
}

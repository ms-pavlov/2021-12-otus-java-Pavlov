package ru.otus.dataprocessor;

import ru.otus.model.Measurement;

import java.util.*;

public class ProcessorAggregator implements Processor {
    /**
     * Группирует входящий data список по полю name в Map<String, Double>, при этом суммирует поля value. Результат сортируется по ключу.
     *
     * @param data входящий список
     * @return результат группировки в виде Map<String, Double>
     */
    @Override
    public Map<String, Double> process(List<Measurement> data) {
        Map<String, Double> result = new TreeMap<>();
        data.forEach(measurement -> result.put(
                measurement.getName(),
                Optional.ofNullable(result.get(measurement.getName()))
                        .map(currentValue -> currentValue + measurement.getValue())
                        .orElse(measurement.getValue())));
        return result;
    }
}

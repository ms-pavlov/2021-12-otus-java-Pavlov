package ru.calculator;

public class Summator {
    private int sum = 0;
    private int prevValue = 0;
    private int prevPrevValue = 0;
    private int sumLastThreeValues = 0;
    private int someValue = 0;
    private int listValuesSize = 0;
//    private final List<Data> listValues = new ArrayList<>();

    //!!! сигнатуру метода менять нельзя
    public void calc(Data data) {
//        listValues.add(data);
        listValuesSize ++;
        if (listValuesSize % 6_600_000 == 0) {
            listValuesSize = 0;
//            listValues.clear();
        }

        sum += data.getValue();

        sumLastThreeValues = data.getValue() + prevValue + prevPrevValue;

        prevPrevValue = prevValue;
        prevValue = data.getValue();

        for (var idx = 0; idx < 3; idx++) {
            someValue = Math.abs(someValue + (sumLastThreeValues * sumLastThreeValues / (data.getValue() + 1) - sum)) + listValuesSize;
        }
    }

    public Integer getSum() {
        return sum;
    }

    public Integer getPrevValue() {
        return prevValue;
    }

    public Integer getPrevPrevValue() {
        return prevPrevValue;
    }

    public Integer getSumLastThreeValues() {
        return sumLastThreeValues;
    }

    public Integer getSomeValue() {
        return someValue;
    }
}

package ru.otus;


import java.util.*;

public class CustomerService {
    private final NavigableMap<Customer, String> persons;

    public CustomerService() {
        this.persons = new TreeMap<>(Comparator.comparingLong(Customer::getScores));
    }

    //важно подобрать подходящую Map-у, посмотрите на редко используемые методы, они тут полезны

    public final Map.Entry<Customer, String> getSmallest() {
        return getCopy(persons.firstEntry());
    }

    public final Map.Entry<Customer, String> getNext(Customer customer) {
        return getCopy(persons.higherEntry(customer));
    }

    public void add(Customer customer, String data) {
        this.persons.put(customer, data);
    }

    private Map.Entry<Customer, String> getCopy(Map.Entry<Customer, String> person) {
        if (null != person) {
            return new AbstractMap.SimpleImmutableEntry<>(new Customer(person.getKey()), person.getValue());
        }
        return null;
    }


}

package ru.otus.jdbc.mapper;

public interface EntityForJdbc<E> {

    boolean isIDNull(E entity);

    E setID(long id, E entity);
}

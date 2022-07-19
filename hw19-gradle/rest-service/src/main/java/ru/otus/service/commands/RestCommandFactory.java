package ru.otus.service.commands;

public interface RestCommandFactory<E, M, R, Q> {
    ServiceCommand<E, M, R, Q> getFindOneCommand();

    ServiceCommand<E, M, R, Q> getSaveCommand();

    ServiceCommand<E, M, R, Q> getDeleteCommand();
}

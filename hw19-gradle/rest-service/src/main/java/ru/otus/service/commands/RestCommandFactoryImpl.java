package ru.otus.service.commands;

public class RestCommandFactoryImpl<E, M, R, Q> implements RestCommandFactory<E, M, R, Q> {
    private final ServiceCommand<E, M, R, Q> findCommand;
    private final ServiceCommand<E, M, R, Q> saveCommand;
    private final ServiceCommand<E, M, R, Q> deleteCommand;

    public RestCommandFactoryImpl() {
        this.findCommand = new FindServiceCommand<>();
        this.saveCommand = new SaveServiceCommand<>();
        this.deleteCommand = new SoftDeleteServiceCommand<>();
    }

    @Override
    public ServiceCommand<E, M, R, Q> getFindOneCommand() {
        return findCommand;
    }

    @Override
    public ServiceCommand<E, M, R, Q> getSaveCommand() {
        return saveCommand;
    }

    @Override
    public ServiceCommand<E, M, R, Q> getDeleteCommand() {
        return deleteCommand;
    }
}

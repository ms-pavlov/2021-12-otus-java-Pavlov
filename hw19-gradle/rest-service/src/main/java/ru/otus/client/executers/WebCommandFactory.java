package ru.otus.client.executers;

public interface WebCommandFactory<Q> {
    WebCommand<Q> prepGet();

    WebCommand<Q> prepPost();

    WebCommand<Q> prepPut();
}

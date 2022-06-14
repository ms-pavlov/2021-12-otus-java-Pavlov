package ru.otus.services.request;

public interface WebRequestFactory<Q> {

    WebRequest<Q> prepGetRequest();

    WebRequest<Q> prepPostRequest(Q request);

    WebRequest<Q> prepPutRequest(Long id, Q request);
}

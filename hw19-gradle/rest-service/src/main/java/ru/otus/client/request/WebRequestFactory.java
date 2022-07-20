package ru.otus.client.request;

public interface WebRequestFactory<Q> {

    WebRequest<Q> prepGetListRequest();

    WebRequest<Q> prepGetRequest(Long id);

    WebRequest<Q> prepPostRequest(Q request);

    WebRequest<Q> prepPutRequest(Long id, Q request);
}

package ru.otus.client.request;


public class SimpleWebRequestFactory<Q> implements WebRequestFactory<Q> {

    private final String apiUpl;

    public SimpleWebRequestFactory(String apiUpl) {
        this.apiUpl = apiUpl;
    }

    @Override
    public WebRequest<Q> prepGetListRequest() {
        return new SimpleWebRequest<>(apiUpl);
    }

    @Override
    public WebRequest<Q> prepGetRequest(Long id) {
        return new SimpleWebRequest<>(apiUpl.concat(id + "/"));
    }

    @Override
    public WebRequest<Q> prepPostRequest(Q request) {
        return new SimpleWebRequest<>(apiUpl, request);
    }

    @Override
    public WebRequest<Q> prepPutRequest(Long id, Q request) {
        return new SimpleWebRequest<>(apiUpl.concat(id + "/"), request);
    }
}

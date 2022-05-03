package ru.otus.crm.service;

import ru.otus.cachehw.HwCache;
import ru.otus.core.repository.DataTemplate;
import ru.otus.core.sessionmanager.TransactionRunner;
import ru.otus.crm.model.Client;

import java.util.List;
import java.util.Optional;

public class DbServiceClientWithCacheImpl extends DbServiceClientImpl {
    private final HwCache<Long, Client> cache;

    public DbServiceClientWithCacheImpl(TransactionRunner transactionRunner, DataTemplate<Client> dataTemplate, HwCache<Long, Client> cache) {
        super(transactionRunner, dataTemplate);
        this.cache = cache;
    }

    @Override
    public Optional<Client> getClient(long id) {
        return Optional.ofNullable(
                Optional.ofNullable(cache.get(id))
                        .orElseGet(() -> getClientFromDB(id)));
    }

    private Client getClientFromDB(long id) {
        var response = super.getClient(id);
        response.ifPresent(client -> cache.put(id, client));
        return response.orElse(null);
    }

    @Override
    public Client saveClient(Client client) {
        var result = super.saveClient(client);
        cache.put(result.getId(), result);
        return result;
    }

    @Override
    public List<Client> findAll() {
        var result = super.findAll();
        result.forEach(client -> cache.put(client.getId(), client));
        return result;
    }
}

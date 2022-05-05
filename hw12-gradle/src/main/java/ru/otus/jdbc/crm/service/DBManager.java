package ru.otus.jdbc.crm.service;

import ru.otus.jdbc.core.repository.executor.DbExecutor;
import ru.otus.jdbc.core.sessionmanager.TransactionRunner;

public interface DBManager {
    TransactionRunner getTransactionRunner();
    DbExecutor getExecutor();

}

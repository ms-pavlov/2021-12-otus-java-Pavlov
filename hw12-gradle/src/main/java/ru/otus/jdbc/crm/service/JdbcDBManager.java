package ru.otus.jdbc.crm.service;

import ru.otus.jdbc.core.repository.executor.DbExecutor;
import ru.otus.jdbc.core.repository.executor.DbExecutorImpl;
import ru.otus.jdbc.core.sessionmanager.TransactionRunner;
import ru.otus.jdbc.core.sessionmanager.TransactionRunnerJdbc;

import javax.sql.DataSource;

public class JdbcDBManager implements DBManager{
    private final TransactionRunner transactionRunner;
    private final DbExecutor executor;

    public JdbcDBManager(DataSource dataSource) {
        this.transactionRunner = new TransactionRunnerJdbc(dataSource);
        this.executor = new DbExecutorImpl();
    }


    @Override
    public TransactionRunner getTransactionRunner() {
        return transactionRunner;
    }

    @Override
    public DbExecutor getExecutor() {
        return executor;
    }
}

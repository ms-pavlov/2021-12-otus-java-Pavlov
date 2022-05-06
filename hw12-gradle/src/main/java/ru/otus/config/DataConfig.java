package ru.otus.config;

import ru.otus.jdbc.crm.model.Client;
import ru.otus.jdbc.crm.service.DBManager;
import ru.otus.jdbc.crm.service.DBService;
import ru.otus.web.dao.UserDao;

public interface DataConfig {

    DBService<Client> getDbClientService();

    UserDao getUserDao();
}

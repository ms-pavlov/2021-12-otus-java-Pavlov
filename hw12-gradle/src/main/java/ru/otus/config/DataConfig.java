package ru.otus.config;

import ru.otus.jdbc.crm.service.DBManager;
import ru.otus.web.dao.UserDao;

public interface DataConfig {

    DBManager getDbClientManager();

    UserDao getUserDao();
}

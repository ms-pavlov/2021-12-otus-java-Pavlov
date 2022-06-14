package ru.otus.jdbc.crm.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.CrudRepository;
import ru.otus.entities.Client;
import ru.otus.services.data.DBService;
import ru.otus.services.data.SpringDataJdbcService;
import ru.otus.services.data.sessionmanager.TransactionManager;
import ru.otus.services.data.sessionmanager.TransactionManagerSpring;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class SpringDataJdbcServiceTest {
    private static final String URL = "jdbc:h2:mem:test";
    private static final String USER = "sa";
    private static final String PASSWORD = "";
    private static final int COUNT = 2;
    private static final Logger log = LoggerFactory.getLogger(SpringDataJdbcServiceTest.class);
    private DBService<Client> service;
    private CrudRepository<Client, Long> crudRepository;

    @BeforeEach
    void before() {
        this.crudRepository = (CrudRepository<Client, Long>) mock(CrudRepository.class);

        Client[] list = {new Client(1L, "Vasa", 2), new Client("Kolya", 0)};

        when(crudRepository.findAll()).thenReturn(Arrays.asList(list));
        when(crudRepository.findById(1L)).thenReturn(Optional.ofNullable(list[0]));

        TransactionManager transactionManager = new TransactionManagerSpring();
        service = new SpringDataJdbcService<>(transactionManager, crudRepository);
    }

    @Test
    void saveAndFindOne() {
        var client = new Client("Vasa", 0);
        service.save(client);
        verify(crudRepository, Mockito.times(1)).save(client);

        var inDBClient = service.findOne(1L);

        assertEquals(1L, inDBClient.map(Client::getId).orElse(null));
        assertEquals("Vasa", inDBClient.map(Client::getName).orElse(null));
    }

    @Test
    void findAll() {
        var clients = service.findAll();

        assertEquals("Vasa", clients.get(0).getName());

        var firstClient = clients.stream().sorted(Comparator.comparingInt(Client::getOrderColumn)).toList().get(0);

        assertEquals("Kolya", firstClient.getName());
        assertEquals(COUNT, clients.size());
    }

}
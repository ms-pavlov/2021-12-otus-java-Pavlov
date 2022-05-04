package ru.otus.jdbc.crm.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.base.AbstractHibernateTest;
import ru.otus.jdbc.crm.model.Client;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Демо работы с hibernate (с абстракциями) должно ")
class DbServiceClientTest extends AbstractHibernateTest {

    @Test
    @DisplayName(" корректно сохранять, изменять и загружать клиента")
    void shouldCorrectSaveClient() {
        //given
        var client = new Client("Ivan");

        //when
        var savedClient = dbServiceClient.saveClient(client);
        System.out.println(savedClient);

        //then
        var loadedSavedClient = dbServiceClient.getClient(savedClient.getId());
        Assertions.assertThat(loadedSavedClient).isPresent();
        Assertions.assertThat(loadedSavedClient.get()).usingRecursiveComparison().isEqualTo(savedClient);

        //when
        var savedClientUpdated = loadedSavedClient.get().clone();
        savedClientUpdated.setName("updatedName");
        dbServiceClient.saveClient(savedClientUpdated);

        //then
        var loadedClient = dbServiceClient.getClient(savedClientUpdated.getId());
        Assertions.assertThat(loadedClient).isPresent();
        Assertions.assertThat(loadedClient.get()).usingRecursiveComparison().isEqualTo(savedClientUpdated);
        System.out.println(loadedClient);

        //when
        var clientList = dbServiceClient.findAll();

        //then
        Assertions.assertThat(clientList.size()).isEqualTo(1);
        Assertions.assertThat(clientList.get(0)).usingRecursiveComparison().isEqualTo(loadedClient.get());
    }
}
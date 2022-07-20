package ru.otus.services.sources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import ru.otus.jpa.entities.Contacts;
import ru.otus.mappers.EntityMapper;
import ru.otus.models.organization.ContactsModel;
import ru.otus.service.repositories.SimpleCRUDModel;

@Component
public class ContactsSource extends SimpleCRUDModel<ContactsModel, Contacts> {
    @Autowired
    public ContactsSource(JpaRepository<Contacts, Long> repository,
                          @Qualifier("contactsEntityMapperImpl") EntityMapper<ContactsModel, Contacts> entityMapper) {
        super(repository, entityMapper);
    }
}

package ru.otus.jdbc.crm.model;

import javax.persistence.*;
import java.util.Optional;

@Entity
@Table(name = "phone")
public class Phone {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "phone_sequence")
    @SequenceGenerator(name = "phone_sequence", sequenceName = "phone_sequence", allocationSize = 1)
    @Column(name = "phone_id")
    private Long id;

    @Column(name = "phone_number")
    private String number;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "phone_client_key"), name = "phone_client_id", referencedColumnName = "id")
    private Client client;

    public Phone() {
    }

    public Phone(Long id, String number) {
        this.id = id;
        this.number = number;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Long getClientId() {
        return Optional.ofNullable(client).map(Client::getId).orElse(null);
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return "Phone{" +
                "id=" + id +
                ", number='" + number + '\'' +
//                ", client=" + getClientId() +
                '}';
    }
}

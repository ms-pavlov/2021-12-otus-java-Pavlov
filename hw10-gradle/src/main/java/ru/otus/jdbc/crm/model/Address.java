package ru.otus.jdbc.crm.model;

import javax.persistence.*;
import java.util.Optional;

@Entity
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "address_sequence")
    @SequenceGenerator(name = "address_sequence", sequenceName = "address_sequence", allocationSize = 1)
    @Column(name = "address_id")
    private Long id;

    @Column(name = "address_street")
    private String street;

    @OneToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "address_client_key"), name = "address_client_id", referencedColumnName = "id")
    private Client client;

    public Address() {
    }

    public Address(Long id, String street) {
        this.id = id;
        this.street = street;
    }

    public Address(Long id, String street, Client client) {
        this.id = id;
        this.street = street;
        this.client = client;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
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
        return "Address{" +
                "id=" + id +
                ", street='" + street + '\'' +
//                ", client=" + getClientId() +
                '}';
    }
}

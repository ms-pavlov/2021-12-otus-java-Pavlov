package ru.otus.crm.model;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "client")
public class Client implements Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_sequence")
    @SequenceGenerator(name = "client_sequence", sequenceName = "client_sequence", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToOne(targetEntity = Address.class, mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Address address;

    @OneToMany(targetEntity = Phone.class, mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Phone> phones;

    public Client() {
    }

    public Client(String name) {
        this.id = null;
        this.name = name;
    }

    public Client(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Client(Long id, String name, Address address, List<Phone> phones) {
        this.id = id;
        this.name = name;
        this.address = Optional.ofNullable(address).map(address1 -> {
            address1.setClient(this);
            return address1;
        }).orElse(null);
        this.phones = Optional.ofNullable(phones).orElse(new ArrayList<>()).stream().peek(phone -> phone.setClient(this)).toList();
    }

    @Override
    public Client clone() {
        return new Client(this.id, this.name, this.address, this.phones);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phones='" + phones + '\'' +
                '}';
    }


}

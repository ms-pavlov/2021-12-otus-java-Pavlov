package ru.otus.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.relational.core.mapping.Table;

import javax.annotation.Nonnull;
import java.io.Serial;
import java.io.Serializable;

@Table("client")
public class Client implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    private final Long id;
    @Nonnull
    private final String name;
    @Nonnull
    private final Integer orderColumn;

    public Client(String name, int orderColumn) {
        this(null, name, orderColumn);
    }

    @PersistenceConstructor
    public Client(Long id, @Nonnull String name, int orderColumn) {
        this.id = id;
        this.name = name;
        this.orderColumn = orderColumn;
    }


    public Long getId() {
        return id;
    }

    @Nonnull
    public String getName() {
        return name;
    }

    @Nonnull
    public Integer getOrderColumn() {
        return orderColumn;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Client client = (Client) o;

        return name.equals(client.name);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + name.hashCode();
        return result;
    }
}

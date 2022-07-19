package ru.otus.jpa.entities;

import lombok.*;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serial;
import java.io.Serializable;
import java.util.Optional;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "contacts")
@XmlRootElement
public class Contacts implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "contacts_sequence", sequenceName = "contacts_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contacts_sequence")
    @Basic(optional = false)
    @Column(name = "contact_id")
    private Long id;
    @Column(name = "contact_name")
    private String name;
    @Column(name = "contact_phone")
    private String phone;
    @Column(name = "contact_active")
    private boolean active;
    @JoinColumn(name = "contact_placements_id", referencedColumnName = "placement_id")
    @ManyToOne
    private Placements placement;

    public Long getDepartmentId() {
        return Optional.ofNullable(placement).map(Placements::getDepartmentId).orElse(null);
    }

    public String getDepartmentName() {
        return Optional.ofNullable(placement).map(Placements::getDepartmentName).orElse(null);
    }
}
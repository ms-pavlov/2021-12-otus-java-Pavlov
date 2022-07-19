package ru.otus.jpa.entities;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "buildings")
@EqualsAndHashCode
@XmlRootElement
public class Buildings implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "buildings_sequence", sequenceName = "buildings_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "buildings_sequence")
    @Basic(optional = false)
    @Column(name = "building_id")
    private Long id;
    @Column(name = "building_name")
    private String name;
    @Column(name = "building_description", length = 1000)
    private String description;
    @Column(name = "building_active")
    private boolean active;
    @OneToMany(mappedBy = "building")
    @ToString.Exclude
    private Collection<Placements> placements;

}

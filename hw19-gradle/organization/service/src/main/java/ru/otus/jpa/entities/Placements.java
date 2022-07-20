package ru.otus.jpa.entities;

import lombok.*;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.Optional;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "placements")
@XmlRootElement
public class Placements implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "placements_sequence", sequenceName = "placements_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "placements_sequence")
    @Basic(optional = false)
    @Column(name = "placement_id")
    private Long id;
    @Column(name = "placement_active")
    private boolean active;
    @JoinColumn(name = "placement_department_id", referencedColumnName = "department_id")
    @ManyToOne
    private Departments department;
    @JoinColumn(name = "placement_building_id", referencedColumnName = "building_id")
    @ManyToOne
    private Buildings building;
    @OneToMany(mappedBy = "placement")
    private Collection<Contacts> contacts;
    @OneToMany(mappedBy = "placement")
    private Collection<Rooms> rooms;

    public Long getDepartmentId() {
        return Optional.ofNullable(department).map(Departments::getId).orElse(null);
    }

    public String getDepartmentName() {
        return Optional.ofNullable(department).map(Departments::getName).orElse(null);
    }

    public Long getBuildingId() {
        return Optional.ofNullable(building).map(Buildings::getId).orElse(null);
    }

    public String getBuildingName() {
        return Optional.ofNullable(building).map(Buildings::getName).orElse(null);
    }
}

package ru.otus.jpa.entities;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "departments")
@XmlRootElement
@NamedEntityGraph(name = "Departments.default", attributeNodes = {@NamedAttributeNode("placements")})
public class Departments implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "departments_sequence", sequenceName = "departments_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "departments_sequence")
    @Basic(optional = false)
    @Column(name = "department_id")
    private Long id;
    @Column(name = "department_name", length = 512)
    private String name;
    @Column(name = "department_description", length = 1000)
    private String description;
    @Column(name = "department_active")
    private boolean active;
    @OneToMany(mappedBy = "department")
    private Collection<Placements> placements;
}

package ru.otus.jpa.entities;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "rooms")
@XmlRootElement
public class Rooms implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "rooms_sequence", sequenceName = "rooms_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rooms_sequence")
    @Basic(optional = false)
    @Column(name = "rooms_id")
    private Long id;
    @Column(name = "rooms_name")
    private String name;
    @Column(name = "rooms_description", length = 1000)
    private String description;
    @Column(name = "rooms_active")
    private boolean active;

    @JoinColumn(name = "rooms_placements_id", referencedColumnName = "placement_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Placements placement;
}

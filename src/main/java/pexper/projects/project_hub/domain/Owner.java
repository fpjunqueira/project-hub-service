package pexper.projects.project_hub.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = {"projects", "address"})
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Include
    private Long id;
    private String name;
    private String email;

    @JsonIgnore
    @ManyToMany(mappedBy = "owners")
    private Set<Project> projects = new HashSet<>();

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;
}

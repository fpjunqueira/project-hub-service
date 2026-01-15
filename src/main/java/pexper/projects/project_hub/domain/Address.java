package pexper.projects.project_hub.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = {"owner", "project"})
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Include
    private Long id;
    private String street;
    private String city;
    private String state;
    private String number;
    private String zipCode;

    @JsonIgnore
    @OneToOne
    private Owner owner;

    @JsonIgnore
    @OneToOne
    private Project project;
}

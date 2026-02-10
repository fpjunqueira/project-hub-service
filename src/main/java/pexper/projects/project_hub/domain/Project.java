package pexper.projects.project_hub.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
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
@ToString(exclude = {"owners", "address", "files", "contractRegistration"})
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Include
    private Long id;
    private String projectName;
    private String projectType;
    private String projectNumber;
    private String purchaseOrder;
    private String serviceOrder;
    private String poNumber;
    private String directClient;
    private String directClientManager;
    private String finalClient;
    private String finalClientManager;
    private String siteId;

    @Column(name = "address_reference")
    private String addressId;

    @ManyToOne
    @JoinColumn(name = "contract_registration_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private ContractRegistration contractRegistration;

    @ManyToMany
    @JoinTable(name = "owner_project", joinColumns = @JoinColumn(name = "project_id"), inverseJoinColumns = @JoinColumn(name = "owner_id"))
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Set<Owner> owners = new HashSet<>();

    @OneToOne
    @JoinColumn(name = "address_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Address address;

    @OneToMany(mappedBy = "project")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Set<File> files = new HashSet<>();
}

package pexper.projects.project_hub.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ContractRegistration {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Include
    private Long id;
    private String directClient;
    private String directClientManager;
    private String finalClient;
    private String finalClientManager;
    private String projectType;
    private String projectNumber;
    private String purchaseOrder;
    private String serviceOrder;
    private String poNumber;
    private String siteId;
    private String addressId;
    private String totalProjectValue;
    private String projectPhases;
}

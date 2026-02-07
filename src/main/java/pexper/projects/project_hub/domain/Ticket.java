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
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Include
    private Long id;
    private String ticketNumber;
    private String directClient;
    private String directClientManager;
    private String finalClient;
    private String finalClientManager;
    private String projectType;
    private String projectNumber;
    private String purchaseOrder;
    private String serviceOrder;
    private String poNumber;
    private String address;
    private String addressNumber;
    private String addressComplement;
    private String district;
    private String city;
    private String state;
    private String zipCode;
    private String latitudeLongitude;
    private String siteType;
    private String accessReleaseNumber;
    private String tbsaId;
    private String tbsaTicket;
    private String activityDescription;
}

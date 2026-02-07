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
public class Documentation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Include
    private Long id;
    private String documentationType;
    private String directClient;
    private String directClientManager;
    private String finalClient;
    private String finalClientManager;
    private String projectType;
    private String projectNumber;
    private String purchaseOrder;
    private String serviceOrder;
    private String technician;
    private String address;
    private String addressNumber;
    private String addressComplement;
    private String district;
    private String city;
    private String state;
    private String zipCode;
    private String latitudeLongitude;
    private String siteId;
    private String addressId;
    private String projectPhase;
    private String activityType;
    private String scheduledDate;
    private String scheduledTime;
    private String finalClientName;
    private String finalClientContact;
    private String finalClientEmail;
    private String materialUsed;
    private String note1;
    private String note2;
}

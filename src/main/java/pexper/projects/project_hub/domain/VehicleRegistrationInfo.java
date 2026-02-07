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
public class VehicleRegistrationInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Include
    private Long id;
    private String licensePlate;
    private String renavam;
    private String chassis;
    private String vehicleType;
    private String category;
    private String makeModel;
    private String manufactureYear;
    private String modelYear;
    private String color;
    private String capacity;
    private String currentMileage;
    private String crlv;
    private String ipvaStatus;
    private String registrationExpiry;
    private String finesStatus;
    private String insuranceStatus;
    private String insuranceExpiryDate;
    private String companyOrThirdParty;
    private String ownerName;
    private String taxId;
    private String serviceHistory;
    private String nextScheduledService;
    private String oilChange;
    private String tireMaintenance;
    private String damageRepairRecords;
    private String fuelType;
    private String averageConsumption;
    private String fuelingHistory;
    private String note1;
    private String note2;
}

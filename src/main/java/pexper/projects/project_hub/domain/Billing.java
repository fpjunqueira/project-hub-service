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
public class Billing {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Include
    private Long id;
    private String legalName;
    private String taxId;
    private String billingAddress;
    private String phone;
    private String email;
    private String issueDate;
    private String dueDate;
    private String startDate;
    private String endDate;
    private String competenceMonth;
    private String totalAmount;
    private String finalClientManager;
    private String projectType;
    private String projectNumber;
    private String purchaseOrder;
    private String serviceOrder;
    private String poNumber;
    private String finalClient;
    private String description;
    private String freelancerAmount;
    private String materialsAmount;
    private String freightAmount;
    private String dailyRate;
    private String technicianCount;
    private String actualActivityDuration;
    private String taxIss;
    private String taxIcms;
    private String taxPis;
    private String taxCofins;
}

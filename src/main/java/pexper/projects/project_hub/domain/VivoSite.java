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
public class VivoSite {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Include
    private Long id;
    private String sequence;
    private String addressId;
    private String stateAbbreviation;
    private String state;
    private String name;
    private String abbreviation;
    private String gvOiFixedAbbreviation;
    private String ssiAddress;
    private String altitude;
    private String maintenancePending;
    private String thirdPartyArea;
    private String structure;
    private String vipSite;
    private String note1;
    private String note2;
    private String note3;
}

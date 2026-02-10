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
public class ClaroSite {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Include
    private Long id;
    private String siteId;
    private String addressId;
    private String name;
    private String anatelTx;
    private String anatelRf;
    private String anatelNetwork;
    private String elevation;
    private String latitude;
    private String longitude;
    private String latitudeDms;
    private String longitudeDms;
    private String geoCluster;
    private String datum;
    private String ibge;
    private String infraSla;
    private String omrClassification;
    private String contractClass;
    private String gsmUmtsLteLicenses;
    private String note1;
    private String note2;
    private String note3;
}

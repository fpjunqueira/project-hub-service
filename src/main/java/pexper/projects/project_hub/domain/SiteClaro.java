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
public class SiteClaro {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Include
    private Long id;
    private String siteId;
    private String nome;
    private String anatelTx;
    private String anatelRf;
    private String redeAnatel;
    private String elevacao;
    private String latitude;
    private String longitude;
    private String latitudeDms;
    private String longitudeDms;
    private String clusterGeo;
    private String datum;
    private String ibge;
    private String slaInfra;
    private String classificacaoOmr;
    private String classContrato;
    private String licencasGsmUmtsLte;
    private String observacao1;
    private String observacao2;
    private String observacao3;
}

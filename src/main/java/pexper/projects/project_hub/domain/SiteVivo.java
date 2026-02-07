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
public class SiteVivo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Include
    private Long id;
    private String sequencial;
    private String siglaUf;
    private String uf;
    private String nome;
    private String sigla;
    private String siglaGvOiFixa;
    private String enderecoSsi;
    private String altitude;
    private String pendenciaManutencao;
    private String areaTerceiros;
    private String estrutura;
    private String siteVip;
    private String observacao1;
    private String observacao2;
    private String observacao3;
}

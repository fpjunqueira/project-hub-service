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
public class SiteTim {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Include
    private Long id;
    private String siteId;
    private String tipoElemento;
    private String tecnologia;
    private String tipoConexao;
    private String classificacao;
    private String dataAquisicao;
    private String dataConstrucao;
    private String dataAtivacao;
    private String dataDesativacao;
    private String justificativa;
    private String tipoTorre;
    private String aevNominal;
    private String alturaEstrutura;
    private String statusAtualizacaoSpazio;
}

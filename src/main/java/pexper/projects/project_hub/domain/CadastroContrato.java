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
public class CadastroContrato {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Include
    private Long id;
    private String clienteDireto;
    private String gestorClienteDireto;
    private String clienteFinal;
    private String gestorClienteFinal;
    private String projetoTipo;
    private String projetoNumero;
    private String oc;
    private String os;
    private String po;
    private String siteId;
    private String enderecoId;
    private String valorTotalProjeto;
    private String fasesProjeto;
}

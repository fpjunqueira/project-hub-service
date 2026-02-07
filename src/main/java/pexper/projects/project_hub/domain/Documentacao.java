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
public class Documentacao {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Include
    private Long id;
    private String tipoDocumentacao;
    private String clienteDireto;
    private String gestorClienteDireto;
    private String clienteFinal;
    private String gestorClienteFinal;
    private String projetoTipo;
    private String projetoNumero;
    private String oc;
    private String os;
    private String tecnico;
    private String endereco;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String uf;
    private String cep;
    private String latitudeLongitude;
    private String siteId;
    private String enderecoId;
    private String faseProjeto;
    private String tipoAtividade;
    private String dataAgendamento;
    private String horarioAgendamento;
    private String nomeClienteFinal;
    private String contatoClienteFinal;
    private String emailClienteFinal;
    private String materialUtilizado;
    private String observacao1;
    private String observacao2;
}

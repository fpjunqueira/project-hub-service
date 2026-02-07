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
public class Faturamento {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Include
    private Long id;
    private String nomeRazaoSocial;
    private String cnpjCpf;
    private String enderecoCobranca;
    private String telefone;
    private String email;
    private String dataEmissao;
    private String dataVencimento;
    private String dataInicio;
    private String dataTermino;
    private String mesCompetencia;
    private String valorTotal;
    private String gestorClienteFinal;
    private String projetoTipo;
    private String projetoNumero;
    private String oc;
    private String os;
    private String po;
    private String clienteFinal;
    private String descricao;
    private String valorFreelancer;
    private String valorMateriais;
    private String valorFrete;
    private String valorDiaria;
    private String quantidadeTecnicos;
    private String duracaoRealAtividade;
    private String impostoIss;
    private String impostoIcms;
    private String impostoPis;
    private String impostoCofins;
}

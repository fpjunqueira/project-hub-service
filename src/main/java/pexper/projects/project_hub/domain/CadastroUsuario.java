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
public class CadastroUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Include
    private Long id;
    private String nomeCompleto;
    private String rg;
    private String orgaoExpedidor;
    private String dataExpedicao;
    private String dataNascimento;
    private String nomeMae;
    private String nomePai;
    private String cpf;
    private String naturalidade;
    private String estadoNasceu;
    private String endereco;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String uf;
    private String cep;
    private String login;
    private String senha;
    private String nivelPermissao;
    private String cargo;
    private String cltOuCnpj;
    private String dataAdmissao;
    private String asoValidade;
    private String nr10Validade;
    private String nr35Validade;
    private String situacao;
    private String observacao1;
    private String observacao2;
    private String observacao3;
}
